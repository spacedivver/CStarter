<template>
  <CStestHeader />
  <div class="container mt-5 mb-5">
    <h3 class="text-center mb-4">환경설정</h3>

    <!-- 완료 화면 -->
    <div v-if="completed" class="loading-screen">
      <img src="@/assets/images/interview/complete.png" alt="완료" style="width: 150px;" class="loading-icon mb-3" /> 
      <h3>설정 완료!</h3>
      <p>지금부터 면접을 시작할게요.</p>
    </div>

    <!-- 로딩 화면이 아닐 때만 보여줍니다 -->
    <div v-else>
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
        <button class="btn btn-primary" style="width: 150px;" @click="startInterview">시작하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import CStestHeader from '@/components/cstest/CStestHeader.vue';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute(); // 현재 라우트를 가져옵니다.
const router = useRouter(); // 라우터를 가져옵니다.

// 마이크 및 카메라 상태
const isMicOn = ref(false);
const isCameraOn = ref(false);
const completed = ref(false); // 완료 상태 추가

// 마이크 토글
const toggleMic = () => {
  isMicOn.value = !isMicOn.value;
};

// 카메라 토글
const toggleCamera = () => {
  isCameraOn.value = !isCameraOn.value;
};

// 시작하기 버튼 클릭 시 호출되는 함수
const startInterview = () => {
  completed.value = true; // 완료 상태 활성화

  // 2초 후에 CStestInterview 페이지로 이동
  setTimeout(() => {
    const bno = route.query.bno; // 쿼리에서 bno 가져오기
    if (bno) {
      // CStestInterview로 이동할 때 bno를 쿼리로 전달
      router.push({ name: 'CStestInterview', query: { bno } });
    } else {
      console.error('bno is not defined');
    }
  }, 2000); // 2초 대기
};

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

/* 완료 화면 스타일 */
.loading-screen {
  position: fixed; /* 화면 중앙에 고정 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.9); /* 배경색 */
  z-index: 1000; /* 다른 요소 위에 표시 */
}
</style>
