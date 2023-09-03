<template>
    <h1>
        Scale {{  id }}
    </h1>
    <br><br>
    <h2>
        Operations
    </h2>
    <Table>
        <template #thead>
            <TableHead>Identifier</TableHead>
            <TableHead>Cargo</TableHead>
            <TableHead>Type</TableHead>
            <TableHead>Quantity</TableHead>
        </template>
        <template #tbody>
            <template v-for="scale in scales" :key="scale.id.toString()">
                <template v-for="operation in scale.operations" :key="operation.id.toString()">
                    <tr>
                        <TableCell :cellvalue="operation.id" cellkey="id">
                            {{ operation.id }}
                        </TableCell>
                        <TableCell :cellvalue="operation.cargo" cellkey="cargo">
                            {{ cargo.fromOrdinal(operation.cargo).replace(/_/gi, " ") }}
                        </TableCell>
                        <TableCell :cellvalue="operation.operationType" cellkey="operationType">
                            {{ operationType.fromOrdinal(operation.operationType) }}
                        </TableCell>
                        <TableCell :cellvalue="operation.quantity" cellkey="operationType">
                            {{ operation.quantity.value }}
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
import { Scale } from '../../../domain/entity/scale';
import { useScaleStateManager } from '../stateManager/scaleStateManager';
import { defineAsyncComponent } from "vue";
import { CargoVO } from '../../../domain/valueobject/cargo';
import { OperationTypeVO } from '../../../domain/valueobject/operationType';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            id: '',
            scales: [] as Scale[],
            scaleStateManager: useScaleStateManager(),
            cargo: CargoVO,
            operationType: OperationTypeVO,
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
        await this.scaleStateManager.find(new UllUUID(this.id));
        this.scales = this.scaleStateManager.scales;
    },
    methods: {
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
};
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
</style>