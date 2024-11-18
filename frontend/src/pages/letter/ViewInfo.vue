<template>
    <div>
      <img :src="noticeUrl" alt="공고 이미지" />
    </div>
  </template>
  
  <script setup>
  import { useRoute } from 'vue-router';
  
  const route = useRoute();
  const noticeUrl = route.query.notice; // notice URL을 query로 받음
  </script>
  
  <style scoped>
  /* 필요한 스타일 추가 */
  </style>
  