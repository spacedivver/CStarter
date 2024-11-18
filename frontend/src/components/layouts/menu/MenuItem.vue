<script setup>
const props = defineProps({
  menu: { type: Object, required: true }, // 'Type'을 'type'으로 수정
});
</script>

<template>
  <li class="nav-item">
    <!-- 현재 경로와 menu.url이 일치하면 active 클래스 추가 -->
    <router-link
      class="nav-link"
      :to="menu.url"
      :class="{ active: $route.path === menu.url }"
    >
      {{ menu.title }}
    </router-link>
  </li>
</template>

<style scoped>
.nav-link {
  color: black;
  text-decoration: none;
  transition: color 0.3s;
}

/* 활성화된 링크 스타일 */
.nav-link.active {
  color: #007bff;
  font-weight: bold;
  padding: 0.5rem !important;
}
</style>
