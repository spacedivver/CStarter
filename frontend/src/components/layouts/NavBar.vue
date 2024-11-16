<script setup>
import { reactive, computed } from "vue";
import MenuGroup from "./menu/MenuGroup.vue";
import AccountMenuGroup from "./menu/AccountMenuGroup.vue";
import config from "@/config";
import { useRoute } from "vue-router";

let state = reactive({ isNavShow: false });

let navClass = computed(() =>
  state.isNavShow ? "collapse navbar-collapse show" : "collapse navbar-collapse"
);

const toggleNavShow = () => (state.isNavShow = !state.isNavShow);
const route = useRoute();
</script>

<template>
  <nav class="navbar navbar-expand-sm">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">
        <img
          src="@/assets/images/CStarter_logo.png"
          alt="CStarter Logo"
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

.nav-link.active {
  background-color: #007bff !important; /* 파란색 배경 */
  color: white !important; /* 텍스트 색상 */
  border-radius: 50px !important; /* 모서리를 둥글게 설정 */
}
</style>
