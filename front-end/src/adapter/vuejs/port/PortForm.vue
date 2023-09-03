<template>
    <h1>Insert your port</h1>
    <h6>(Select the coordinates in the map)</h6>
    <br><br><br><br>
    <div class="row">
        <form @submit.prevent="submitPort" class="col">
            <label>Name:</label>
            <input type="text" required v-model="name">
            <label>Latitude:</label>
            <input type="text" :value=latitudeSetup readonly>
            <label>Longitude:</label>
            <input type="text" :value=longitudeSetup readonly>
            <div class="submit">
                <button>Submit port</button>
            </div>
        </form>

        <div ref="mapContainer" class="map-container col"></div>
    </div>
</template>

<script lang="ts">

import { UllUUID } from '../../../utils/ull-uuid';
import { Port } from '../../../domain/entity/port';
import { PortName } from '../../../domain/valueobject/portName';
import { Coordinate } from '../../../domain/valueobject/coordinate';
import { usePortStateManager } from '../stateManager/portStateManager';
import { ref, onMounted } from 'vue';
import L from 'leaflet';

import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
    setup() {
    const mapContainer = ref(null);
    let map: any;
    const latitudeSetup = ref(0);
    const longitudeSetup = ref(0);
    let marker: any;

    onMounted(() => {
      if (mapContainer.value) {
        map = L.map(mapContainer.value).setView([36.12692188650319, -9.631536524590521], 4);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        map.on('click', function(ev: any){
            var latlng = map.mouseEventToLatLng(ev.originalEvent);
            latitudeSetup.value = latlng.lat;
            longitudeSetup.value = latlng.lng;

            if (latitudeSetup.value !== 0 && longitudeSetup.value !== 0) {
                if (marker) {
                    map.removeLayer(marker);
                }
                marker = L.marker([latitudeSetup.value, longitudeSetup.value]);
                map.addLayer(marker);
                marker.addTo(map).bindPopup('Selected port');
            }
        });

      }
    });

    return {
        latitudeSetup,
        longitudeSetup,
        marker,
        mapContainer
    };
  },
    data() {
        return {
            name: '',
        };
    },
    methods: {
        async submitPort() {
            try {
                const name = new PortName(this.name);
                const coordinate = new Coordinate(this.latitudeSetup, this.longitudeSetup);
                const port = new Port(UllUUID.random(), name, coordinate);
                console.log(port);
                await usePortStateManager().save(port);
                this.showNotification('Success', 'Port inserted successfully');
            } catch (error) {
                this.showNotification('Failure', error instanceof Error ? error.message : 'An error has occurred');
                console.error(error);
            }
        },
        showNotification(type: string, message: string) {
            if (type ==='Failure') {
                toast.error(message + ', port not inserted', {
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
    .row {
        overflow: hidden;
        display: flex;
        align-items: center;
        height: 100%;
    }
    .col {
        float: left;
        margin: 8%;
    }
    .map-container {
        border: white solid 5px;
        margin-right: 11%;
        height: 400px;
    }
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