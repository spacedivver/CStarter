<script setup>
import MypageHeader from "@/components/mypage/MypageHeader.vue";
import { ref} from "vue";
import CStestList from "@/components/mypage/CStestList.vue";
import InterviewList from "@/components/mypage/InterviewList.vue";

const currentView = ref("CStest"); // 초기값: CS테스트 리스트
const changeView = (view) => {
  currentView.value = view;
};
</script>

<template>
    <MypageHeader/>
  <div class="container">
    <h4 class="title mb-4 d-flex justify-content-center"><i class="fa-solid fa-copy"></i> 푼 문제</h4>
    <p> 현재 뷰 {{ currentView }}</p>
    <div class="row">
        <ul
          class="nav nav-tabs custom justify-content-center"
          id="myTab"
          role="tablist"
        >
          <li class="nav-item">
            <button
              class="nav-link"
              :class = "{active: currentView === 'CStest'}"
              @click="changeView('CStest')"
              ><h6>CS테스트</h6></button>
          </li>
          <li class="nav-item">
            <button
              class="nav-link"
              :class = "{active: currentView === 'Interview'}"
              @click="changeView('Interview')"
              ><h6>모의면접</h6></button
            >
          </li>
        </ul>
        <div class="content">
      <component :is="currentView === 'CStest' ? CStestList : InterviewList" />
    </div>
      </div>
    
  </div>
</template>

<style scoped>
.title {
  font-weight: bold;
  color: #333;
}

.filter-section {
  background-color: #f8f9fa;
}

.total-count {
  color: #333;
  font-weight: bold;
}

.table {
  width: 100%;
  background-color: #fff;
}

.stack-java {
  color: #f28a1b;
}

.stack-python {
  color: #1976d2;
}

.stack-vue {
  color: #28a745;
}

.stack-SQL {
  color: #dc3545;
}

.router-link {
  color: inherit;
  text-decoration: none;
  display: block; /* Ensures the link spans the entire text cell */
  padding: 5px;  /* Adds a little space around the link */
}

.table tbody tr:hover {
  background-color: #f1f1f1; /* Light background on hover */
  cursor: default; /* Prevent cursor change on non-clickable areas */
}

.table tbody tr .router-link:hover {
  color: #3E66DF; /* Highlight on hover with a soft color */
  cursor: pointer; /* Ensure pointer cursor is only on the link */
}

.table tbody tr td {
  cursor: default; /* Ensure cursor remains default in non-clickable areas */
}
/* 각 테이블 열에 고정된 너비를 설정 */
.table th, .table td {
  vertical-align: middle; /* 세로 중앙 정렬 */
  width: 10%;
}

.table th {
  text-align: center;
}

/* 각 기술스택의 열 너비도 고정 */
.table th:nth-child(2), .table td:nth-child(2) {
  width: 20%;
  text-align: center
}

.table th:nth-child(3), .table td:nth-child(3) {
  width: 60%;
}

.table {
  width: 100%;
  background-color: #fff;
  border-collapse: collapse; /* 테두리 겹침 방지 */
  border: 1px solid #ddd; /* 테이블 전체에 외곽선 추가 */
}

.table th {
  vertical-align: middle;
}

.table th {
  text-align: center;
  background-color: #f8f9fa; /* 헤더 배경 색상 */
}

.table tbody tr:hover {
  background-color: #f1f1f1; /* 행 hover 시 배경색 */
  cursor: default; /* 비클릭 영역에서 커서 변경되지 않게 */
}
.header-bg {
  background-color: #d3f1fd;
  margin-bottom: 30px;
}
.nav-link.active {
  color: #007bff;
  font-weight: bold;
  border-bottom: 2px solid !important;
}
</style>
