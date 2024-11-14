<script setup>
import MenuItem from "./MenuItem.vue";
const props = defineProps({
  menus: { Type: Array, required: true },
});
</script>

<template>
  <ul class="navbar-nav">
    <MenuItem v-for="menu in menus" :menu="menu" class="custom"/>
  </ul>
</template>

<style>
.custom {
  padding-right: 20px;
  font-weight: 600;
}</style>
