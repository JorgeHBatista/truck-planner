<template>
    <h1>
        Vessel {{ id }}
    </h1>
    <br><br>
    <h2>
        Scales
    </h2>
    <Table>
        <template #thead>
            <TableHead>Identifier</TableHead>
            <TableHead>Starting time</TableHead>
            <TableHead>Finishing time</TableHead>
            <TableHead>Operations</TableHead>
        </template>
        <template #tbody>
            <template v-for="vessel in vessels" :key="vessel.id.toString()">
                <template v-for="scale in vessel.scales" :key="scale.id.toString()">
                    <tr>
                        <TableCell :cellvalue="scale.id" cellkey="id">
                            {{ scale.id }}
                        </TableCell>
                        <TableCell :cellvalue="scale.startingTime" cellkey="startingTime">
                            {{ dateToString(scale.startingTime) }}
                        </TableCell>
                        <TableCell :cellvalue="scale.finishingTime" cellkey="finishingTime">
                            {{ dateToString(scale.finishingTime) }}
                        </TableCell>
                        <TableCell>
                            <button class="expand">
                                <router-link class="link" :to="{path: `/scales/${scale.id}`}">
                                See operations
                                </router-link>
                            </button>
                        </TableCell>
                    </tr>
                </template>
            </template>
        </template>
    </Table>
    <div class="afterTable footer">
        <button class="delete" v-on:click="deleteAll">DELETE ALL</button>
    </div>
    
</template>

<script lang="ts">
import { UllUUID } from '../../../utils/ull-uuid';
import { Vessel } from '../../../domain/entity/vessel';
import { useVesselStateManager } from '../stateManager/vesselStateManager';
import { useScaleStateManager } from '../stateManager/scaleStateManager';
import { defineAsyncComponent } from "vue";
import { DateUtils } from '../../../utils/dateUtils';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            id: '',
            vessels: [] as Vessel[],
            vesselStateManager: useVesselStateManager(),
            scaleStateManager: useScaleStateManager(),
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
        await this.vesselStateManager.find(new UllUUID(this.id));
        this.vessels = this.vesselStateManager.vessels;
    },
    methods: {
        dateToString(date: Date) {
            return DateUtils.toString(date);
        },
        async deleteAll() {
            if (confirm('Are you sure you want to delete all scales?')) {
                try {
                    await this.scaleStateManager.deleteAll();
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
    .expand {
        width: 90%;
        background-color: lightgreen;
        color: green;
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
</style>
