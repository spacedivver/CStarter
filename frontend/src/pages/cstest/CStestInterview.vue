<template>
  <CStestHeader />
  <div class="container mt-5 mb-5">
    <!-- 진행 시간 -->
    <div class="row justify-content-center">
      <div class="col-6 d-flex justify-content-center align-items-center">
        <div class="circle timer">{{ time }}초</div>
      </div>
    </div>

    <!-- 질문과 답변 -->
    <div class="question-section mt-4">
      <div v-if="currentQuestionIndex < questions.length">
        <div class="ai-response mb-3">
          <div class="d-flex">
            <div class="ai-icon">
              <img src="@/assets/images/aiicon.png" alt="" style="width: 50px; height: 50px;" class="me-3"></img>
            </div>
            <div class="ml-3">
              <div class="question-index mb-1">질문</div>
              <div class="question-text">{{ questionTitle }}</div> <!-- 제목 표시 -->
            </div>
          </div>
        </div>

        <!-- 사용자 답변 -->
        <div class="answer-section mt-2">
          <div class="d-flex align-items-center mb-2" v-if="sttTexts.length || isRecording">
            <img src="@/assets/images/usericon.png" alt="" style="width: 50px; height: 50px;" class="me-3"></img>
            <div class="user-answer">내 답변</div>
          </div>
          <div v-if="isRecording" class="mb-2">
            <div class="stt-text bubble">
              <img src="@/assets/images/microphone.png" alt="마이크" style="width: 35px; height: 35px;" class="ms-2 me-3"> 
              답변 중 ...
            </div>
          </div>
          <div v-if="sttTexts.length" class="answer-box">
            <div v-for="(text, idx) in sttTexts" :key="idx" class="stt-text bubble m-2">{{ text }}</div>
          </div>
        </div>

        <div class="d-flex justify-content-between mt-1">
          <div class="mx-auto mt-2">
            <button class="btn btn-primary" @click="startRecording" v-if="!isRecording && sttTexts.length === 0">답변하기</button>
            <button class="btn btn-danger ml-3" @click="stopRecording" v-if="isRecording">중지하기</button>
          </div>

          <div class="d-flex">
            <button class="icon-button ml-3" @click="resetAnswer" v-if="sttTexts.length > 0 && !isRecording">
              <i class="fas fa-microphone"></i> 다시 답변하기
            </button>
            <button class="icon-button ml-3" @click="listenToAnswer" v-if="sttTexts.length > 0 && !isRecording">
              <i class="fas fa-volume-up"></i> 내 답변 듣기
            </button>
          </div>
        </div>
      </div>

      <!-- 다음 질문 버튼 -->
      <div v-if="sttTexts.length && currentQuestionIndex < questions.length && !isRecording" class="d-flex justify-content-center mt-4">
        <button class="btn btn-success" @click="nextQuestion">다음 질문</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import CStestHeader from '@/components/cstest/CStestHeader.vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute(); // 현재 라우트를 가져옵니다.
const questionTitle = ref(''); // 질문 제목을 저장할 상태
const sttTexts = ref([]); // STT 텍스트 저장
const time = ref(0); // 진행 시간
let timerInterval = null; // 타이머 인터벌
const isRecording = ref(false); // 음성 인식 상태
const currentQuestionIndex = ref(0); // 현재 질문 인덱스
const questions = ref([]); // 질문 배열
let recognition = null; // 음성 인식 객체

// 질문 목록 API 호출
const loadQuestion = async (bno) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/interview/tech/question/${bno}`);
    questionTitle.value = response.data.title; // 제목을 저장합니다.
    questions.value = [response.data]; // API 응답을 배열로 저장합니다.
  } catch (error) {
    console.error("Failed to load question:", error);
  }
};

onMounted(async () => {
  const bno = route.query.bno; // 쿼리에서 bno를 가져옵니다.
  if (bno) {
    await loadQuestion(bno); // bno로 질문을 로드합니다.
  }
  startTimer(); // 타이머 시작
  initSpeechRecognition(); // 음성 인식 초기화
});

// 타이머 시작
const startTimer = () => {
  time.value = 0;
  if (timerInterval) {
    clearInterval(timerInterval);
  }
  timerInterval = setInterval(() => {
    time.value += 1;
  }, 1000);
};

// 음성 인식 초기화
const initSpeechRecognition = () => {
  if ('SpeechRecognition' in window || 'webkitSpeechRecognition' in window) {
    recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
    recognition.continuous = true;
    recognition.lang = 'ko-KR';
    recognition.interimResults = true;

    recognition.onstart = () => {
      isRecording.value = true;
    };

    recognition.onend = () => {
      isRecording.value = false;
    };

    recognition.onresult = (event) => {
      for (let i = event.resultIndex; i < event.results.length; i++) {
        if (event.results[i].isFinal) {
          sttTexts.value.push(event.results[i][0].transcript);
        }
      }
    };
  } else {
    console.log("음성 인식이 지원되지 않습니다.");
  }
};

// 음성 인식 시작
const startRecording = () => {
  if (recognition) {
    recognition.start();
  }
};

// 음성 인식 중지
const stopRecording = () => {
  if (recognition) {
    recognition.stop(); // 음성 인식 중지
  }
};

// 내 답변 듣기
const listenToAnswer = () => {
  const msg = new SpeechSynthesisUtterance(sttTexts.value.join(' '));
  window.speechSynthesis.speak(msg);
};

// 다음 질문으로 이동
const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
    sttTexts.value = []; // 다음 질문을 위해 답변 초기화
  } else {
    // 모든 질문이 끝난 경우
    window.location.href = '/report/result'; // 결과 페이지로 이동
  }
};

// 다시 답변하기
const resetAnswer = () => {
  sttTexts.value = []; // 이전 답변 초기화
  startRecording(); // 새로운 답변 녹음 시작
};
</script>

<style scoped>
/* 사용자 답변 박스 스타일 */
.answer-box {
  background-color: #E9F0FF;
  border-radius: 10px; /* 라운드 처리 */
  padding: 10px; /* 패딩 추가 */
}

.timer {
  font-size: 20px;
  font-weight: bold;
  padding: 5px;
  background-color: #28a745;
  color: white;
  border-radius: 20px;
  text-align: center;
  width: 150px;
  height: 40px;
}

.question-section {
  margin-top: 30px;
}

/* 버튼 스타일 */
.btn {
  transition: background-color 0.3s, transform 0.3s;
}

.btn:hover {
  transform: translateY(-2px);
}

.btn:active {
  transform: translateY(1px);
}

.btn-danger {
  background-color: #dc3545; /* 중지 버튼 색상 */
  color: white;
}

.btn-danger:hover {
  background-color: #c82333; /* 호버 효과 */
}

.question-index {
  color: #FF8000;
  font-weight: 700;
  font-size: 20px;
}

.question-text {
  font-size: 18px;
}

/* 다시 답변하기 버튼 회색들 */
.icon-button {
  background: none; /* 배경 없음 */
  border: none; /* 테두리 없음 */
  color: gray; /* 텍스트 색상 */
  font-size: 14px; /* 폰트 크기 */
  display: flex; /* 아이콘과 텍스트를 수평으로 배치 */
  align-items: center; /* 중앙 정렬 */
  cursor: pointer; /* 포인터 커서 */
  transition: color 0.3s; /* 색상 변화 애니메이션 */
  margin-top: 2px;
}

.icon-button:hover {
  color: #007bff; /* 호버 시 색상 변화 */
}

.icon-button i {
  margin-right: 5px; /* 아이콘과 텍스트 간격 */
}

</style>
