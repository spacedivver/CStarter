<script setup>
const props = defineProps({ id: String });

// const avatar = `/api/member/${props.id}/avatar`;
</script>

<template>
  <li class="nav-item">
    <router-link class="nav-link" to="/mypage">
      <img src="@/assets/images/profile.jpg" class="avatar avatar-sm" />
      {{ id }}
    </router-link>
  </li>
</template>
<style></style>
