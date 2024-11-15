<script setup>
import MenuItem from "./MenuItem.vue";
const props = defineProps({
  menus: { type: Array, required: true }, // 'Type' 대신 'type'으로 수정
});
</script>

<template>
  <ul class="navbar-nav">
    <MenuItem
      v-for="menu in menus"
      :key="menu.name"
      :menu="menu"
      :class="{ active: $route.path === menu.path }"
      class="custom"
    />
  </ul>
</template>

<style scoped>
.custom {
  padding-right: 20px;
  font-weight: 600;
}
</style>
