<template>
    <h1>
        Truck {{ id }}
    </h1>
    <br><br>
    <Table class="table">
        <template #thead>
            <TableHead>Identifier</TableHead>
            <TableHead>Plate</TableHead>
            <TableHead>Brand</TableHead>
            <TableHead>Driver</TableHead>
            <TableHead>Operation</TableHead>
        </template>

        <template #tbody>
            <template v-for="truck in trucks" :key="truck.id">
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
        <button class="delete" v-on:click="erase">DELETE</button>
        <button class="update" v-on:click="update">UPDATE</button>
    </div>
    
</template>

<script lang="ts">
import { UllUUID } from '../../../utils/ull-uuid';
import { Truck } from '../../../domain/entity/truck';
import { Plate } from '../../../domain/valueobject/plate';
import { Brand } from '../../../domain/valueobject/brand';
import { Driver } from '../../../domain/valueobject/driver';
import { useTruckStateManager } from '../stateManager/truckStateManager';
import { defineAsyncComponent } from "vue";
import { DateUtils } from '../../../utils/dateUtils';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            id: '',
            trucks: [] as Truck[],
            truckStateManager: useTruckStateManager(),
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
        await this.truckStateManager.find(new UllUUID(this.id));
        this.trucks = this.truckStateManager.trucks;
    },
    methods: {
        dateToString(date: Date) {
            return DateUtils.toString(date);
        },
        async update() {
            const uuid = new UllUUID(this.id);
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
        },
        async erase() {
            if (confirm('Are you sure you want to delete this truck?')) {
                try {
                    await this.truckStateManager.delete(new UllUUID(this.id));
                    this.showNotification('Success', 'Trucks deleted successfully');
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
