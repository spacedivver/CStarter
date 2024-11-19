
<template>
  <CStestHeader/>
  <div class="container">
    <h2 class="title mb-4"><i class="fa-solid fa-copy"></i> 문제 목록</h2>
    <div class="filter-section rounded shadow-sm">
      <div class="p-3">
        <h6 class="form-label mb-2 fw-bold">기술스택</h6>
        <div
          v-for="item in page.category"
          :key="item.type"
          class="form-check form-check-inline"
        >
          <input
            v-model="pageRequest.selectedType"
            class="form-check-input"
            type="radio"
            :id="item.type"
            :value="item.type"
            @change="toggleType(item.type)"
          />
          <label class="form-check-label" :for="item.type">{{ item.name }}</label>
        </div>
      </div>

      <div class="row align-items-end ps-3 pb-3 pe-3 rounded">
        <div class="col-3">
          <h6 class="form-label mb-2 fw-bold">검색어로 찾기</h6>
          <select
            v-model="pageRequest.searchType"
            class="form-select search-dropdown"
            required
          >
            <option value="">기술스택</option>
            <option
              v-for="item in page.category"
              :key="item.type"
              :value="item.type"
            >
              {{ item.name }}
            </option>
          </select>
        </div>
        <div class="col-9">
          <div class="input-group">
            <input
              v-model="pageRequest.searchValue"
              @keyup.enter="searchChange"
              type="text"
              class="form-control search-input"
              placeholder="검색어를 입력하세요."
            />
            <span class="input-group-text search-button">
              <button class="btn-icon" @click="searchChange">
                <i class="fa fa-search"></i>
              </button>
            </span>
          </div>
        </div>
      </div>
    </div>

    <div class="total-count mt-4">
      <h4>{{ page.totalCount }} 문제</h4>
    </div>
    <table class="table mt-3 m shadow-sm">
      <thead>
        <tr>
          <th>상태</th>
          <th>기술스택</th>
          <th>제목</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="article in articles" :key="article.bno">
          <td><i class="fa fa-check ms-2"></i></td>
          <td :class="`stack-${article.type}`">
            {{ page.category.find((value) => value.type === article.type)?.name }}
          </td>
          <td>
            <router-link :to="{ name: 'CStestSetting', query: route.query }" class="router-link">
              {{ article.title }}
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="pagination-section my-5 d-flex justify-content-center">
      <button 
        class="pagination-button" 
        @click="handlePageChange(pageRequest.page - 1)" 
        :disabled="pageRequest.page <= 1"
      >
        이전
      </button>
      <span class="pagination-info">페이지 {{ pageRequest.page }}</span>
      <button 
        class="pagination-button" 
        @click="handlePageChange(pageRequest.page + 1)" 
        :disabled="pageRequest.page >= Math.ceil(page.totalCount / pageRequest.amount)"
      >
        다음
      </button>
    </div>
  </div>
</template>


<script setup>
import CStestHeader from "@/components/cstest/CStestHeader.vue";
import { ref, reactive, computed, onMounted } from "vue";
import axios from "axios"; // Axios import 추가
import { useRoute } from "vue-router";

const page = ref({
  testList: [], // 전체 문제 목록
  displayedList: [], // 현재 페이지에 표시할 문제 목록
  category: [],
  totalCount: 0, // 전체 문제 수
});

const route = useRoute();

// 페이지 요청을 위한 기본값 설정
const pageRequest = reactive({
  page: 1, // 현재 페이지
  amount: 10, // 페이지당 보여줄 문제 수
  searchType: "",
  searchValue: "",
  selectedType: "all", // 기본 선택 카테고리
});

// API에서 데이터를 가져오는 함수
const loadQuestions = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/interview/tech/question');
    const questions = response.data; // API 응답 데이터
    page.value.testList = questions; // 전체 문제 목록 업데이트
    page.value.totalCount = questions.length; // 전체 문제 수 업데이트
    updateDisplayedList(); // 현재 페이지에 표시할 문제 목록 업데이트

    // 카테고리 동적으로 추가 (중복 제거)
    const uniqueCategories = new Set(questions.map(q => q.type));
    page.value.category = [
      { type: "all", name: "전체" },
      ...Array.from(uniqueCategories).map(type => ({ type, name: type }))
    ];
    
  } catch (error) {
    console.error("Failed to load questions:", error);
  }
};

// 현재 페이지에 표시할 문제 목록 업데이트
const updateDisplayedList = () => {
  const filteredList = page.value.testList.filter(article =>
    pageRequest.selectedType === "all" || article.type === pageRequest.selectedType
  );
  
  // 필터링된 문제 수에 따라 총 개수 업데이트
  page.value.totalCount = filteredList.length;

  const start = (pageRequest.page - 1) * pageRequest.amount;
  const end = start + pageRequest.amount;
  page.value.displayedList = filteredList.slice(start, end);
};

// 페이지 변경 핸들러
const handlePageChange = (pageNum) => {
  pageRequest.page = pageNum; // 페이지 변경
  updateDisplayedList(); // 현재 페이지에 표시할 문제 목록 업데이트
};

// 카테고리 변경 핸들러
const toggleType = (type) => {
  pageRequest.selectedType = type; // 선택한 카테고리 변경
  pageRequest.page = 1; // 항상 첫 페이지로 리셋
  updateDisplayedList(); // 현재 페이지에 표시할 문제 목록 업데이트
};

// 컴포넌트 마운트 시 데이터 로딩
onMounted(() => {
  loadQuestions();
});

// 필터링된 아티클
const articles = computed(() => page.value.displayedList);

</script>


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

.stack-HTML {
  color: #003cff;
}


.stack-Java {
  color: #f28a1b;
}

.stack-Python {
  color: #1976d2;
}

.stack-Vue {
  color: #28a745;
}

.stack-MySQL {
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
.btn-icon {
  border: none; /* 기본 테두리 제거 */
  background: none; /* 배경 제거 */
  padding: 0; /* 패딩 제거 */
  cursor: pointer; /* 포인터 커서 설정 */
}

.btn-icon:focus {
  outline: none; /* 포커스 시 아웃라인 제거 */
}

.btn-icon i {
  color: inherit; /* 아이콘 색상 상속 */
}

/* 페이지네이션 버튼 스타일 */
.pagination-button {
  background-color: #007bff; /* 버튼 배경색 */
  color: white; /* 버튼 글자색 */
  border: none; /* 테두리 제거 */
  padding: 8px 12px; /* 패딩 */
  margin: 0 5px; /* 버튼 간격 */
  border-radius: 5px; /* 모서리 둥글게 */
  cursor: pointer; /* 포인터 커서 설정 */
  transition: background-color 0.3s; /* 배경색 전환 효과 */
}

.pagination-button:hover {
  background-color: #0056b3; /* hover 시 배경색 */
}

.pagination-button:disabled {
  background-color: #ccc; /* 비활성화 상태 색상 */
  cursor: not-allowed; /* 비활성화 상태에서 커서 */
}

.pagination-info {
  align-self: center; /* 가운데 정렬 */
  margin: 0 10px; /* 간격 조정 */
}

</style>
