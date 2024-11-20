<template>
  <LetterHeader />
  <div class="container mt-5 mb-5 p-4 text-center">
    <h3 class="mb-4">환경설정</h3>

    <!-- 완료 화면 -->
    <div v-if="completed" class="loading-screen">
      <img src="@/assets/images/interview/complete.png" alt="완료" style="width: 150px;" class="loading-icon mb-3" /> 
      <h3>질문지 생성 완료!</h3>
      <p>지금부터 면접을 시작할게요.</p>
    </div>

    <!-- 로딩 화면 -->
    <div v-if="loading" class="loading-screen">
      <img src="@/assets/images/interview/loading.gif" alt="로딩 중..." class="loading-icon" /> 
      <h3>질문지 생성 중...</h3>
      <p>목을 가다듬고, 긴장을 풀어볼까요?</p>
    </div>

    <!-- 질문 설정 UI -->
    <div v-else>
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
        <button class="btn btn-primary" style="width: 150px;" @click="submitQuestions">시작하기</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import LetterHeader from '@/components/letter/LetterHeader.vue';
import { useRouter } from 'vue-router';
import { ref, computed } from 'vue';
import { useCoverLetterStore } from '@/stores/coverLetterStore';
import { useQuestionStore } from '@/stores/questionStore'; 
import { useSpeechStore } from '@/stores/speechStore'; // 음성 스토어 가져오기
import axios from 'axios';

const router = useRouter();
const coverLetterStore = useCoverLetterStore();
const questionStore = useQuestionStore();
const speechStore = useSpeechStore(); // 음성 스토어 인스턴스 생성

const clno = computed(() => coverLetterStore.clno);
const companyName = computed(() => coverLetterStore.companyName);
const job = computed(() => coverLetterStore.job);

const isMicOn = ref(false);
const isCameraOn = ref(false);
const loading = ref(false);
const completed = ref(false);
const questionCount = ref(1);

// 마이크 토글
const toggleMic = () => {
  isMicOn.value = !isMicOn.value;
};

// 카메라 토글
const toggleCamera = () => {
  isCameraOn.value = !isCameraOn.value;
};

const submitQuestions = async () => {
  const data = {
    clno: clno.value,
    companyName: companyName.value,
    job: job.value,
    questionCount: questionCount.value,
    mno: 1
  };

  try {
    loading.value = true;
    completed.value = false;

    const response = await axios.post('http://localhost:8080/api/interview/cover-letter/question', data);
    questionStore.setQuestions(response.data);

    // 첫 번째 질문에 대한 TTS 요청 보내기
    sendFirstQuestionToTTS(response.data);

    completed.value = true;

    setTimeout(() => {
      router.push('/Interview'); // Interview 페이지로 이동
    }, 1000);
  } catch (error) {
    console.error('질문을 제출하는 데 실패했습니다:', error);
  } finally {
    loading.value = false;
  }
};

const sendFirstQuestionToTTS = async (questions) => {
  if (questions.length > 0) {
    const firstQuestion = questions[0];
    const data = {
      clno: firstQuestion.clno,
      number: firstQuestion.number,
      questionType: 0,
      rno: firstQuestion.rno
    };
    
    try {
      await axios.post('http://localhost:8080/api/interview/cover-letter/question/tts', data);
      speechStore.speak(firstQuestion.question); // 음성을 재생합니다.
      console.log('첫 번째 질문 TTS 요청 성공');
    } catch (error) {
      console.error('첫 번째 질문 TTS 요청 실패:', error);
    }
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

/* 로딩 화면 스타일 */
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
