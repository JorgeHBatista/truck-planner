<template>
    <h1>
        Port {{ id }}
    </h1>
    <br><br>
    <Table class="table">
        <template #thead>
            <TableHead>Identifier</TableHead>
            <TableHead>Name</TableHead>
            <TableHead>Coordinates</TableHead>
        </template>

        <template #tbody>
            <template v-for="port in ports" :key="port.id">
                <tr>
                <TableCell :cellvalue="port.id" cellkey="id">
                    {{ port.id }}
                </TableCell>
                <TableCell :cellvalue="port.name.portName" cellkey="name">
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
        <button class="delete" v-on:click="erase">DELETE</button>
        <button class="update" v-on:click="update">UPDATE</button>
    </div>
    
</template>

<script lang="ts">
import { UllUUID } from '../../../utils/ull-uuid';
import { Port } from '../../../domain/entity/port';
import { PortName } from '../../../domain/valueobject/portName';
import { Coordinate } from '../../../domain/valueobject/coordinate';
import { usePortStateManager } from '../stateManager/portStateManager';
import { DateUtils } from '../../../utils/dateUtils';
import { toast } from 'vue3-toastify';
import { ref, onMounted, defineAsyncComponent } from 'vue';
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
                map = L.map(mapContainer.value).setView([0, 0], 4);

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
            mapContainer,
            latitude,
            longitude,
        };
    },
    data() {
        return {
            id: '',
            ports: [] as Port[],
            portStateManager: usePortStateManager(),
        };
    },
    components: {
        Table: defineAsyncComponent(() => import("../table/Table.vue")),
        TableHead: defineAsyncComponent(() => import("../table/TableHead.vue")),
        TableCell: defineAsyncComponent(() => import("../table/TableCell.vue")),
    },
    async created() {
        const currentURL = window.location.href;
        const id = currentURL.split("/").pop();
        if (id !== undefined) {
            this.id = id;
        }
        await this.portStateManager.find(new UllUUID(this.id));
        this.ports = this.portStateManager.ports;
    },
    methods: {
        dateToString(date: Date) {
            return DateUtils.toString(date);
        },
        async update() {
            const uuid = new UllUUID(this.id);
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
            alert('You can not leave any field empty');
        },
        async erase() {
            if (confirm('Are you sure you want to delete this port?')) {
                try {
                    await this.portStateManager.delete(new UllUUID(this.id));
                    this.showNotification('Success', 'Ports deleted successfully');
                    window.location.href = '/accesses';
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
    }
}
</script>
<style scoped>
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
    .map-container {
        border: white solid 5px;
        margin-left: 11%;
        height: 400px;
    }
    .afterTable {
        position: fixed;
        bottom: 0;
        margin-bottom: 1%;
        width: 90%;
        display: flex;
        flex-direction: row;
        margin-left: 5%;
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
