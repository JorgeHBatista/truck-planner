<template>
    <h1>
        Accesses
    </h1>
    <div class="form">
        <div class="filter">Filter:</div>
        <input type="text" v-model="search">
        <div class="filter">Results per page:</div>
        <input type="number" v-model="pageRequest.size">
        <Table>
            <template #thead>
                <TableHead>Identifier</TableHead>
                <TableHead>Truck identifier</TableHead>
                <TableHead>Port identifier</TableHead>
                <TableHead>Entry date</TableHead>
                <TableHead>Exit date</TableHead>
                <TableHead>Entry point</TableHead>
                <TableHead>Exit point</TableHead>
                <TableHead>Identification type</TableHead>
            </template>

            <template #tbody>
                <template v-for="access in filterPosts(accessStateManager.access)" :key="access.id">
                    <tr>
                    <TableCell :cellvalue="access.id" cellkey="id">
                        {{ access.id }}
                    </TableCell>
                    <TableCell :cellvalue="access.truckID" cellkey="truck">
                        <router-link class="link" :to="{path: `/trucks/${access.truckID}`}">
                            {{ access.truckID }}
                        </router-link>
                    </TableCell>
                    <TableCell :cellvalue="access.portID" cellkey="port">
                        <router-link class="link" :to="{path: `/ports/${access.portID}`}">
                            {{ access.portID }}
                        </router-link>
                    </TableCell>
                    <TableCell :cellvalue="access.entryDate" cellkey="entryDate">
                        {{ dateToString(access.entryDate) }}
                    </TableCell>
                    <TableCell :cellvalue="access.exitDate" cellkey="exitDate">
                        {{ dateToString(access.exitDate) }}
                    </TableCell>
                    <TableCell :cellvalue="access.entryPoint" cellkey="entryPoint">
                        {{ entryPoint.fromOrdinal(access.entryPoint) }}
                    </TableCell>
                    <TableCell :cellvalue="access.exitPoint" cellkey="exitPoint">
                        {{ exitPoint.fromOrdinal(access.exitPoint) }}
                    </TableCell>
                    <TableCell :cellvalue="access.identificationType" cellkey="identificationType">
                        {{ identificationType.fromOrdinal(access.identificationType) }}
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

import { Access } from '../../../domain/entity/access';
import { useAccessStateManager } from '../stateManager/accessStateManager';
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
                size: 5,
            },
            accessStateManager: useAccessStateManager(),
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
                    if (this.pageRequest.page < this.accessStateManager.access.length / this.pageRequest.size - 1) {
                        this.pageRequest.page++;
                    }
                    break;
                case 4:
                    if (this.accessStateManager.access.length % this.pageRequest.size === 0) {
                        this.pageRequest.page = this.accessStateManager.access.length / this.pageRequest.size - 1;
                    } else {
                        this.pageRequest.page = Math.floor(this.accessStateManager.access.length / this.pageRequest.size);
                    }
                    
            }
            this.pages[2] = this.pageRequest.page;
        },
        filterPosts(accesses: any): Access[] {
            let accessFiltered: Access[] = [];
            accessFiltered = accesses.filter((access: Access) => {
                if (access.id.toString().toUpperCase().includes(this.search.toUpperCase())
                    || access.truckID.toString().toUpperCase().includes(this.search.toUpperCase())
                    || access.portID.toString().toUpperCase().includes(this.search.toUpperCase())
                    || this.dateToString(access.entryDate).toUpperCase().includes(this.search.toUpperCase())
                    || this.dateToString(access.exitDate).toUpperCase().includes(this.search.toUpperCase())
                    || this.entryPoint.fromOrdinal(access.entryPoint).toUpperCase().includes(this.search.toUpperCase())
                    || this.exitPoint.fromOrdinal(access.exitPoint).toUpperCase().includes(this.search.toUpperCase())
                    || this.identificationType.fromOrdinal(access.identificationType).toUpperCase().includes(this.search.toUpperCase())) {
                    return access;
                }
            });
            return this.pagePosts(accessFiltered);
        },
        pagePosts(access: any): Access[] {
            const pageableAccess: Access[] = [];
            const initialPage = this.pageRequest.page * this.pageRequest.size;
            for (let i = initialPage; i < this.pageRequest.size + initialPage; i++) {
                if (access[i] != null) {
                    pageableAccess.push(access[i]);
                }
            }
            return pageableAccess;
        },
        async start() {
            try {
                await this.accessStateManager.findAll(this.pageRequest);
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
            }
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all access?')) {
                try {
                    await this.accessStateManager.deleteAll();
                    this.showNotification('Success', 'accessStateManager deleted successfully');
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
