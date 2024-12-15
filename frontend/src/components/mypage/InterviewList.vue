<script setup>
import MypageHeader from "@/components/mypage/MypageHeader.vue";
import { ref, reactive, computed, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

const route = useRoute();
const router = useRouter();

const activeTap = ref("page");
const page = ref({
  testList: [],
  totalCount: 0,
});

const mno = 1;
const fetchReportList = async () => {
  try {
    console.log("fetchReportLsit");
    const response = await axios.get(
      `http://localhost:8080/api/report?mno=${mno}`
    );
    const reportList = response.data;
    console.log(reportList);
    page.value.testList = reportList.map((report) => ({
      content: report.content,
      score: report.score,
      companyName: report.companyName,
      job: report.job,
    }));
    page.value.totalCount = reportList.length;
  } catch (error) {
    console.error("Error fetching reporstList:", error);
    throw error;
  }
};

const pageRequest = reactive({
  page: parseInt(route.query.page) || 1,
  amount: parseInt(route.query.amount) || 8,
  searchType: "",
  searchValue: "",
  selectedType: "all",
});

const articles = computed(() => {
  // 선택된 타입에 따른 필터링
  const filteredArticles = page.value.testList.filter(
    (article) =>
      pageRequest.selectedType === "all" ||
      pageRequest.selectedType === article.type
  );

  // 페이지와 페이지당 항목 수를 고려하여 슬라이싱
  const startIndex = (pageRequest.page - 1) * pageRequest.amount;
  const endIndex = startIndex + pageRequest.amount;

  return filteredArticles.slice(startIndex, endIndex);
});
console.log("articles.computed", articles.value);

// 페이지가 변경될 때 호출
const handlePageChange = (pageNum) => {
  pageRequest.page = pageNum;
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
onMounted(async () => {
  pageRequest.page = parseInt(route.query.page) || 1;
  pageRequest.amount = parseInt(route.query.amount) || 8;
  await fetchReportList();
});
</script>

<template>
  <div class="container">
    <div class="row align-items-end rounded">
      <div class="col-3">
        <h6 class="form-label mb-2 fw-bold">검색어로 찾기</h6>
        <select
          v-model="pageRequest.searchType"
          class="form-select search-dropdown"
          required
        >
          <option value="">기업</option>
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

    <div class="total-count mt-4">
      <h4>{{ page.totalCount }} 문제</h4>
    </div>

    <table class="table mt-3 m shadow-sm">
      <thead>
        <tr>
          <th>기업</th>
          <th>직무</th>
          <th>총평</th>
          <th>점수</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="article in articles" :key="article.rno">
          <td>
            {{ article.companyName }}
          </td>
          <td>
            <span :class="{
            'front': article.job === '프론트',
            'back': article.job === '백엔드',
            'system': article.job ==='SW개발 및 시스템 운영'
          }">{{ article.job }}</span>
          </td>
          <td>
            <router-link
              :to="{ name: 'Setting', query: route.query }"
              class="router-link"
            >
              {{ article.content }}
            </router-link>
          </td>

          <td>
            <span :class="{
             'back': article.score >= 85, 
            'red': article.score < 85
            }">

              {{ article.score }}
            </span>
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
        @page-changed="handlePageChange"
      >
        <template #first-page-button
          ><i class="fa-solid fa-backward-fast"></i
        ></template>
        <template #prev-button><i class="fa-solid fa-caret-left"></i></template>
        <template #next-button
          ><i class="fa-solid fa-caret-right"></i
        ></template>
        <template #last-page-button
          ><i class="fa-solid fa-forward-fast"></i
        ></template>
      </vue-awesome-paginate>
    </div>
  </div>
</template>

<style scoped>
.title {
  font-weight: bold;
  color: #333;
}

.total-count {
  color: #333;
  font-weight: bold;
}

.table {
  width: 100%;
  background-color: #fff;
}


.router-link {
  color: inherit;
  text-decoration: none;
  display: block; /* Ensures the link spans the entire text cell */
  padding: 5px; /* Adds a little space around the link */
}

.table tbody tr:hover {
  background-color: #f1f1f1; /* Light background on hover */
  cursor: default; /* Prevent cursor change on non-clickable areas */
}

.table tbody tr .router-link:hover {
  color: #3e66df; /* Highlight on hover with a soft color */
  cursor: pointer; /* Ensure pointer cursor is only on the link */
}

.table tbody tr td {
  cursor: default; /* Ensure cursor remains default in non-clickable areas */
}
.table th,
.table td {
  vertical-align: middle; /* 세로 중앙 정렬 */
  text-align: center;
}

.table th:nth-child(1),
.table td:nth-child(1) {
  width: 20%; /* 기업 열의 너비 */
}

.table th:nth-child(2),
.table td:nth-child(2) {
  width: 20%; /* 직무 열의 너비 */
}

.table th:nth-child(3),
.table td:nth-child(3) {
  width: 50%; /* 총평 열의 너비 */
}

.table th:nth-child(4),
.table td:nth-child(4) {
  width: 10%; /* 점수 열의 너비 */
}

.table th {
  text-align: center;
  background-color: #f8f9fa; /* 헤더 배경 색상 */
}

.table tbody tr:hover {
  background-color: #f1f1f1; /* 행 hover 시 배경색 */
  cursor: default; /* 비클릭 영역에서 커서 변경되지 않게 */
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

.back {
  color: #1976d2;
}

.front {
  color: #28a745;
}
.system {
  color: #f28a1b;
}
.red {
  color: #dc3545;
}

</style>
