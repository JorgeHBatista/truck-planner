<template>
    <div class="parent">
		<div class="child">
        <v-card
      class="mx-auto"
      max-width="344"
    >
      <v-img
        src="../../../public/port.png"
        height="200px"
        cover
      ></v-img>
  
      <v-card-title>
        Ports
      </v-card-title>
  
      <v-card-subtitle>
        +50 ports all around the globe
      </v-card-subtitle>
  
      <v-card-actions class="action">
        <router-link :to="{name: 'PortList'}">
            <v-btn
            color="orange-lighten-2"
            variant="text"
            >
            Explore
            </v-btn>
        </router-link>
      </v-card-actions>
        </v-card>
        <v-card
      class="mx-auto"
      max-width="344"
    >
      <v-img
        src="../../../public/access.jpg"
        height="250px"
        cover
      ></v-img>
  
      <v-card-title>
        Accesses
      </v-card-title>
  
      <v-card-subtitle>
        Trucks accessing to any port
      </v-card-subtitle>
  
      <v-card-actions class="action">
        <router-link :to="{name: 'AccessList'}">
        <v-btn
          color="orange-lighten-2"
          variant="text"
        >
          Explore
        </v-btn>
      </router-link>
      </v-card-actions>

        </v-card>
        <v-card
        class="mx-auto"
        max-width="344"
        >
        <v-img
            src="../../../public/truck.png"
            height="200px"
            cover
        ></v-img>
    
        <v-card-title>
            Trucks
        </v-card-title>
    
        <v-card-subtitle>
            +500 trucks registered
        </v-card-subtitle>
    
        <v-card-actions class="action">
            <router-link :to="{name: 'TruckList'}">
                <v-btn
                color="orange-lighten-2"
                variant="text"
                >
                Explore
                </v-btn>
            </router-link>
        </v-card-actions>
    
        </v-card>
        <v-card
        class="mx-auto"
        max-width="344"
        >
        <v-img
            src="../../../public/berth.jpg"
            height="250px"
            cover
        ></v-img>
    
        <v-card-title>
            Berths
        </v-card-title>
    
        <v-card-subtitle>
            Vessels accessing to any port
        </v-card-subtitle>
    
        <v-card-actions class="action">
          <router-link :to="{name: 'BerthList'}">
            <v-btn
            color="orange-lighten-2"
            variant="text"
            >
            Explore
            </v-btn>
          </router-link>
        </v-card-actions>
        </v-card>
        <v-card
        class="mx-auto"
        max-width="344"
        >
        <v-img
            src="../../../public/vessel.png"
            height="200px"
            cover
        ></v-img>
    
        <v-card-title>
            Vessels
        </v-card-title>
    
        <v-card-subtitle>
            +400 vessels using our system
        </v-card-subtitle>
    
        <v-card-actions class="action">
            <router-link :to="{name: 'VesselList'}">
                <v-btn
                color="orange-lighten-2"
                variant="text"
                >
                Explore
                </v-btn>
            </router-link>
        </v-card-actions>
        </v-card>
		</div>
    </div>
    
</template>

<script lang="ts">
    export default {
    }
</script>

<style scoped>
    .parent {
		width: 100%;
		height: 100%;
    }
	.child {
		margin-top: 12.5%;
		height: 100%;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
		display: flex;
        justify-content: center;
        align-items: center; 
	}
    .action {
        display: block;
        margin-left: auto;
        margin-right: auto;
    }

</style>