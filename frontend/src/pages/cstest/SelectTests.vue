<script setup>
import { ref, reactive, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

// `api`와 관련된 부분이 정의되어 있어야 합니다.
// import api from "@/api";  // 예시로 추가

const route = useRoute();
const router = useRouter();

const page = ref({
  testList: [
    { bno: 1, type: "java", title: "Java의 기본 문법과 객체지향 개념" },
    { bno: 2, type: "java", title: "자바의 데이터 타입과 컬렉션 프레임워크" },
    { bno: 3, type: "java", title: "java 기초지식" },
    { bno: 4, type: "java", title: "Java에서의 멀티스레딩과 동기화" },
    { bno: 5, type: "java", title: "Java의 람다 표현식과 Stream API 사용" },
    { bno: 6, type: "java", title: "Java에서의 메모리 관리와 최적화 방법" },
    { bno: 7, type: "python", title: "Python의 기본 문법과 데이터 타입" },
    { bno: 8, type: "python", title: "Python에서의 파일 입출력 및 CSV 처리" },
    {
      bno: 9,
      type: "python",
      title: "Python에서의 메모리 관리와 가비지 컬렉션",
    },
    { bno: 10, type: "python", title: "Python의 제너레이터와 이터레이터" },
    { bno: 11, type: "vue", title: "컴포넌트에 대한 이해" },
    { bno: 12, type: "vue", title: "Vue Router 설정 및 네비게이션 가드 활용" },
    { bno: 13, type: "vue", title: "Vue의 라이프사이클에 대한 이해" },
    { bno: 14, type: "vue", title: "Vue의 반응성 시스템 이해하기" },
    {
      bno: 15,
      type: "vue",
      title: "Vue에서 컴포넌트 통신 방식 (Props, Emit, Provide/Inject)",
    },
    { bno: 16, type: "vue", title: "API 통신과 Axios 연동" },
    { bno: 17, type: "SQL", title: "INSERT문 기초" },
    { bno: 18, type: "SQL", title: "INSERT문 심화" },
  ],
  category: [
    { type: "all", name: "전체" },
    { type: "java", name: "JAVA" },
    { type: "python", name: "Python" },
    { type: "vue", name: "Vue" },
    { type: "SQL", name: "SQL" },
  ],
  totalCount: 10,
});

const pageRequest = reactive({
  page: parseInt(route.query.page) || 1,
  amount: parseInt(route.query.amount) || 12,
  searchType: "",
  searchValue: "",
  selectedType: "all",
});

const articles = computed(() =>
  page.value.testList.filter(
    (article) =>
      pageRequest.selectedType === "all" ||
      pageRequest.selectedType === article.type
  )
);

// 페이지가 변경될 때 호출
const handlePageChange = (pageNum) => {
  router.push({
    query: {
      page: pageNum,
      amount: pageRequest.amount,
      searchType: pageRequest.searchType,
      searchValue: pageRequest.searchValue,
      selectedType: pageRequest.selectedType,
    },
  });
};

// 검색이 변경될 때 호출
const searchChange = () => {
  router.push({
    query: {
      page: pageRequest.page,
      amount: pageRequest.amount,
      searchType: pageRequest.searchType,
      searchValue: pageRequest.searchValue,
      selectedType: pageRequest.selectedType,
    },
  });
};

// 기술스택 필터링
const toggleType = (type) => {
  pageRequest.selectedType = type;
};

// 쿼리로 데이터 로딩
const load = async (query) => {
  try {
    // 여기에 실제 API 호출을 추가해야 합니다
    // 예시: page.value = await api.getList(query);
    page.value = await api.getList(query); // 이 부분에서 실제 API를 호출해 데이터를 받아옵니다.
    if (!pageRequest.selectedType) {
      pageRequest.selectedType = "all";
    }
  } catch (error) {
    console.error("Failed to load data", error);
  }
};

// 페이지가 바뀔 때마다 데이터 로딩
watch(route, async () => {
  await load(route.query);
});

load(pageRequest);
</script>
<template>
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
          <label class="form-check-label" :for="item.type">{{
            item.name
          }}</label>
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
            {{
              page.category.find((value) => value.type === article.type)?.name
            }}
          </td>
          <td>
            <router-link :to="{ name: 'Setting', query: route.query }" class="router-link">
              {{ article.title }}
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="pagination-section my-5 d-flex justify-content-center">
      <vue-awesome-paginate
        :total-items="page.totalCount"
        :items-per-page="pageRequest.amount"
        :max-pages-shown="5"
        v-model="pageRequest.page"
        @click="handlePageChange"
      >
        <template #first-page-button><i class="fa-solid fa-backward-fast"></i></template>
        <template #prev-button><i class="fa-solid fa-caret-left"></i></template>
        <template #next-button><i class="fa-solid fa-caret-right"></i></template>
        <template #last-page-button><i class="fa-solid fa-forward-fast"></i></template>
      </vue-awesome-paginate>
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

</style>
