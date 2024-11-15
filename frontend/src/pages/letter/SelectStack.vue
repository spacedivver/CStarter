<template>
  <div class="container">
    <div class="title">커리큘럼</div>

    <!-- 과정 선택 -->
    <div class="course-selection">
      <el-select
        v-model="selectedCourse"
        placeholder="과정을 선택해 주세요."
        class="w-100"
        filterable
        :remote="true"
        :remote-method="handleFilterCourses"
        :loading="loading"
      >
        <el-option
          v-for="course in filteredCourses"
          :key="course.value"
          :label="course.label"
          :value="course.value"
        />
      </el-select>
    </div>
    <div class="title">기술스택 추가선택</div>

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
    <div class="pill-container mb-3" v-show="showFrontendPills">
      <ul class="nav nav-pills flex-row flex-wrap">
        <li class="nav-item" v-for="(link, index) in frontendLinks" :key="index">
          <input
            type="checkbox"
            :id="'frontend-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link.name"
          />
          <label :for="'frontend-pill' + index" class="nav-label">
            <img :src="link.icon" alt="" class="pill-icon" />
            {{ link.name }}
          </label>
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
      <ul class="nav nav-pills flex-row flex-wrap">
        <li class="nav-item" v-for="(link, index) in backendLinks" :key="index">
          <input
            type="checkbox"
            :id="'backend-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link.name"
          />
          <label :for="'backend-pill' + index" class="nav-label">
            <img :src="link.icon" alt="" class="pill-icon" />
            {{ link.name }}
          </label>
        </li>
      </ul>
    </div>

    <!-- 선택된 항목 보내기 버튼 -->
    <div style="width: 150px;" class="mt-3">
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
    const selectedCourse = ref(null);
    const showFrontendPills = ref(false);
    const showBackendPills = ref(false);
    const selectedItems = ref([]);
    const loading = ref(false);
    const searchQuery = ref("");

    // 부트캠프 과정 데이터
    const courses = ref([
      { value: 'frontend', label: 'Frontend 과정' },
      { value: 'backend', label: 'Backend 과정' },
      { value: 'fullstack', label: 'Fullstack 과정' },
    ]);
    const frontendLinks = ref([
  { name: "HTML", icon: "/src/assets/images/icons/html.png" },
  { name: "CSS", icon: "/src/assets/images/icons/css.png" },
  { name: "JavaScript", icon: "/src/assets/images/icons/javascript.png" },
  { name: "React", icon: "/src/assets/images/icons/react.svg" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
  { name: "Vue.js", icon: "/src/assets/images/icons/vue.png" },
]);

const backendLinks = ref([
{ name: "Node.js", icon: "/src/assets/images/icons/nodejs.png" },
  { name: "Express", icon: "/src/assets/images/icons/expressjs.png" },
  { name: "Django", icon: "/src/assets/images/icons/django.png" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },
  { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.jpg" },

  // { name: "Laravel", icon: "/src/assets/images/icons/laravel.png" },
  // { name: "ASP.NET", icon: "/src/assets/images/icons/aspnet.png" },
  // { name: "PHP", icon: "/src/assets/images/icons/php.png" },
  // { name: "Go", icon: "/src/assets/images/icons/go.png" },
  // { name: "Java", icon: "/src/assets/images/icons/java.png" },
  // { name: "C#", icon: "/src/assets/images/icons/csharp.png" },
  // { name: "MongoDB", icon: "/src/assets/images/icons/mongodb.png" },
  // { name: "PostgreSQL", icon: "/src/assets/images/icons/postgresql.png" },
  { name: "MySQL", icon: "/src/assets/images/icons/mysql.png" },
  // { name: "Redis", icon: "/src/assets/images/icons/redis.png" },
  // { name: "Firebase", icon: "/src/assets/images/icons/firebase.png" },
  // { name: "GraphQL", icon: "/src/assets/images/icons/graphql.png" },
  // { name: "REST API", icon: "/src/assets/images/icons/restapi.png" },
  // { name: "Docker", icon: "/src/assets/images/icons/docker.png" },
]);

    // 필터링된 과정 배열
    const filteredCourses = computed(() => {
      if (!searchQuery.value) {
        return courses.value; // 검색어가 없으면 전체 과정 반환
      }
      return courses.value.filter(course =>
        course.label.toLowerCase().includes(searchQuery.value.toLowerCase())
      );
    });

    const handleFilterCourses = (query) => {
      loading.value = true;
      searchQuery.value = query; // 검색어를 업데이트
      loading.value = false; // 로딩 상태 종료
    };

    const handleComplete = () => {
      console.log("선택된 과정:", selectedCourse.value);
      console.log("선택된 항목:", selectedItems.value);
      router.push("/Letter/SelectCompany");
    };

    return {
      selectedCourse,
      filteredCourses,
      showFrontendPills,
      showBackendPills,
      selectedItems,
      frontendLinks,
      backendLinks,
      handleComplete,
      handleFilterCourses,
      loading,
    };
  },
};
</script>

<style scoped>
.title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 15px;
}

.course-selection {
  margin-bottom: 20px;
}
/* 체크박스 컨테이너 */
.checkbox-container {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

/* 체크박스 숨기기 */
.pill-checkbox {
  display: none; /* 기본 체크박스를 숨김 */
}

/* 커스텀 체크박스 디자인 */
.pill-label {
  position: relative;
  font-size: 18px;
  cursor: pointer;
  color: #3E66DF;
  font-weight: bold;
  padding-left: 30px; /* 체크박스 크기만큼 왼쪽 여백 */
  transition: color 0.3s ease;
}

.pill-label:before {
  content: '';
  position: absolute;
  margin-top: 4px;
  left: 0;
  width: 18px;
  height: 18px;
  border: 1px solid #aeaeaf;
  border-radius: 4px;
  background-color: white;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.pill-checkbox:checked + .pill-label:before {
  background-color: #3E66DF;
  border-color: #3E66DF;
}

.pill-checkbox:checked + .pill-label:after {
  content: '';
  position: absolute;
  bottom: 0px;
  left: 2px; 
  color: white;
  font-size: 16px;
  transition: color 0.3s ease;
}

.pill-label:hover {
  color: #0056b3; /* Hover 시 텍스트 색상 변화 */
}


.nav-pills {
  margin-top: 10px;
  list-style: none;
  padding: 0;
}

.nav-item {
  margin-right: 10px;
  margin-bottom: 10px; /* 줄바꿈을 위해 여백 추가 */
}

.nav-checkbox {
  display: none;
}

.nav-label {
  padding: 8px 16px;
  border: 1px solid #DCDFE6;
  border-radius: 4px;
  color: black;
  cursor: pointer;
  background-color: #fff;
  transition: background-color 0.3s, color 0.3s;
}

.nav-checkbox:checked + .nav-label {
  background-color: #3E66DF;
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
  transition: background-color 0.3s;
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
.pill-icon {
  width: 25px; /* 아이콘 크기 */
  height: 25px; /* 아이콘 크기 */
  margin-right: 3px; /* 이름과 아이콘 사이 간격 */
  object-fit: contain; /* 비율을 유지하며 크기를 조절 */
}

</style>
