<template>
    <h1>Insert your truck</h1>
    <form @submit.prevent="submitTruck">
        <label>Plate:</label>
        <input type="text" required v-model="plate">
        <label>Brand:</label>
        <input type="text" required v-model="brand">
        <label>Driver's full name:</label>
        <input type="text" required v-model="driver">
        <label>Choose the type of operation to do:</label>
        <select required v-model="operation">
            <option value="unload">Unload</option>
            <option value="load">Load</option>
        </select>
        <div class="submit">
            <button>Submit truck</button>
        </div>
    </form>
</template>

<script lang="ts">

import { UllUUID } from '../../../utils/ull-uuid';
import { Truck } from '../../../domain/entity/truck';
import { Plate } from '../../../domain/valueobject/plate';
import { Brand } from '../../../domain/valueobject/brand';
import { Driver } from '../../../domain/valueobject/driver';
import { useTruckStateManager } from '../stateManager/truckStateManager';

import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
    data() {
        return {
            plate: '',
            brand: '',
            driver: '',
            operation: 'unload',
        };
    },
    methods: {
        async submitTruck() {
            try {
                const plate = new Plate(this.plate);
                const brand = new Brand(this.brand);
                const driver = new Driver(this.driver);
                const isUnload = this.operation == 'unload' ? true : false;
                const truck = new Truck(UllUUID.random(), plate, brand, driver, isUnload);
                await useTruckStateManager().save(truck);
                this.showNotification('Success', 'Truck inserted successfully');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                console.error(error);
            }
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                toast.error(message + ', truck not inserted', {
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