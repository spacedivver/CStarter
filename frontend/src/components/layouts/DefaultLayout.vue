<script setup>
import Header from "./Header.vue";
import NavBar from "./NavBar.vue";
import Footer from "./Footer.vue";
</script>

<template>
  <div class="container-lg">
    <!-- <Header /> -->
    <NavBar />
  </div>
  <div class="">
    <div class="content">
      <slot></slot>
    </div>
  </div>
  <div class="gap"></div>
  <div class="">
    <Footer />
  </div>
</template>

<style>
.gap {
  height: 50px;
}
</style>
