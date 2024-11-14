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
      <div v-if="currentQuestionIndex < questions.length">
        <div class="ai-response mb-3">
          <div class="d-flex">
            <div class="ai-icon">
              <img src="@/assets/images/aiicon.png" alt="" style="width: 50px; height: 50px;" class="me-3"></img>
            </div>
            <div class="ml-3">
              <div class="question-index mb-1">질문 {{ currentQuestionIndex + 1 }}</div>
              <div class="question-text">{{ questions[currentQuestionIndex] }}</div>
            </div>
          </div>
        </div>

          <!-- 사용자 답변 -->
            <div class="answer-section mt-2">
              <div class="d-flex align-items-center mb-2" v-if="sttTexts.length || isRecording"> <!-- 답변이 있거나 음성 인식 중일 때 보이도록 설정 -->
                <img src="@/assets/images/usericon.png" alt="" style="width: 50px; height: 50px;" class="me-3"></img>
                <div class="user-answer">내 답변</div>
              </div>
              <div v-if="isRecording" class="answer-box"> <!-- 음성 인식 중일 때만 박스를 보여줌 -->
                <div class="stt-text bubble mt-2">
                  <img src="@/assets/images/microphone.png" alt="마이크" style="width: 20px; height: 20px;" class="me-2"> 
                  답변 중...
                </div>
              </div>
              <div v-if="sttTexts.length" class="answer-box"> <!-- 이전 답변이 있을 때만 박스를 보여줌 -->
                <div v-for="(text, idx) in sttTexts" :key="idx" class="stt-text bubble mt-2">{{ text }}</div>
              </div>
            </div>



        <!-- 마이크 및 음성 인식 -->
        <div class="d-flex justify-content-center mt-3">
          <button class="btn btn-primary" @click="startRecording" v-if="!isRecording && sttTexts.length === 0">답변하기</button>
          <button class="btn btn-secondary ml-3" @click="listenToAnswer" v-if="sttTexts.length > 0">내 답변 듣기</button>
          <button class="btn btn-warning ml-3" @click="resetAnswer" v-if="sttTexts.length > 0">다시 답변하기</button>
          <button class="btn btn-danger ml-3" @click="stopRecording" v-if="isRecording">중지하기</button>
        </div>
      </div>

      <!-- 다음 질문 버튼 -->
      <div v-if="sttTexts.length && currentQuestionIndex < questions.length" class="d-flex justify-content-center mt-4">
        <button class="btn btn-success" @click="nextQuestion">다음 질문</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';

const questions = ref([
  "MVC 패턴에 대해 설명해주세요.",
  "Vue.js의 장점은 무엇인가요?",
  "JavaScript에서 클로저란 무엇인가요?"
]); // 여러 질문 배열

// STT 텍스트 저장
const sttTexts = ref([]);

// 진행 시간
const time = ref(0);
let timerInterval = null;

// 음성 인식 상태
const isRecording = ref(false);
const currentQuestionIndex = ref(0); // 현재 질문 인덱스

// 음성 인식 객체
let recognition = null;

onMounted(() => {
  startTimer();

  // 음성 인식 객체 초기화
  if ('SpeechRecognition' in window || 'webkitSpeechRecognition' in window) {
    recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
    recognition.continuous = true;
    recognition.lang = 'ko-KR';
    recognition.interimResults = true;

    recognition.onstart = () => {
      isRecording.value = true;
      nextTick(() => {
        // DOM 업데이트가 완료된 후 호출
      });
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
    // 이전 답변 유지
  } else {
    // 모든 질문이 끝난 경우
    alert("모든 질문이 완료되었습니다.");
  }
};

// 다시 답변하기
const resetAnswer = () => {
  // 이전 답변 유지
  if (!isRecording.value) { // 음성 인식이 진행 중이지 않을 때만 시작
    startRecording(); // 새로운 답변 녹음 시작
  }
};
</script>

<style scoped>
/* 사용자 답변 박스 스타일 */
.answer-box {
  background-color: #E9F0FF; /* 회색 배경 */
  border-radius: 10px; /* 라운드 처리 */
  padding: 10px; /* 패딩 추가 */
  margin-top: 10px; /* 마진 추가 */
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
</style>
