<template>
  <CStestHeader />
  <div class="container mb-5p">
    <div class="title">커리큘럼</div>

    <!-- 키워드 검색 영역 -->
    <div class="search-container">
      <label for="keyword" class="col-2 col-form-label">과정명 검색</label>
      <div class="col-9">
        <div class="input-group input-group-sm">
          <input
            type="text"
            class="form-control ms-1 rounded"
            placeholder="과정명을 입력해 주세요."
            v-model="searchValue"
          />
          <button class="btn btn-primary ms-2" @click="search">검색하기</button>
        </div>
      </div>
    </div>

    <!-- Frontend 체크박스 -->
    <div class="checkbox-container">
      <input
        type="checkbox"
        id="frontend-checkbox"
        v-model="showFrontendPills"
        class="pill-checkbox"
      />
      <label for="frontend-checkbox" class="pill-label">Frontend</label>
    </div>
    <div class="pill-container" v-show="showFrontendPills">
      <ul class="nav nav-pills flex-row">
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill1"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Frontend Link1"
          />
          <label for="pill1" class="nav-label">Link 1</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill2"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Frontend Link2"
          />
          <label for="pill2" class="nav-label">Link 2</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill3"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Frontend Link3"
          />
          <label for="pill3" class="nav-label">Link 3</label>
        </li>
      </ul>
    </div>

    <!-- Backend 체크박스 -->
    <div class="checkbox-container">
      <input
        type="checkbox"
        id="backend-checkbox"
        v-model="showBackendPills"
        class="pill-checkbox"
      />
      <label for="backend-checkbox" class="pill-label">Backend</label>
    </div>
    <div class="pill-container" v-show="showBackendPills">
      <ul class="nav nav-pills flex-row">
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill4"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Backend Link1"
          />
          <label for="pill4" class="nav-label">Link 1</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill5"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Backend Link2"
          />
          <label for="pill5" class="nav-label">Link 2</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill6"
            class="nav-checkbox"
            v-model="selectedItems"
            value="Backend Link3"
          />
          <label for="pill6" class="nav-label">Link 3</label>
        </li>
      </ul>
    </div>

    <!-- 선택된 항목 보내기 버튼 -->
    <div class="row col-2">
      <button class="send-button" @click="handleComplete">선택 완료</button>
    </div>
  </div>
</template>

<script>
import CStestHeader from "./CStestHeader.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";

export default {
  components : {
    CStestHeader,
  },
  setup() {
    const router = useRouter();
    const searchValue = ref("");
    const showFrontendPills = ref(false);
    const showBackendPills = ref(false);
    const selectedItems = ref([]);

    const handleComplete = () => {
      router.push("/CSTest/SelectTests");
    };

    const sendSelectedItems = () => {
      console.log("선택된 항목:", selectedItems.value);
      fetch("/api/selected-items", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ selectedItems: selectedItems.value }),
      })
        .then((response) => response.json())
        .then((data) => console.log("서버 응답:", data))
        .catch((error) => console.error("오류:", error));
    };

    return {
      searchValue,
      showFrontendPills,
      showBackendPills,
      selectedItems,
      handleComplete,
      sendSelectedItems,
    };
  },
};
</script>

<style scoped>
.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.search-container {
  margin-bottom: 20px;
}

.checkbox-container {
  margin-bottom: 10px;
}

.pill-checkbox {
  margin-right: 8px;
}

.pill-label {
  font-size: 18px;
  cursor: pointer;
  color: #007bff;
  font-weight: bold;
}

.pill-container {
  margin-top: 10px;
}

.nav-pills {
  margin-top: 10px;
  list-style: none;
  padding: 0;
}

.nav-item {
  margin-right: 10px;
}

.nav-checkbox {
  display: none;
}

.nav-label {
  padding: 8px 16px;
  border: 1px solid #007bff;
  border-radius: 4px;
  color: #007bff;
  cursor: pointer;
  background-color: #fff;
}

.nav-checkbox:checked + .nav-label {
  background-color: #007bff;
  color: white;
}

.send-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  width: 100%;
}

.send-button:hover {
  background-color: #218838;
}

.send-button:active {
  background-color: #1e7e34;
}

.row {
  margin-top: 20px;
}

</style>
