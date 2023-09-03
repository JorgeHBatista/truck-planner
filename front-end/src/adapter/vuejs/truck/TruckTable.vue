<template>
    <h1>
        Trucks
    </h1>
    <div class="form">
        <div class="filter">Filter:</div>
        <input type="text" v-model="search">
        <div class="filter">Results per page:</div>
        <input type="number" v-model="pageRequest.size">
        <Table class="table">
            <template #thead>
                <TableHead>Identifier</TableHead>
                <TableHead>Plate</TableHead>
                <TableHead>Brand</TableHead>
                <TableHead>Driver</TableHead>
                <TableHead>Operation</TableHead>
            </template>

            <template #tbody>
                <template v-for="truck in filterPosts(truckStateManager.trucks)" :key="truck.id">
                    <tr>
                    <TableCell :cellvalue="truck.id" cellkey="id">
                        {{ truck.id }}
                    </TableCell>
                    <TableCell :cellvalue="truck.plate.plate" cellkey="plate">
                        {{ truck.plate.plate }}
                    </TableCell>
                    <TableCell :cellvalue="truck.brand.brand" cellkey="brand">
                        {{ truck.brand.brand }}
                    </TableCell>
                    <TableCell :cellvalue="truck.driver.driver" cellkey="driver">
                        {{ truck.driver.driver }}
                    </TableCell>
                    <TableCell :cellvalue="truck.isUnload" cellkey="isUnload">
                        {{ truck.isUnload.toString() == "true" ? 'Unload' : 'Load' }}
                    </TableCell>
                    </tr>
                </template>
            </template>
        </Table>
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

import { Truck } from '../../../domain/entity/truck';
import { Plate } from '../../../domain/valueobject/plate';
import { Brand } from '../../../domain/valueobject/brand';
import { Driver } from '../../../domain/valueobject/driver';
import { useTruckStateManager } from '../stateManager/truckStateManager';
import { defineAsyncComponent } from "vue";
import { UllUUID } from '../../../utils/ull-uuid';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            pageRequest: {
                page: 0,
                size: 5,
            },
            truckStateManager: useTruckStateManager(),
            search: '',
            pages: ['<<', '<', 0, '>', '>>'],
        };
    },
    components: {
        Table: defineAsyncComponent(() => import("../table/Table.vue")),
        TableHead: defineAsyncComponent(() => import("../table/TableHead.vue")),
        TableCell: defineAsyncComponent(() => import("../table/TableCell.vue")),
    },
    methods: {
        async handlePagination(index: number) {
            try {
                await this.truckStateManager.findAll(this.pageRequest);
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
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
                    if (this.pageRequest.page < this.truckStateManager.trucks.length / this.pageRequest.size - 1) {
                        this.pageRequest.page++;
                    }
                    break;
                case 4:
                    if (this.truckStateManager.trucks.length % this.pageRequest.size === 0) {
                        this.pageRequest.page = this.truckStateManager.trucks.length / this.pageRequest.size - 1;
                    } else {
                        this.pageRequest.page = Math.floor(this.truckStateManager.trucks.length / this.pageRequest.size);
                    }
                    
            }
            this.pages[2] = this.pageRequest.page;
        },
        filterPosts(trucks: any): Truck[] {
            let trucksFiltered: Truck[] = [];
            trucksFiltered = trucks.filter((truck: Truck) => {
                if (truck.id.toString().toUpperCase().includes(this.search.toUpperCase())
                    || truck.plate.plate.toUpperCase().includes(this.search.toUpperCase())
                    || truck.brand.brand.toUpperCase().includes(this.search.toUpperCase())
                    || truck.driver.driver.toUpperCase().includes(this.search.toUpperCase())) {
                    return truck;
                }
            });
            return this.pagePosts(trucksFiltered);
        },
        pagePosts(trucks: any): Truck[] {
            const pageableTrucks: Truck[] = [];
            const initialPage = this.pageRequest.page * this.pageRequest.size;
            for (let i = initialPage; i < this.pageRequest.size + initialPage; i++) {
                if (trucks[i] != null) {
                    pageableTrucks.push(trucks[i]);
                }
            }
            return pageableTrucks;
        },
        async start() {
            try {
                await this.truckStateManager.findAll(this.pageRequest);
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async update() {
            try {
                const id = prompt('Enter the id of the truck you want to update');
                if (id != null) {
                    const uuid = new UllUUID(id);
                    const plateString = prompt('Enter the new plate of the truck');
                    if (plateString != null) {
                        const plate = new Plate(plateString);
                        const brandString = prompt('Enter the new brand of the truck');
                        if (brandString != null) {
                            const brand = new Brand(brandString);
                            const driverString = prompt('Enter the new driver of the truck');
                            if (driverString != null) {
                                const driver = new Driver(driverString);
                                const operation = prompt('Enter the operation of the truck (load / unload)');
                                if (operation != null) {
                                    let isUnload = true;
                                    if (operation.match(/^(load|unload)$/i)) {
                                        isUnload = operation.match(/^unload$/i) ? true : false;
                                        const truck = new Truck(uuid, plate, brand, driver, isUnload);
                                        await this.truckStateManager.update(uuid, truck);
                                        this.showNotification('Success', 'Truck updated successfully'); 
                                    } else {
                                        alert('You have to enter a valid operation (load / unload)');
                                    }
                                } return;
                            }
                        }
                    }
                    alert('You can not leave any field empty');
                }
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async erase() {
            const id = prompt('Enter the id of the truck you want to delete');
            if (id != null) {
                let uuid: UllUUID;
                try {
                    uuid = new UllUUID(id);
                    await this.truckStateManager.delete(uuid);
                    this.showNotification('Success', 'Truck deleted successfully');
                } catch (error) {
                    if (error instanceof Error) {
                        if (error.message === "") error.message = "Truck not deleted, incorrect id";
                    }
                    this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                }
            } else {
                alert('You have to enter an id');
            }
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all trucks?')) {
                try {
                    await this.truckStateManager.deleteAll();
                    this.showNotification('Success', 'Trucks deleted successfully');
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
    .afterTable {
        position: fixed;
        bottom: 0;
        margin-bottom: 1%;
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
