<script setup>
import { ref, reactive, computed, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const page = ref({
  testList: [
    { bno: 1, type: "java", title: "java 기초지식" },
    { bno: 2, type: "python", title: "python 기초지식" },
    { bno: 3, type: "java", title: "객체 지향 프로그래밍 특징" },
  ],
  category: [
    { type: "all", name: "전체" },
    { type: "java", name: "java" },
    { type: "python", name: "python" },
  ],
  totalCount: 3,
});

const articles = computed(() =>
  page.value.testList.filter(
    (article) =>
      pageRequest.types.includes("all") ||
      pageRequest.types.includes(article.type)
  )
);

const pageRequest = reactive({
  page: parseInt(route.query.page) || 1,
  amount: parseInt(route.query.amount) || 12,
  searchType: "",
  searchValue: "",
  types: ["all"],
});

const handlePageChange = (pageNum) => {
  router.push({
    query: {
      page: pageNum,
      amount: pageRequest.amount,
      searchType: pageRequest.searchType,
      searchValue: pageRequest.searchValue,
      types: pageRequest.types,
    },
  });
};

const searchChange = () => {
  router.push({
    query: {
      page: pageRequest.page,
      amount: pageRequest.amount,
      searchType: pageRequest.searchType,
      searchValue: pageRequest.searchValue,
      types: pageRequest.types,
    },
  });
};

watch(route, async () => {
  await load(route.query);
});

const toggleType = (type) => {
  if (type === "all") {
    if (pageRequest.types.includes("all")) {
      pageRequest.types = [];
    } else {
      pageRequest.types = [
        "all",
        ...page.value.category
          .map((item) => item.type)
          .filter((t) => t !== "all"),
      ];
    }
  } else {
    if (pageRequest.types.includes(type)) {
      pageRequest.types = pageRequest.types.filter((t) => t !== type);
    }
  }
};

const load = async (query) => {
  try {
    page.value = await api.getList(query); // Ensure api is defined/imported
    if (pageRequest.types.length === 0) {
      page.value.category.forEach((element) => {
        pageRequest.types.push("all");
      });
    }
  } catch (error) {
    console.error("Failed to load data", error);
  }
};
load(pageRequest);
</script>

<template>
  <div>
    <h2 class="mb-3"><i class="fa-solid fa-copy"></i> 문제 목록</h2>

    <div class="mt-4">
      <h5 class="form-label">기술스택</h5>
      <div
        v-for="item in page.category"
        :key="item.type"
        class="form-check form-check-inline"
      >
        <input
          v-model="pageRequest.types"
          checked
          class="form-check-input"
          type="checkbox"
          :id="item.type"
          :value="item.type"
        />
        <label class="form-check-label" :for="item.type">{{ item.name }}</label>
      </div>
    </div>

    <div class="row mt-2 align-items-end">
      <div class="col-3">
        <label class="form-label">검색 종류</label>
        <select v-model="pageRequest.searchType" class="form-select" required>
          <option value="">선택하세요.</option>
          <option
            v-for="item in page.category"
            :key="item.type"
            :value="item.type"
          >
            {{ item.name }}
          </option>
        </select>
      </div>
      <div class="col-7">
        <label class="form-label">검색어</label>
        <input
          v-model="pageRequest.searchValue"
          @keyup.enter="searchChange"
          type="text"
          class="form-control"
          placeholder="내용을 입력하세요."
        />
      </div>
      <div class="col-2">
        <button class="form-control btn btn-primary" @click="searchChange">
          검색
        </button>
      </div>
    </div>

    <div class="mt-3 text-end">(총 {{ page.totalCount }}건)</div>

    <table class="table table-striped">
      <thead>
        <tr>
          <th>상태</th>
          <th>기술스택</th>
          <th>제목</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="article in articles" :key="article.bno">
          <td>{{ article.bno }}</td>
          <td>
            {{
              page.category.find((value) => value.type === article.type)?.name
            }}
          </td>
          <td>
            <router-link :to="{ name: 'Setting', query: route.query }">
              {{ article.title }}
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="my-5 d-flex justify-content-center">
      <vue-awesome-paginate
        :total-items="page.totalCount"
        :items-per-page="pageRequest.amount"
        :max-pages-shown="5"
        v-model="pageRequest.page"
        @click="handlePageChange"
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
