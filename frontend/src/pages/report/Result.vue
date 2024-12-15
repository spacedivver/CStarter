<template>
  <LetterHeader />
  <div class="container mb-5p">
    <div class="form-group">
      <h5 class="m-0 mt-3">결과</h5>
      <div class="result-container">
        <h1 class="result-score">
          <div class="score-text">
            <span class="fw-bold " style="color:#007bff">{{ score }}</span>
            <span class="fw-light">점</span>
          </div>
          <img src="@/assets/images/report/medal.png" alt="Medal" class="medal-image" />
        </h1>
      </div>
      <div class="divider"></div>
      <h5>총평</h5>

      <p>
        {{ content }}
      </p>
    </div>
    
<!-- 평가 요소 추가 -->
<div class="form-group">
  <div class="evaluation">
    <div class="evaluation-row mb-2">
      <div class="evaluation-item ps-0  mb-0">
        <h5 class="fw-bold">직무역량 <span class="red">{{ jobCompetenceScore }}점</span></h5>
        <h6 class="fw-light">직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유</h6>
      </div>
      <div class="evaluation-item mb-0">
        <h5 class="fw-bold">직업 기초 능력 <span class="red">{{ logicalAbilityScore }}점</span> <span class="fw-light">(논리성)</span></h5>
        <h6 class="fw-light">직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유</h6>
      </div>
    </div>
    <div class="evaluation-row  mb-0">
      <div class="evaluation-item ps-0">
        <h5 class="fw-bold">로열티 <span class="red">{{ loyaltyScore }}점</span> <span class="fw-light">(기업 비전 및 사업 이해도)</span> </h5>
        <h6 class="fw-light">직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유</h6>
      </div>
      <div class="evaluation-item mb-0">
        <h5 class="fw-bold">인성 <span class="red">{{ personalityScore }}점</span> <span class="fw-light">(핵심 가치 및 회사 인재상 적합성)</span></h5>
        <h6 class="fw-light">직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유직무역량이 몇점몇점인이유</h6>
      </div>
    </div>
  </div>
</div>


    <div v-for="(item, index) in answerList" :key="index">
      <div class="form-group">
        <!-- 제목 앞에 인덱스 추가 -->
        <h5 class="title">
          <span class="index">{{ index + 1 }}.</span> {{ item.question }}
        </h5>
        <!-- 구분선 -->
        <div class="divider"></div>
        <!-- 내용 -->
        <div class="row d-flex">
          <div class="col-6">
            <h5 class="fw-bold">나의 답변</h5>
            <p class="content">{{ item.answer }}</p>
          </div>
          <div class="col-6">
            <h5 class="fw-bold">모범 답변</h5>
            <p class="content">{{ item.feedback }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import LetterHeader from "@/components/letter/LetterHeader.vue";

const score = ref(0);
const content = ref("");
const rno = 70;
const jobCompetenceScore = ref(15); // 직무역량 점수
const logicalAbilityScore = ref(25); // 직업 기초 능력 점수
const loyaltyScore = ref(27); // 로열티 점수
const personalityScore = ref(35); // 인성 점수
// answerList 변수 정의 및 초기화
const answerList = ref([]); // 빈 배열로 초기화

const fetchReports = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/report/${rno}/feedback`
    );

    // score와 content 설정
    score.value = response.data.score;
    content.value = response.data.content || "총평이 없습니다.";

    // API 응답에서 feedback 배열 추출
    const feedbackList = response.data.feedback;

    // 필요한 데이터만 매핑
    answerList.value = feedbackList.map((item) => ({
      question: item.question, // 질문
      answer: item.answer || "답변이 없습니다.", // 답변 (없으면 기본값 설정)
      feedback: item.feedback || "피드백이 없습니다.", // 피드백 (없으면 기본값 설정)
    }));
  } catch (error) {
    console.error("Error fetching feedback:", error);
  }
};

onMounted(() => {
  fetchReports();
});
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.divider {
  height: 1px;
  background-color: #ddd;
  margin: 10px 0;
}

.result-container {
  display: flex;
  justify-content: space-between; /* 요소들을 양쪽 끝으로 정렬 */
  align-items: center; /* 수직 정렬 */
}

.result-score {
  display: flex;
  align-items: center; /* 수직 정렬 */
}

.medal-image {
  height: 100px; /* 높이를 늘려서 rowspan 효과를 줌 */
  margin-top: -40px; /* 점수와 메달 이미지의 위치 조정 */
  margin-left: 550px;
}


.title {
  font-weight: bold;
  font-size: 1.2em;
  color: #333;
  background-color: #e9f5ff; /* 제목 배경색 */
  padding: 10px;
  border-radius: 8px;
  display: flex;
  align-items: center;
}

.index {
  font-weight: bold;
  color: #007bff;
  margin-right: 8px;
}

.content {
  font-size: 1em;
  color: #555;
  margin-top: 8px;
  line-height: 1.6;
}

.mb-5p {
  margin-bottom: 5%;
}

.evaluation {
  display: flex;
  flex-direction: column; /* 세로 방향으로 정렬 */
}

.evaluation-row {
  display: flex; /* 가로 방향으로 정렬 */
  justify-content: space-between; /* 항목 간의 간격을 균등하게 배분 */
  margin-bottom: 20px; /* 각 행 간의 간격 */
}

.evaluation-item {
  flex: 1; /* 각 항목이 동일한 너비를 가지도록 설정 */
  margin-right: 10px; /* 오른쪽 항목과의 간격 */
  padding: 10px;

}

.evaluation-item:last-child {
  margin-right: 0; /* 마지막 항목은 오른쪽 여백 제거 */
}

.red {
  color:#f28a1b;
}
</style>
