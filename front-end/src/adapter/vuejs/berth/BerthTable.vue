<template>
    <h1>
        Berths
    </h1>
    <div class="form">
        <div class="filter">Filter:</div>
        <input type="text" v-model="search">
        <div class="filter">Results per page:</div>
        <input type="number" v-model="pageRequest.size">
        <Table>
            <template #thead>
                <TableHead>Identifier</TableHead>
                <TableHead>Vessel identifier</TableHead>
                <TableHead>Port identifier</TableHead>
            </template>

            <template #tbody>
                <template v-for="berth in filterPosts(berthStateManager.berth)" :key="berth.id">
                    <tr>
                    <TableCell :cellvalue="berth.id" cellkey="id">
                        {{ berth.id }}
                    </TableCell>
                    <TableCell :cellvalue="berth.VesselID" cellkey="truck">
                        <router-link class="link" :to="{path: `/vessels/${berth.VesselID}`}">
                            {{ berth.VesselID }}
                        </router-link>
                    </TableCell>
                    <TableCell :cellvalue="berth.PortID" cellkey="port">
                        <router-link class="link" :to="{path: `/ports/${berth.PortID}`}">
                            {{ berth.PortID }}
                        </router-link>
                    </TableCell>
                    </tr>
                </template>
            </template>
        </Table>
        <div class="afterTable footer">
            <button class="delete" v-on:click="deleteAll">DELETE ALL</button>
            <div class="paginationLine" v-for="(button, index) in pages" :key=index>
                <button class="paginationButton" v-on:click="handlePagination(index)">
                    {{ typeof(button)==="number" ? button + 1 : button }}
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">

import { Berth } from '../../../domain/entity/berth';
import { useBerthStateManager } from '../stateManager/berthStateManager';
import { defineAsyncComponent } from "vue";
import { toast } from 'vue3-toastify';
import { DateUtils } from '../../../utils/dateUtils';
import { EntryPointVO } from '../../../domain/valueobject/entryPoint';
import { ExitPointVO } from '../../../domain/valueobject/exitPoint';
import { IdentificationTypeVO } from '../../../domain/valueobject/identificationType';

export default {
    data() {
        return {
            pageRequest: {
                page: 0,
                size: 7,
            },
            berthStateManager: useBerthStateManager(),
            search: '',
            pages: ['<<', '<', 0, '>', '>>'],
            entryPoint: EntryPointVO,
            exitPoint: ExitPointVO,
            identificationType: IdentificationTypeVO,
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
                    if (this.pageRequest.page < this.berthStateManager.berth.length / this.pageRequest.size - 1) {
                        this.pageRequest.page++;
                    }
                    break;
                case 4:
                    if (this.berthStateManager.berth.length % this.pageRequest.size === 0) {
                        this.pageRequest.page = this.berthStateManager.berth.length / this.pageRequest.size - 1;
                    } else {
                        this.pageRequest.page = Math.floor(this.berthStateManager.berth.length / this.pageRequest.size);
                    }
                    
            }
            this.pages[2] = this.pageRequest.page;
        },
        filterPosts(berths: any): Berth[] {
            let berthFiltered: Berth[] = [];
            berthFiltered = berths.filter((berth: Berth) => {
                if (berth.id.toString().toUpperCase().includes(this.search.toUpperCase())
                    || berth.VesselID.toString().toUpperCase().includes(this.search.toUpperCase())
                    || berth.PortID.toString().toUpperCase().includes(this.search.toUpperCase())) {
                    return berth;
                }
            });
            return this.pagePosts(berthFiltered);
        },
        pagePosts(berth: any): Berth[] {
            const pageableBerth: Berth[] = [];
            const initialPage = this.pageRequest.page * this.pageRequest.size;
            for (let i = initialPage; i < this.pageRequest.size + initialPage; i++) {
                if (berth[i] != null) {
                    pageableBerth.push(berth[i]);
                }
            }
            return pageableBerth;
        },
        async start() {
            try {
                await this.berthStateManager.findAll(this.pageRequest);
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all berth?')) {
                try {
                    await this.berthStateManager.deleteAll();
                    this.showNotification('Success', 'berthStateManager deleted successfully');
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
        width: 80%;
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
</style>
