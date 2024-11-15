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
  <div class="container-xxl">
    <div class="content my-3 ">
      <slot></slot>
    </div>
    <Footer />
  </div>
</template>
