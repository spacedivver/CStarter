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
              <span>🤖</span>
            </div>
            <div class="ml-3">
              <div class="question-index">[질문 {{ currentQuestionIndex + 1 }}]</div>
              <div class="question-text">{{ questions[currentQuestionIndex] }}</div>
            </div>
          </div>
        </div>

        <!-- 사용자 답변 -->
        <div v-if="sttTexts.length" class="answer-section mt-2">
          <div class="d-flex align-items-center mb-2">
            <div class="user-icon">👤</div>
            <div class="user-answer">[내 답변]</div>
          </div>
          <div v-for="(text, idx) in sttTexts" :key="idx" class="stt-text bubble mt-2">"{{ text }}"</div>
        </div>

        <!-- 마이크 및 음성 인식 -->
        <div class="d-flex justify-content-center mt-3">
          <button class="btn btn-primary" @click="startRecording" v-if="!isRecording && sttTexts.length === 0">답변하기</button>
          <button class="btn btn-secondary ml-3" @click="listenToAnswer" v-if="sttTexts.length > 0">내 답변 듣기</button>
          <button class="btn btn-warning ml-3" @click="resetAnswer" v-if="sttTexts.length > 0">다시 답변하기</button>
        </div>
      </div>

      <!-- 다음 질문 버튼 -->
      <div v-if="sttTexts.length && currentQuestionIndex < questions.length" class="d-flex justify-content-center mt-4">
        <button class="btn btn-success" @click="nextQuestion">다음 질문</button>
      </div>
    </div>

    <!-- 음성 인식 볼륨 시각화 -->
    <div v-if="isRecording" class="volume-visual mt-4">
      <div class="volume-bar" :style="{ height: volumeHeight + 'px' }"></div>
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
const volumeHeight = ref(0); // 볼륨 높이
const currentQuestionIndex = ref(0); // 현재 질문 인덱스

// 음성 인식 객체
let recognition = null;

// Web Audio API 관련 변수
let audioContext = null;
let analyser = null;
let mediaStreamSource = null;
let analyserDataArray = null;

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
      setupAudioContext();
      nextTick(() => {
        startVolumeVisualization(); // DOM 업데이트가 완료된 후 호출
      });
    };

    recognition.onend = () => {
      isRecording.value = false;
      stopVolumeVisualization();
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
    sttTexts.value = [];  // STT 텍스트 초기화
    recognition.start();
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
    alert("모든 질문이 완료되었습니다.");
  }
};

// 다시 답변하기
const resetAnswer = () => {
  sttTexts.value = []; // 이전 답변 초기화
  if (!isRecording.value) { // 음성 인식이 진행 중이지 않을 때만 시작
    startRecording(); // 새로운 답변 녹음 시작
  }
};

// Web Audio API 초기화
const setupAudioContext = () => {
  audioContext = new (window.AudioContext || window.webkitAudioContext)();
  analyser = audioContext.createAnalyser();
  analyser.fftSize = 256;  // 주파수 분석의 크기
  analyserDataArray = new Uint8Array(analyser.frequencyBinCount);
  
  // 마이크 스트리밍을 받기 위한 setup
  navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
    mediaStreamSource = audioContext.createMediaStreamSource(stream);
    mediaStreamSource.connect(analyser);
  }).catch((err) => {
    console.log("마이크 권한 오류:", err);
  });
};

// 실시간 볼륨 시각화
const startVolumeVisualization = () => {
  const updateVolume = () => {
    analyser.getByteFrequencyData(analyserDataArray);  // 현재의 볼륨 데이터를 가져옵니다.
    const average = analyserDataArray.reduce((a, b) => a + b, 0) / analyserDataArray.length;
    volumeHeight.value = average;  // 평균 볼륨 값을 높이로 설정

    if (isRecording.value) {
      requestAnimationFrame(updateVolume);  // 계속해서 업데이트
    }
  };

  updateVolume();
};

// 볼륨 시각화 종료
const stopVolumeVisualization = () => {
  volumeHeight.value = 0; // 볼륨 높이 초기화
};
</script>

<style scoped>
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

.volume-visual {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  height: 100px; /* 높이 조정 */
  margin-top: 20px;
}

.volume-bar {
  width: 10px; /* 바의 너비 */
  background-color: #28a745;
  border-radius: 5px;
  transition: height 0.05s;
}
</style>
