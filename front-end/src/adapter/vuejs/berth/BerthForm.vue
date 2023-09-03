<template>
    <h1>Insert your berth</h1>
    <form @submit.prevent="submitBerth">
        <label>Vessel ID:</label>
        <input type="text" required v-model="vesselID">
        <label>Port ID:</label>
        <input type="text" required v-model="portID">
        <div class="submit">
            <button>Submit berth</button>
        </div>
    </form>
</template>

<script lang="ts">

import { UllUUID } from '../../../utils/ull-uuid';
import { Berth } from '../../../domain/entity/berth';
import { useBerthStateManager } from '../stateManager/berthStateManager';
import { useVesselStateManager } from '../stateManager/vesselStateManager';
import { usePortStateManager } from '../stateManager/portStateManager';

import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
    data() {
        return {
            vesselID: '',
            portID: '',
            vesselStateManager: useVesselStateManager(),
            portStateManager: usePortStateManager(),
        };
    },
    methods: {
        async submitBerth() {
            try {
                const vesselID = new UllUUID(this.vesselID);
                try {
                    await this.vesselStateManager.find(vesselID)
                } catch (error) {
                    throw new Error('The given vessel does not exists in database');
                }
                const portID = new UllUUID(this.portID);
                try {
                    await this.portStateManager.find(portID)
                } catch (error) {
                    throw new Error('The given port does not exists in database');
                }
                const retrievedVessel = this.vesselStateManager.vessels[0];
                const idVessel = new UllUUID(retrievedVessel.id.toString());
                const retrievedPort = this.portStateManager.ports[0];
                const idPort = new UllUUID(retrievedPort.id.toString());
                const berth = new Berth(UllUUID.random(), idPort, idVessel);
                await useBerthStateManager().save(berth);
                this.showNotification('Success', 'Berth inserted successfully');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                console.error(error);
            }
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                toast.error(message + ', berth not inserted', {
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