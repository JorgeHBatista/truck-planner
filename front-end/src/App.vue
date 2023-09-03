<script setup lang="ts">
	import TopBar from './adapter/vuejs/TopBar.vue'
</script>

<template>
	<div id="app">
    <div class="TopBar">
      <TopBar />
    </div>
    <router-view></router-view>
  </div>
</template>

<style>
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}
  	body {
  	  	margin: 0;
  	  	background: #eee !important;
  	}
</style>
