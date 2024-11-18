<template>
  <div class="container">
    <h5>결과</h5>
    <h1 class="mb-4">
      <div>
        <span class="fw-bold">92</span>
        <span class="fw-light">점 <br /></span>
      </div>
    </h1>
    <h5>총평</h5>
    <p>
      면접 답변은 매우 체계적이고 핵심을 잘 전달하셨습니다. 긍정적인 부분으로는 핵심 요점을 간결하게 잘 전달했고, 기술 간 비교 설명이 명확하며, 구체적인 예시로 실무 적용성을 강조한 점이 돋보였습니다. 개선할 점은 일부 기술의 보안적 측면이나 세부사항을 보강할 필요가 있고, 자신의 경험과 연결하면 실무 능력 어필에 도움이 될 수 있으며, 답변의 결론을 명확히 해주면 더 좋습니다. 전반적으로 잘 준비된 답변이었고, 약간의 세부 보강으로 훌륭한 면접 답변을 완성하도록 하십시오.
    </p>

    <div v-for="(item, index) in qna" :key="index">
      <div class="form-group">
        <!-- 제목 앞에 인덱스 추가 -->
        <h5 class="title">
          <span class="index">{{ index + 1 }}.</span> {{ item.title }}
        </h5>
        <!-- 구분선 -->
        <div class="divider"></div>
        <!-- 내용 -->
         <div class="row d-flex">
<div class="col-6">
<h5 class="fw-bold">나의 답변</h5 >
  <p class="content">{{ item.content }}</p>
</div>
<div class="col-6">
  <h5 class="fw-bold">모범 답변</h5 >
<p class="content">{{ item.content }}</p>
</div>
          </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const qna = ref([
  {
    title: "SQL과 NoSQL의 차이점은 무엇인가요?",
    content:
      "SQL은 정형화된 데이터 구조(테이블)에서 관계형 데이터베이스를 다루기 위한 쿼리 언어입니다. 강력한 트랜잭션 관리와 데이터 무결성을 보장하는 반면, 데이터가 자주 변하거나 구조가 고정되지 않은 경우엔 유연성이 떨어질 수 있습니다. NoSQL은 문서형, 키-값, 열형 등 다양한 데이터 모델을 지원하는 비관계형 데이터베이스입니다. 특히 대용량 데이터 처리나 빠른 스케일링에 적합합니다. MongoDB와 같은 NoSQL은 비정형 데이터를 저장할 때 유리합니다.",
  },
  {
    title: "React와 Vue.js의 차이점은 무엇인가요?",
    content:
      "React는 컴포넌트 기반의 UI 라이브러리로, 함수형 프로그래밍 개념을 바탕으로 하고 있어 자유도가 높습니다. 사용자는 필요한 라이브러리와 도구를 선택하여 구성할 수 있습니다. Vue.js는 프레임워크로, React에 비해 진입 장벽이 낮고, 양방향 데이터 바인딩을 지원하여 데이터와 UI의 동기화가 쉽습니다. Vue는 보일러플레이트 코드가 적고, 소규모 프로젝트부터 대규모 프로젝트까지 사용 가능합니다.",
  },
]);
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
</style>
