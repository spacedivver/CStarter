<!-- 모의면접 세팅 -->
<template>
  <CStestHeader />
  <div class="container mt-5 mb-5p">
    <h3 class="text-center mb-4">환경설정</h3>

    <!-- 마이크 설정 (토글 버튼) -->
    <div class="row mb-3 justify-content-center">
      <div class="col-md-6 text-center">
        <button class="btn" :class="isMicOn ? 'btn-success' : 'btn-secondary'" @click="toggleMic">
          <i class="bi bi-mic-fill me-2"></i> 
          {{ isMicOn ? '마이크가 켜졌습니다' : '마이크가 꺼졌습니다' }}
        </button>
      </div>
    </div>

    <!-- 카메라 설정 (토글 버튼) -->
    <div class="row mb-3 justify-content-center">
      <div class="col-md-6 text-center">
        <button class="btn" :class="isCameraOn ? 'btn-danger' : 'btn-secondary'" @click="toggleCamera">
          <i class="bi bi-camera-video-fill me-2"></i> 
          {{ isCameraOn ? '카메라가 켜졌습니다' : '카메라가 꺼졌습니다' }}
        </button>
      </div>
    </div>

    <!-- '다음 단계' 버튼 추가 -->
    <div class="text-center mt-4">
      <router-link to="/CSTest/CStestInterview">
        <button class="btn btn-primary" style="width: 150px;">다음 단계</button>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import CStestHeader from '@/components/cstest/CStestHeader.vue';
import { ref } from 'vue';

// 마이크 및 카메라 상태
const isMicOn = ref(false);
const isCameraOn = ref(false);

// 마이크 토글
const toggleMic = () => {
  isMicOn.value = !isMicOn.value;
};

// 카메라 토글
const toggleCamera = () => {
  isCameraOn.value = !isCameraOn.value;
};

// 질문 개수 모델
const questionCount = ref(1);
</script>

<style scoped>
.container {
  max-width: 800px;
}

.btn {
  width: 100%;
  font-size: 1.2rem;
}

h3 {
  color: #4e73df;
}

.form-label {
  font-weight: 600;
}

select.form-select {
  font-size: 1.2rem;
  padding: 0.75rem 1.25rem;
}

/* 버튼 중앙 정렬을 위한 스타일 */
.row.justify-content-center {
  display: flex;
  justify-content: center;
}

.col-md-6 {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
