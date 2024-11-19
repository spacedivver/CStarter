<template>
  <LetterHeader />
  <div class="container mt-5 mb-5 p-4 text-center"> <!-- container에 text-center 추가 -->
    <h3 class="mb-4">환경설정</h3>

    <!-- 마이크 설정 (토글 버튼) -->
    <div class="row mb-3 justify-content-center">
      <div class="col-md-6">
        <button class="btn" :class="isMicOn ? 'btn-success' : 'btn-secondary'" @click="toggleMic">
          <i class="bi bi-mic-fill me-2"></i> 
          {{ isMicOn ? '마이크가 켜졌습니다' : '마이크가 꺼졌습니다' }}
        </button>
      </div>
    </div>

    <!-- 카메라 설정 (토글 버튼) -->
    <div class="row mb-3 justify-content-center">
      <div class="col-md-6">
        <button class="btn" :class="isCameraOn ? 'btn-danger' : 'btn-secondary'" @click="toggleCamera">
          <i class="bi bi-camera-video-fill me-2"></i> 
          {{ isCameraOn ? '카메라가 켜졌습니다' : '카메라가 꺼졌습니다' }}
        </button>
      </div>
    </div>

    <!-- 질문 개수 선택 -->
    <div class="row mb-3 mt-5">
      <div class="col-md-12 mx-auto">
        <h5 for="questionCount" class="form-label mb-3">질문 개수를 선택하세요</h5>
        <el-select id="questionCount" class="mx-auto" v-model="questionCount" style="width: 380px;">
          <el-option v-for="i in 20" :key="i" :value="i">{{ i }} 개</el-option>
        </el-select>
      </div>
    </div>

    <!-- '다음 단계' 버튼 추가 -->
    <div class="mt-4">
      <router-link to="/Interview">
        <button class="btn btn-primary" style="width: 150px;" @click="submitQuestions">시작하기</button>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import LetterHeader from '@/components/letter/LetterHeader.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref,computed } from 'vue';
import { useCoverLetterStore } from '@/stores/coverLetterStore';
import { useQuestionStore } from '@/stores/questionStore'; 
import axios from 'axios';

const router = useRouter(); // router 가져오기
const route = useRoute();

// Pinia 스토어 사용
const coverLetterStore = useCoverLetterStore();
const questionStore = useQuestionStore();

// computed 속성으로 스토어의 상태 가져오기
const clno = computed(() => coverLetterStore.clno);
const companyName = computed(() => coverLetterStore.companyName);
const job = computed(() => coverLetterStore.job);

console.log(clno, companyName, job);
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


// POST 요청 함수
const submitQuestions = async () => {
  const data = {
    clno: clno.value,
    companyName: companyName.value,
    job: job.value,
    questionCount: questionCount.value,
    mno: 1
  };

  try {
    const response = await axios.post('http://localhost:8080/api/interview/cover-letter/question', data);
    console.log(data);
    // 응답 데이터를 새로운 질문 스토어에 저장
    questionStore.setQuestions(response.data); // 질문 리스트 저장

    // 다음 페이지로 이동 (예: Interview 페이지)
    router.push('/Interview');
  } catch (error) {
    console.error('질문을 제출하는 데 실패했습니다:', error);
  }
};

</script>

<style scoped>


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
</style>
