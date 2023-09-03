import { createRouter, createWebHistory } from 'vue-router';
import TruckForm from '../adapter/vuejs/truck/TruckForm.vue';
import TruckTable from '../adapter/vuejs/truck/TruckTable.vue';
import IndividualTruckTable from '../adapter/vuejs/truck/IndividualTruckTable.vue';
import PortTable from '../adapter/vuejs/port/PortTable.vue';
import PortForm from '../adapter/vuejs/port/PortForm.vue';
import IndividualPortTable from '../adapter/vuejs/port/IndividualPortTable.vue';
import VesselTable from '../adapter/vuejs/vessel/VesselTable.vue';
import IndividualVesselTable from '../adapter/vuejs/vessel/IndividualVesselTable.vue';
import VesselForm from '../adapter/vuejs/vessel/VesselForm.vue';
import AccessTable from '../adapter/vuejs/access/AccessTable.vue';
import AccessForm from '../adapter/vuejs/access/AccessForm.vue';
import BerthTable from '../adapter/vuejs/berth/BerthTable.vue';
import BerthForm from '../adapter/vuejs/berth/BerthForm.vue';
import InidividualScaleTable from '../adapter/vuejs/scale/IndividualScaleTable.vue';
import Home from '../adapter/vuejs/Home.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/trucks',
        name: 'TruckList',
        component: TruckTable
    },
    {
        path: '/trucks/new',
        name: 'TruckForm',
        component: TruckForm
    },
    {
        path: '/trucks/:id',
        name: 'OneTruck',
        component: IndividualTruckTable
    },
    {
        path: '/ports',
        name: 'PortList',
        component: PortTable
    },
    {
        path: '/ports/new',
        name: 'PortForm',
        component: PortForm
    },
    {
        path: '/ports/:id',
        name: 'OnePort',
        component: IndividualPortTable
    },
    {
        path: '/vessels',
        name: 'VesselList',
        component: VesselTable
    },
    {
        path: `/vessels/:id`,
        name: 'OneVessel',
        component: IndividualVesselTable
    },
    {
        path: '/vessels/new',
        name: 'VesselForm',
        component: VesselForm
    },
    {
        path: '/accesses',
        name: 'AccessList',
        component: AccessTable
    },
    {
        path: '/accesses/new',
        name: 'AccessForm',
        component: AccessForm
    },
    {
        path: '/berths',
        name: 'BerthList',
        component: BerthTable
    },
    {
        path: '/berths/new',
        name: 'BerthForm',
        component: BerthForm
    },
    {
        path: `/scales/:id`,
        name: 'OneScale',
        component: InidividualScaleTable
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;