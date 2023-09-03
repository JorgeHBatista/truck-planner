<template>
    <h1>Insert your access</h1>
    <form @submit.prevent="submitAccess">
        <label>Truck ID:</label>
        <input type="text" required v-model="truckID">
        <label>Port ID:</label>
        <input type="text" required v-model="portID">
        <label>Entry date:</label>
        <input type="datetime-local" required v-model="entryDate">
        <label>Exit date:</label>
        <input type="datetime-local" required v-model="exitDate">
        <label>Select the entry point</label>
        <select required v-model="entryPoint">
            <option value="GATE1">Gate 1</option>
            <option value="GATE2">Gate 2</option>
            <option value="GATE3">Gate 3</option>
            <option value="GATE4">Gate 4</option>
            <option value="GATE5">Gate 5</option>
            <option value="GATE6">Gate 6</option>
            <option value="OCEAN">Ocean</option>
        </select>
        <label>Select the exit point</label>
        <select required v-model="exitPoint">
            <option value="GATE1">Gate 1</option>
            <option value="GATE2">Gate 2</option>
            <option value="GATE3">Gate 3</option>
            <option value="GATE4">Gate 4</option>
            <option value="GATE5">Gate 5</option>
            <option value="GATE6">Gate 6</option>
            <option value="OCEAN">Ocean</option>
        </select>
        <label>Select the identification type</label>
        <select required v-model="identificationType">
            <option value="QR">QR</option>
            <option value="CARD">Card</option>
            <option value="UNKNOWN">Unknown</option>
        </select>
        <div class="submit">
            <button>Submit access</button>
        </div>
    </form>
</template>

<script lang="ts">

import { UllUUID } from '../../../utils/ull-uuid';
import { Access } from '../../../domain/entity/access';
import { EntryPoint} from '../../../domain/valueobject/entryPoint';
import { ExitPoint} from '../../../domain/valueobject/exitPoint';
import { IdentificationType} from '../../../domain/valueobject/identificationType';
import { useAccessStateManager } from '../stateManager/accessStateManager';
import { useTruckStateManager } from '../stateManager/truckStateManager';
import { usePortStateManager } from '../stateManager/portStateManager';


import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
    data() {
        return {
            truckID: '',
            portID: '',
            entryDate: '',
            exitDate: '',
            entryPoint: '',
            exitPoint: '',
            identificationType: '',
        };
    },
    methods: {
        async submitAccess() {
            try {
                const truckID = new UllUUID(this.truckID);
                try {
                    await useTruckStateManager().find(truckID)
                } catch (error) {
                    throw new Error('The given truck does not exists in database');
                }
                const portID = new UllUUID(this.portID);
                try {
                    await usePortStateManager().find(portID)
                } catch (error) {
                    throw new Error('The given port does not exists in database');
                }
                const entryDate = new Date(this.entryDate);
                const exitDate = new Date(this.exitDate);
                const entryPoint = EntryPoint[this.entryPoint as keyof typeof EntryPoint];
                const exitPoint = ExitPoint[this.exitPoint as keyof typeof ExitPoint];
                const identificationType = IdentificationType[this.identificationType as keyof typeof IdentificationType];
                const access = new Access(UllUUID.random(), entryDate, exitDate, entryPoint, exitPoint, identificationType, truckID, portID);
                await useAccessStateManager().save(access);
                this.showNotification('Success', 'Access inserted successfully');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                console.error(error);
            }
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                toast.error(message + ', access not inserted', {
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
};
</script>

<style>
    h1 {
        color: 000033;
        text-align: center;
        margin-bottom: 30px;
        margin-top: 30px;
    }
    form {
        max-width: 420px;
        margin: 30px auto;
        background-color: #FFF;
        text-align: left;
        padding: 40px;
        border-radius: 10px;
    }
    label {
        color: #aaa;
        display: inline-block;
        margin: 25px 0 15px;
        font-size: 0.6em;
        text-transform: uppercase;
        letter-spacing: 1px;
        font-weight: bold;
    }
    input, select {
        display: block;
        padding: 10px 6px;
        width: 100%;
        box-sizing: border-box;
        border: none;
        border-bottom: 1px solid #ddd;
        color: #555;
        background-color: #f2f2f2 !important;
        border-radius: 15px !important;
    }
    button {
        width: 100%;
        height: 100%;
    }
    .submit {
        margin-left : 12.5%;   
        width: 70%;
        background: lightblue;
        border: 0;
        padding: 10px 20px;
        margin-top: 20px;
        color: white;
        border-radius: 20px;
        text-align: center;
    }
</style>