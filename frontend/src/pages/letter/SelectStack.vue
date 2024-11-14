<template>
  <div class="container">
    <div class="title">커리큘럼</div>

    <!-- 키워드 검색 영역 -->
    <div class="search-container">
      <label for="keyword" class="col-2 col-form-label">과정명 선택</label>
      <div class="col-9">
        <div class="input-group input-group-sm">
          <input
            type="text"
            class="form-control ms-1 rounded"
            placeholder="과정명을 입력해 주세요."
            v-model="searchValue"
            @input="filterCourses"
          />
          <button class="btn btn-primary ms-2" @click="search">검색하기</button>
        </div>
      </div>
    </div>

    <!-- 과정 선택 -->
    <div class="course-selection">
      <el-select
        v-model="selectedCourse"
        placeholder="과정을 선택해 주세요."
        class="w-100"
      >
        <el-option
          v-for="course in filteredCourses"
          :key="course.value"
          :label="course.label"
          :value="course.value"
        />
      </el-select>
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
        <li class="nav-item" v-for="(link, index) in frontendLinks" :key="index">
          <input
            type="checkbox"
            :id="'frontend-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link"
          />
          <label :for="'frontend-pill' + index" class="nav-label">{{ link }}</label>
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
        <li class="nav-item" v-for="(link, index) in backendLinks" :key="index">
          <input
            type="checkbox"
            :id="'backend-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link"
          />
          <label :for="'backend-pill' + index" class="nav-label">{{ link }}</label>
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
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    const searchValue = ref("");
    const selectedCourse = ref(null);
    const showFrontendPills = ref(false);
    const showBackendPills = ref(false);
    const selectedItems = ref([]);

    // 부트캠프 과정 데이터
    const courses = ref([
      { value: 'frontend', label: 'Frontend 과정' },
      { value: 'backend', label: 'Backend 과정' },
      { value: 'fullstack', label: 'Fullstack 과정' },
    ]);

    const frontendLinks = ref(["Frontend Link 1", "Frontend Link 2", "Frontend Link 3"]);
    const backendLinks = ref(["Backend Link 1", "Backend Link 2", "Backend Link 3"]);

    // 필터링된 과정 배열
    const filteredCourses = computed(() => {
      if (!searchValue.value) {
        return courses.value; // 검색어가 없으면 전체 과정 반환
      }
      return courses.value.filter(course =>
        course.label.toLowerCase().includes(searchValue.value.toLowerCase())
      );
    });

    const handleComplete = () => {
      console.log("선택된 과정:", selectedCourse.value);
      console.log("선택된 항목:", selectedItems.value);
      router.push("/Letter/SelectCompany");
    };

    return {
      searchValue,
      selectedCourse,
      filteredCourses,
      showFrontendPills,
      showBackendPills,
      selectedItems,
      frontendLinks,
      backendLinks,
      handleComplete,
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

.course-selection {
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
