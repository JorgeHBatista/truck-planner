<template>
    <h1>
        Ports
    </h1>
    <div class="form">
        <div class="filter">Filter:</div>
        <input type="text" v-model="search">
        <div class="filter">Results per page:</div>
        <input type="number" v-model="pageRequest.size">
        <Table :myFunction="start" :ports="filterPosts(portStateManager.ports)">
            <template #thead>
                <TableHead>Identifier</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>Coordinates</TableHead>
            </template>

            <template #tbody>
                <template v-for="port in filterPosts(portStateManager.ports)" :key="port.id">
                    <tr>
                    <TableCell :cellvalue="port.id" cellkey="id">
                        {{ port.id }}
                    </TableCell>
                    <TableCell :cellvalue="port.name.portName" cellkey="portName">
                        {{ port.name.portName }}
                    </TableCell>
                    <TableCell :cellvalue="port.coordinate.toString()" cellkey="coordinate">
                        {{ port.coordinate.toString() }}
                    </TableCell>
                    </tr>
                </template>
            </template>
        </Table>
        <div ref="mapContainer" class="map-container"></div>
        <div class="afterTable footer">
            
            <button class="delete" v-on:click="deleteAll">DELETE ALL</button>
            <button class="delete" v-on:click="erase">DELETE</button>
            <div class="paginationLine" v-for="(button, index) in pages" :key=index>
                <button class="paginationButton" v-on:click="handlePagination(index)">
                    {{ typeof(button)==="number" ? button + 1 : button }}
                </button>
            </div>
            <button class="update" v-on:click="update">UPDATE</button>
        </div>
    </div>
</template>

<script lang="ts">

import { Port } from '../../../domain/entity/port';
import { PortName } from '../../../domain/valueobject/portName';
import { Coordinate } from '../../../domain/valueobject/coordinate';
import { usePortStateManager } from '../stateManager/portStateManager';
import { defineAsyncComponent } from "vue";
import { UllUUID } from '../../../utils/ull-uuid';
import { toast } from 'vue3-toastify';
import { ref, onMounted } from 'vue';
import L from 'leaflet';

