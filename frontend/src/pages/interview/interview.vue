<template>
  <div class="container mt-5">
    <!-- 진행 시간 -->
    <div class="row justify-content-center">
      <div class="col-6 d-flex justify-content-center align-items-center">
        <div class="circle timer">{{ time }}초</div>
      </div>
    </div>

    <!-- 질문과 답변 -->
    <div class="question-section mt-4">
        <!-- AI의 질문 -->
      <div class="ai-response mb-3">
        <div class="d-flex">
          <!-- 아이콘 부분 (왼쪽) -->
          <div class="ai-icon">
            <span>🤖</span>
          </div>
          
          <!-- 질문 텍스트 부분 (오른쪽) -->
          <div class="ml-3">
            <div class="question-index">[질문 1]</div>
            <div class="question-text">{{ question }}</div>
          </div>
        </div>
      </div>

      <!-- 사용자 답변 및 STT 텍스트 -->
      <div v-if="sttTexts.length" class="answer-section mt-2">
        <div class="d-flex align-items-center mb-2">
          <div class="user-icon">👤</div>
          <div class="user-answer">[내 답변]</div>
        </div>
        <div v-for="(text, idx) in sttTexts" :key="idx" class="stt-text bubble mt-2">"{{ text }}"</div>
      </div>

      <!-- 음성 인식 마이크 표시 및 답변 듣기 -->
      <div class="d-flex justify-content-center mt-3">
        <button class="btn btn-primary" @click="startRecording" v-if="!isRecording && sttTexts.length === 0">답변하기</button>
        <button class="btn btn-secondary ml-3" @click="listenToAnswer" v-if="sttTexts.length > 0">내 답변 듣기</button>
      </div>

      <!-- 다시 답변하기 -->
      <div class="d-flex justify-content-center mt-3" v-if="sttTexts.length > 0">
        <button class="btn btn-warning d-flex align-items-center" @click="startRecording">
          <i class="fas fa-microphone mr-2"></i> 다시 답변하기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

// 질문
const question = "MVC 패턴에 대해 설명해주세요.";

// STT 텍스트 저장
const sttTexts = ref([]);

// 진행 시간
const time = ref(0);
let timerInterval = null;

// 음성 인식 상태
const isRecording = ref(false);

// 음성 인식 객체
let recognition = null;

onMounted(() => {
  // 타이머 시작
  startTimer();

  // 음성 인식 초기화
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
      let interimTranscript = '';
      for (let i = event.resultIndex; i < event.results.length; i++) {
        if (event.results[i].isFinal) {
          sttTexts.value.push(event.results[i][0].transcript);
        } else {
          interimTranscript += event.results[i][0].transcript;
        }
      }
    };
  } else {
    console.log("음성 인식이 지원되지 않습니다.");
  }
});

// 타이머 시작 함수
const startTimer = () => {
  time.value = 0;
  if (timerInterval) {
    clearInterval(timerInterval);
  }
  timerInterval = setInterval(() => {
    time.value += 1;
  }, 1000);
};

// 음성 인식 시작
const startRecording = () => {
  if (recognition) {
    sttTexts.value = []; // STT 텍스트 초기화
    recognition.start();
  }
};

// 내 답변 듣기
const listenToAnswer = () => {
  const msg = new SpeechSynthesisUtterance(sttTexts.value.join(' '));
  window.speechSynthesis.speak(msg);
};

</script>

<style scoped>
/* 타이머와 기타 UI 스타일 */
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

.ai-response {
  font-size: 1.5rem;
  font-weight: bold;
}

.ai-icon {
  font-size: 1.5rem;
  margin-right: 10px;
}

.question-index {
  font-size: 1.5rem;
}

.question-text {
  font-size: 1.5rem;
}

.user-icon {
  font-size: 2rem;
  margin-right: 10px;
}

.user-answer {
  font-size: 1.5rem;
}

.stt-text {
  font-size: 1.2rem;
  margin-top: 10px;
}

.bubble {
  background-color: #e7f3ff;
  border-radius: 10px;
  padding: 10px;
  margin-top: 5px;
  width: fit-content;
}

button {
  font-size: 1rem;
  width: 150px;
  margin-top: 15px;
}

button .fas {
  font-size: 1.2rem;
  margin-right: 10px;
}
</style>
