<template>
    <h1>
        Insert vessel
    </h1>
    <div class="formContainer">
        <form @submit.prevent>
            <h4>Operation form</h4>
            <h5>{{ "Operations added to current scale: " + operations.length }}</h5>
            <label>Cargo:</label>
            <select required v-model="cargoValue">
                <option v-for="cargo in Object.keys(Cargo).filter(cargo => isNaN(+cargo))"
                    :key="cargo" :v-model="cargoValue" >
                        {{ cargo }}
                </option>
            </select>
            <label>Type of the operation:</label>
            <select required v-model="operationTypeValue">
                <option v-for="operationType in Object.keys(OperationType).filter(operationType => isNaN(+operationType))"
                    :key="operationType" :v-model="operationTypeValue" >
                        {{ operationType }}
                </option>
            </select>
            <label>Quantity:</label>
            <input type="number" required v-model="quantityValue">
            <div class="submit">
                    <button class="submitButton" v-on:click="addOperation">Append</button>
                    <button class="resetButton" v-on:click="resetOperations">Reset</button>
            </div>
        </form>

        <form class="form" @submit.prevent>
            <h4>Scale form</h4>
            <h5>{{ "Scales added to current vessel: " + scales.length }}</h5>
            <label>Starting time:</label>
            <input type="datetime-local" required v-model="entryDate">
            <label>Finishing time:</label>
            <input type="datetime-local" required v-model="exitDate">
            <br><h6>Operations appended:</h6>
            <div class="showOperations" v-for="op, index in operations" :key="op.id.toString()">
                <div class="textOperations">
                    {{  index+1 + ".- " + CargoVO.fromOrdinal(op.cargo).replace(/_/gi, ' ')
                        + ", " + OperationTypeVO.fromOrdinal(op.operationType)
                        + ", " + op.quantity.value
                    }}
                </div>
            </div>
            <div class="submit">
                    <button class="submitButton" v-on:click="addScale(operations)">Append</button>
                    <button class="resetButton" v-on:click="resetScales">Reset</button>
            </div>
        </form>

        <form class="form" @submit.prevent>
            <h4>Vessel form</h4>
            <br><h6>Scales appended:</h6>
            <div class="showOperations" v-for="scale, index in scales" :key="scale.id.toString()">
                <div class="textOperations">
                    {{  index+1 + ".- " + dateToString(scale.startingTime)
                        + ", " + dateToString(scale.finishingTime)
                        + ", Operations:" + scale.operations.map(op => " [ "
                        + CargoVO.fromOrdinal(op.cargo).replace(/_/gi, ' ') + ", " +
                        OperationTypeVO.fromOrdinal(op.operationType) + ", " +
                        op.quantity.value + "]")
                    }}
                </div>
            </div>
            <div class="submit">
                    <button class="submitButton" v-on:click="submitVessel(scales)">Submit</button>
                    <button class="resetButton" v-on:click="resetScales">Reset</button>
            </div>
        </form>
    </div>
    
</template>
<script lang="ts">
import { UllUUID } from '../../../utils/ull-uuid';
import { Cargo } from '../../../domain/valueobject/cargo';
import { CargoVO } from '../../../domain/valueobject/cargo';
import { OperationType } from '../../../domain/valueobject/operationType';
import { OperationTypeVO } from '../../../domain/valueobject/operationType';
import { Operation } from '../../../domain/entity/operation';
import { Quantity } from '../../../domain/valueobject/quantity';
import { Scale } from '../../../domain/entity/scale';
import { DateUtils } from '../../../utils/dateUtils';
import { Vessel } from '../../../domain/entity/vessel';
import { useVesselStateManager } from '../stateManager/vesselStateManager';
import { useScaleStateManager } from '../stateManager/scaleStateManager';
import { useOperationStateManager } from '../stateManager/operationStateManager';
import { toast } from 'vue3-toastify';

export default {
    data() {
        return {
            cargoValue: '',
            operationTypeValue: '',
            quantityValue: '',
            Cargo: Cargo,
            CargoVO: CargoVO,
            OperationType: OperationType,
            OperationTypeVO: OperationTypeVO,
            operations: [] as Operation[],
            scales: [] as Scale[],
            entryDate: '',
            exitDate: '',
        };
    },
    methods: {
        dateToString(date: Date) {
            return DateUtils.toString(date);
        },
        addOperation() {
            const cargo = Cargo[this.cargoValue as keyof typeof Cargo];
            const operationType = OperationType[this.operationTypeValue as keyof typeof OperationType];
            const quantity = new Quantity(+this.quantityValue);
            const operation = new Operation(UllUUID.random(), cargo, operationType, quantity); 
            this.operations.push(operation);
        },
        resetOperations() {
            this.operations = [];
        },
        addScale(operations: any) {
            const startingTime = new Date(this.entryDate);
            const finishingTime = new Date(this.exitDate);
            const scale = new Scale(UllUUID.random(), startingTime, finishingTime, operations);
            this.scales.push(scale);
            this.resetOperations();
        },
        resetScales() {
            this.scales = [];
            this.resetOperations();
        },
        async submitVessel(scales: any) {
            await this.submitOperations(scales);
            await this.submitScales(scales);
            try {
                const vessel = new Vessel(UllUUID.random(), scales);
                await useVesselStateManager().save(vessel);
                this.showNotification('Success', 'Vessel inserted successfully');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                console.error(error);
            }
        },
        async submitOperations(scales: Scale[]) {
            try {
                for (let i = 0; i < scales.length; i++) {
                    const operations = scales[i].operations;
                    for (let i = 0; i < operations.length; i++) {
                        await useOperationStateManager().save(operations[i]);
                    }
                }
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'Something went wrong with the operation');
                console.error(error);
            }
        },
        async submitScales(scales: Scale[]) {
            try {
                for (let i = 0; i < scales.length; i++) {
                    await useScaleStateManager().save(scales[i]);
                }
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'Something went wrong with the scale');
                console.error(error);
            }
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                toast.error(message + ', vessel not inserted', {
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
    h4, h5 {
        text-align: center;
    }
    .showOperations {
        margin-top: 5px;
        background-color: #f2f2f2;
        border-radius: 15px;
        padding: 1px;
    }
    .textOperations {
        margin-top: 1px;
        margin-left: 10px;
    }
    .form {
        max-width: 420px;
        margin: 30px auto;
        background-color: #FFF;
        text-align: left;
        padding: 40px;
        border-radius: 10px;
    }
    input, select {
        background-color: #f2f2f2;
        border-radius: 15px;
    }
    .formContainer {
        display: flex;
        justify-content: space-between;
    }
    .submit {
        display: flex;
        justify-content: center;
        vertical-align: middle;
        background-color: transparent;
    }
    .submitButton {
        margin-left : 12.5%;   
        width: 70%;
        background: lightblue;
        border: 0;
        margin-top: 20px;
        height: 75px;
        padding: 0 20px;
        color: white;
        border-radius: 20px;
        text-align: center;
    }
    .resetButton {
        margin-right : 12.5%;   
        width: 70%;
        background: lightcoral;
        border: 0;
        margin-top: 20px;
        padding: 20px;
        color: white;
        border-radius: 20px;
        text-align: center;
    }
    button {
        margin-left: 25px;
        margin-right: 25px;
        height: 75px;
    }
</style>