export default {
    setup() {
        const mapContainer = ref(null);
        let map: any;
        const latitude = ref(0);
        const longitude = ref(0);
        let marker: any;

        onMounted(() => {
            if (mapContainer.value) {
                map = L.map(mapContainer.value).setView([36.12692188650319, -9.631536524590521], 4);

                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors'
                }).addTo(map);
            }
            map.on('click', function(ev: any){
                var latlng = map.mouseEventToLatLng(ev.originalEvent);
                latitude.value = latlng.lat;
                longitude.value = latlng.lng;
                if (latitude.value !== 0 && longitude.value !== 0) {
                    if (marker) {
                        map.removeLayer(marker);
                    }
                    marker = L.marker([latitude.value, longitude.value]);
                    map.addLayer(marker);
                    marker.addTo(map).bindPopup('Current spot:' + latitude.value + ', ' + longitude.value);
                }
            });
        });

        return {
            latitude,
            longitude,
            marker,
            mapContainer,
            map,
        };
    },
    data() {
        return {
            pageRequest: {
                page: 0,
                size: 2,
            },
            portStateManager: usePortStateManager(),
            search: '',
            pages: ['<<', '<', 0, '>', '>>'],
            coordinates: [] as Coordinate[],
        };
    },
    components: {
        Table: defineAsyncComponent(() => import("../table/Table.vue")),
        TableHead: defineAsyncComponent(() => import("../table/TableHead.vue")),
        TableCell: defineAsyncComponent(() => import("../table/TableCell.vue")),
    },
    methods: {
        handlePagination(index: number) {
            switch (index) {
                case 0:
                    this.pageRequest.page = 0;
                    break;
                case 1:
                    if (this.pageRequest.page > 0) {
                        this.pageRequest.page--;
                    }
                    break;
                case 2:
                    break;
                case 3:
                    if (this.pageRequest.page < this.portStateManager.ports.length / this.pageRequest.size - 1) {
                        this.pageRequest.page++;
                    }
                    break;
                case 4:
                    if (this.portStateManager.ports.length % this.pageRequest.size === 0) {
                        this.pageRequest.page = this.portStateManager.ports.length / this.pageRequest.size - 1;
                    } else {
                        this.pageRequest.page = Math.floor(this.portStateManager.ports.length / this.pageRequest.size);
                    }
                    
            }
            this.pages[2] = this.pageRequest.page;
        },
        filterPosts(ports: any): Port[] {
            let portsFiltered: Port[] = [];
            portsFiltered = ports.filter((port: Port) => {
                if (port.id.toString().toUpperCase().includes(this.search.toUpperCase())
                    || port.name.portName.toUpperCase().includes(this.search.toUpperCase())
                    || port.coordinate.toString().toUpperCase().includes(this.search.toUpperCase())) {
                    return port;
                }
            });
            return this.pagePosts(portsFiltered);
        },
        pagePosts(ports: any): Port[] {
            const pageablePorts: Port[] = [];
            const initialPage = this.pageRequest.page * this.pageRequest.size;
            for (let i = initialPage; i < this.pageRequest.size + initialPage; i++) {
                if (ports[i] != null) {
                    pageablePorts.push(ports[i]);
                }
            }
            return pageablePorts;
        },
        async start() {
            try {
                await this.portStateManager.findAll(this.pageRequest);
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async update() {
            try {
                const id = prompt('Enter the id of the port you want to update');
                if (id != null) {
                    const uuid = new UllUUID(id);
                    const nameString = prompt('Enter the new name of the port');
                    if (nameString != null) {
                        const name = new PortName(nameString);
                        const latitudeString = prompt('Enter the new latitude of the port');
                        if (latitudeString != null) {
                            const longitudeString = prompt('Enter the new longitude of the port');
                            if (longitudeString != null) {
                                const coordinate = new Coordinate(Number(latitudeString), Number(longitudeString));
                                const port = new Port(uuid, name, coordinate);
                                await this.portStateManager.update(uuid, port);
                                this.showNotification('Success', 'Port updated successfully');
                                return;
                            }
                        }
                    }
                }
                alert('You can not leave any field empty');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async erase() {
            const id = prompt('Enter the id of the port you want to delete');
            if (id != null) {
                let uuid: UllUUID;
                try {
                    uuid = new UllUUID(id);
                    await this.portStateManager.delete(uuid);
                    this.showNotification('Success', 'Port deleted successfully');
                } catch (error) {
                    if (error instanceof Error) {
                        if (error.message === "") error.message = "Port not deleted, incorrect id";
                    }
                    this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                }
            } else {
                alert('You have to enter an id');
            }
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all ports?')) {
                try {
                    await this.portStateManager.deleteAll();
                    this.showNotification('Success', 'Ports deleted successfully');
                } catch (error) {
                   this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                }
            }
            
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                if (message === "") message = "Unkown error";
                toast.error(message, {
                    autoClose: 3000,
                })
                return;
            } else {
                toast.success(message, {
                    autoClose: 3000,
                })
            }
        },
    },
    async created() {
        await this.start();
    },
};
</script>
<style scoped>
    .form {
        margin-left: 5%;
        margin-right: 5%;
    }
    .map-container {
        border: white solid 5px;
        margin-left: 11%;
        height: 300px;
    }
    .afterTable {
        margin-top: 0%;
        margin-left: 3%;
        width: 90%;
        display: flex;
        flex-direction: row;
    }
    .paginationLine {
        margin-left: 1%;
        margin-right: 1%;
        margin: auto;
    }
    .paginationButton {
        border-radius: 20px;
        text-align: center;
        color: black;
        width: 40px;
        height: 50px;
        font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
        border: 1px solid #ccc;
        background-color: #fff;
    }
    .filter {
        display: flex;
        flex-direction: row-reverse;
        margin-right: 1%;
        text-transform: uppercase;
        font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
    }
    input {
        width: 10%;
        height: 30px;
        border-radius: 5px;
        margin: auto;
        margin-right: 1%;
        border: 1px solid #ccc;
        background-color: #fff !important;
        font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
    }
    button {
        margin-top: 3%;
        margin-bottom: 2%;
        border-radius: 20px;
        text-align: center;
        color: white;
        width: 8%;
        height: 30px;
        font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
    }
    .delete {
        margin-right: 1%;
        background-color: red;
    }
    .update {
        margin-left: 1%;
        background-color: lightblue;
    }
</style>
