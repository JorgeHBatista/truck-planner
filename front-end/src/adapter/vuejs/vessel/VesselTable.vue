<template>
    <h1>
        Vessels
    </h1>
    <div v-if="isLoading">
        <img src="../../../../public/loading.gif" alt="Loading...">
        <h4>We are retrieving the vessels... Please wait</h4>
    </div>
    <div v-else class="form">
        <div class="filter">Filter:</div>
        <input type="text" v-model="search">
        <div class="filter">Results per page:</div>
        <input type="number" v-model="pageRequest.size">
        <Table class="table">
            <template #thead>
                <TableHead>Identifier</TableHead>
                <TableHead>Scales</TableHead>
            </template>

            <template #tbody>
                <template v-for="vessel in filterPosts(vesselStateManager.vessels)" :key="vessel.id">
                    <tr>
                    <TableCell :cellvalue="vessel.id" cellkey="id">
                        {{ vessel.id }}
                    </TableCell>
                    <TableCell>
                        <button class="expand">
                            <router-link class="link" :to="{path: `/vessels/${vessel.id}`}">
                                See scales
                            </router-link>
                        </button>
                    </TableCell>
                    </tr>
                </template>
            </template>
        </Table>
        <div class="afterTable">
            <button class="delete" v-on:click="deleteAll">DELETE ALL</button>
            <button class="delete" v-on:click="erase">DELETE</button>
            <div class="paginationLine" v-for="(button, index) in pages" :key=index>
                <button class="paginationButton" v-on:click="handlePagination(index)">
                    {{ typeof(button)==="number" ? button + 1 : button }}
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">

import { Vessel } from '../../../domain/entity/vessel';
import { CargoVO } from '../../../domain/valueobject/cargo';
import { OperationTypeVO } from '../../../domain/valueobject/operationType';
import { useVesselStateManager } from '../stateManager/vesselStateManager';
import { defineAsyncComponent } from "vue";
import { UllUUID } from '../../../utils/ull-uuid';
import { DateUtils } from '../../../utils/dateUtils';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            pageRequest: {
                page: 0,
                size: 5,
            },
            vesselStateManager: useVesselStateManager(),
            search: '',
            pages: ['<<', '<', 0, '>', '>>'],
            cargo: CargoVO,
            operationType: OperationTypeVO,
            isLoading: true,
        };
    },
    components: {
        Table: defineAsyncComponent(() => import("../table/Table.vue")),
        TableHead: defineAsyncComponent(() => import("../table/TableHead.vue")),
        TableCell: defineAsyncComponent(() => import("../table/TableCell.vue")),
    },
    methods: {
        dateToString(date: Date) {
            return DateUtils.toString(date);
        },
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
                    if (this.pageRequest.page < this.vesselStateManager.vessels.length / this.pageRequest.size - 1) {
                        this.pageRequest.page++;
                    }
                    break;
                case 4:
                    if (this.vesselStateManager.vessels.length % this.pageRequest.size === 0) {
                        this.pageRequest.page = this.vesselStateManager.vessels.length / this.pageRequest.size - 1;
                    } else {
                        this.pageRequest.page = Math.floor(this.vesselStateManager.vessels.length / this.pageRequest.size);
                    }
                    
            }
            this.pages[2] = this.pageRequest.page;
        },
        filterPosts(vessels: any): Vessel[] {
            let vesselsFiltered: Vessel[] = [];
            vesselsFiltered = vessels.filter((vessel: Vessel) => {
                if (vessel.id.toString().toUpperCase().includes(this.search.toUpperCase())) {
                    return vessel;
                }
            });
            return this.pagePosts(vesselsFiltered);
        },
        pagePosts(vessels: any): Vessel[] {
            const pageableVessels: Vessel[] = [];
            const initialPage = this.pageRequest.page * this.pageRequest.size;
            for (let i = initialPage; i < this.pageRequest.size + initialPage; i++) {
                if (vessels[i] != null) {
                    pageableVessels.push(vessels[i]);
                }
            }
            return pageableVessels;
        },
        async start() {
            try {
                await this.vesselStateManager.findAll(this.pageRequest);
                this.isLoading = false;
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async erase() {
            const id = prompt('Enter the id of the vessel you want to delete');
            if (id != null) {
                let uuid: UllUUID;
                try {
                    uuid = new UllUUID(id);
                    await this.vesselStateManager.delete(uuid);
                    this.showNotification('Success', 'Vessel deleted successfully');
                } catch (error) {
                    if (error instanceof Error) {
                        if (error.message === "") error.message = "Vessel not deleted, incorrect id";
                    }
                    this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                }
            } else {
                alert('You have to enter an id');
            }
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all vessels?')) {
                try {
                    await this.vesselStateManager.deleteAll();
                    this.showNotification('Success', 'Vessels deleted successfully');
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
    .loader {
        display: block;
        margin-left: auto;
        margin-right: auto;
        width: 50%;
    }
    .afterTable {
        position: fixed;
        bottom: 0;
        margin-bottom: 1%;
        width: 85%;
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
    .expand {
        width: 50%;
        background-color: lightgreen;
        color: green;
    }
    .hidden_row {
        display: none;
    }
</style>
