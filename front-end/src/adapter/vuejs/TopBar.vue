<template>
    <header>
        <nav>
            <ul class="navigation">
                <li><router-link class="link" :to="{name: 'Home'}">Home</router-link></li>
                
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">Ports<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><router-link class="link" :to="{name: 'PortList'}">Port register</router-link></li>
                        <li><router-link class="link" :to="{name: 'PortForm'}">Insert port</router-link></li>
                    </ul>
                </li>
                
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">Accesses<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><router-link class="link" :to="{name: 'AccessList'}">Access register</router-link></li>
                        <li><router-link class="link" :to="{name: 'AccessForm'}">Insert access</router-link></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">Trucks<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><router-link class="link" :to="{name: 'TruckList'}">Truck register</router-link></li>
                        <li><router-link class="link" :to="{name: 'TruckForm'}">Insert truck</router-link></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">Berths<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><router-link class="link" :to="{name: 'BerthList'}">Berth register</router-link></li>
                        <li><router-link class="link" :to="{name: 'BerthForm'}">Insert berth</router-link></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">Vessels<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><router-link class="link" :to="{name: 'VesselList'}">Vessel register</router-link></li>
                        <li><router-link class="link" :to="{name: 'VesselForm'}">Insert vessel</router-link></li>
                    </ul>
                </li>
                
                
                
                
            </ul>
        </nav>
    </header>
</template>

<script lang="ts">
    export default {
    }
</script>

<style scoped>
header {
    background-color: rgba(0, 0, 0, 0.8);
    z-index: 99;
    width: 100%;
    transition: 0.5s ease all;
    color: #fff;
}

header nav {
    transition: 0.5s ease all;
    width: 90%;
    margin: 0 5%;
}

@media (min-width: 1140px) {
    header nav {
        max-width: 1140px;
    }
}

header ul,
header .link {
    font-weight: 500;
    color: #fff;
    list-style: none;
    text-decoration: none;
}

header li {
    text-transform: uppercase;
    padding: 16px;
}

header .link {
    font-size: 13px;
    transition: 0.5s ease all;
    padding-bottom: 4px;
    border-bottom: 1px solid transparent;
}

header .link:hover {
    color: #00afea;
    border-color: #00afea;
}

header .branding {
    display: flex;
    align-items: center;
}

header .branding img {
    width: 50px;
    transition: 0.5s ease all;
}

header .navigation {
    display: flex;
    margin-left: auto;
}

.dropdown-menu {
    background-color: rgba(0, 0, 0, 0.9);
    border: none;
    box-shadow: none;
    transition: 0.5s ease all;

}
.dropdown-toggle {
    text-decoration: none;
    font-size: 14px;
}

</style>