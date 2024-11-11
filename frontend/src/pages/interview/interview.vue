<template>
  <div class="container mt-5">
    <!-- 질문1 동그라미 버튼 -->
    <div class="question-circle">질문 {{ currentQuestionIndex + 1 }}</div>

    <!-- 질문 내용 동적으로 변경 -->
    <div class="question-text mt-3">
      {{ currentQuestion }}
    </div>

    <!-- 카메라와 시간 초, 왼쪽은 사용자 카메라, 오른쪽은 시간 초 -->
    <div class="row mt-4 justify-content-center">
      <!-- 왼쪽: 사용자 카메라 -->
      <div class="col-6 d-flex justify-content-center align-items-center">
        <div class="circle camera">
          <video ref="videoElement" autoplay></video>
        </div>
      </div>
      <!-- 오른쪽: 시간 초 -->
      <div class="col-6 d-flex justify-content-center align-items-center">
        <div class="circle timer">{{ time }}초</div>
      </div>
    </div>

    <!-- 답변 완료 버튼 -->
    <div class="text-center mt-4">
      <button class="btn btn-primary" @click="submitAnswer">답변완료</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";

// 시간 상태
const time = ref(0);
let timerInterval = null; // 타이머를 추적할 변수

// 질문 배열
const questions = [
  "MVC 패턴에 대해 설명해주세요.",
  "왜 이 문제를 선택했나요?",
  "이 문제를 해결하는 방법에 대해 설명해주세요.",
  "당신의 강점은 무엇인가요?",
];

// 현재 질문 인덱스
const currentQuestionIndex = ref(0);

// 현재 질문
const currentQuestion = computed(() => questions[currentQuestionIndex.value]);

// 비디오 요소 참조
const videoElement = ref(null);

// 타이머 시작 함수
const startTimer = () => {
  time.value = 0; // 타이머 초기화
  // 기존 타이머가 있다면 clearInterval로 정리
  if (timerInterval) {
    clearInterval(timerInterval);
  }
  // 새로운 타이머 설정
  timerInterval = setInterval(() => {
    time.value += 1; // 초 1씩 증가
  }, 1000); // 1초마다 실행
};

onMounted(() => {
  // 웹캠 스트림을 가져오는 함수
  const startVideo = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      if (videoElement.value) {
        videoElement.value.srcObject = stream; // 비디오 스트림을 video element에 연결
      }
    } catch (err) {
      console.error("웹캠을 가져오는데 실패했습니다:", err);
    }
  };

  startVideo(); // 웹캠 연결 시작
  startTimer(); // 타이머 시작
});

// 답변 완료 버튼 클릭 시 이벤트 처리
const submitAnswer = () => {
  console.log("답변 완료");
  // 질문을 다음으로 변경
  currentQuestionIndex.value =
    (currentQuestionIndex.value + 1) % questions.length;

  // 타이머를 초기화하고 다시 시작
  startTimer();
};
</script>

<style scoped>
.question-circle {
  width: 100px;
  height: 50px;
  border-radius: 25px;
  background-color: #bfd3ff;
  color: black;
  font-size: 20px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
}

.question-text {
  font-size: 1.2rem;
  font-weight: bold;
  text-align: center;
}

.row {
  margin-top: 40px;
}

.circle {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 50px;
  color: black;
  font-weight: 500;
  background-color: #4e73df;
}

.camera {
  background-color: #28a745; /* 카메라 아이콘 배경색 */
}

.timer {
  background-color: #ffc107; /* 타이머 배경색 */
}

video {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 화면 비율 유지하며 크기 맞추기 */
  border-radius: 50%; /* 동그라미 모양 */
}

.btn {
  width: 200px;
  font-size: 1.2rem;
}
</style>
