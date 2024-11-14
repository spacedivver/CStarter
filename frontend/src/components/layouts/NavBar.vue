<script setup>
import { reactive, computed } from "vue";
import MenuGroup from "./menu/MenuGroup.vue";
import AccountMenuGroup from "./menu/AccountMenuGroup.vue";
import config from "@/config";

let state = reactive({ isNavShow: false });

let navClass = computed(() =>
  state.isNavShow ? "collapse navbar-collapse show" : "collapse navbar-collapse"
);

const toggleNavShow = () => (state.isNavShow = !state.isNavShow);
</script>

<template>
  <nav class="navbar navbar-expand-sm shadow-sm">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">
        <img
          src="@/assets/images/CStarter_logo.png"
          alt="KB Finance Logo"
          style="height: 48px"
        />
      </router-link>
      <div class="custom-gap"></div>
      <div class="custom-gap"></div>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#collapsibleNavbar"
        @click="toggleNavShow"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div :class="navClass" id="collapsibleNavbar">
        <MenuGroup :menus="config.menus" />

        <AccountMenuGroup />
      </div>
    </div>
  </nav>
</template>
<style>
.custom-gap {
  width: 20px;
}
</style>
