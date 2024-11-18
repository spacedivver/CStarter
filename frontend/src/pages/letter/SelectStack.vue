<template>
  <LetterHeader />
  <div class="container mb-5p">
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
        @change="updateSelectedTechSkills"
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

    <!-- Frontend -->
    <div class="pill-container mb-3">
      <div class="pill-category">Frontend</div>
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

    <!-- Backend -->
    <div class="pill-container">
      <div class="pill-category">Backend</div>
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

    <!-- AI -->
    <div class="pill-container">
      <div class="pill-category">AI</div>
      <ul class="nav nav-pills flex-row flex-wrap">
        <li class="nav-item" v-for="(link, index) in aiLinks" :key="index">
          <input
            type="checkbox"
            :id="'ai-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link.name"
          />
          <label :for="'ai-pill' + index" class="nav-label">
            <img :src="link.icon" alt="" class="pill-icon" />
            {{ link.name }}
          </label>
        </li>
      </ul>
    </div>

    <!-- 인프라 -->
    <div class="pill-container">
      <div class="pill-category">Infra</div>
      <ul class="nav nav-pills flex-row flex-wrap">
        <li class="nav-item" v-for="(link, index) in infraLinks" :key="index">
          <input
            type="checkbox"
            :id="'infra-pill' + index"
            class="nav-checkbox"
            v-model="selectedItems"
            :value="link.name"
          />
          <label :for="'infra-pill' + index" class="nav-label">
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
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import LetterHeader from "/src/components/letter/LetterHeader.vue";

export default {
  components: {
    LetterHeader
  },
  setup() {
    const router = useRouter();
    const selectedCourse = ref(null);
    const selectedItems = ref([]);
    const loading = ref(false);
    const searchQuery = ref("");

    // 부트캠프 과정 데이터
    const courses = ref([]);
    const techSkills = ref([]);

    // 기술 스택 카테고리 별 링크
    const frontendLinks = ref([]);
    const backendLinks = ref([]);
    const aiLinks = ref([]);
    const infraLinks = ref([]);

    // API로부터 과정 데이터를 가져오는 함수
    const fetchCourses = async () => {
      loading.value = true;
      try {
        const response = await axios.get("http://localhost:8080/api/course");
        courses.value = response.data.map(course => ({
          value: course.cno, // cno를 value로 사용
          label: course.courseName, // courseName을 label로 사용
          techSkills: course.techSkills // techSkills 추가
        }));
      } catch (error) {
        console.error("과정 데이터를 가져오는 데 실패했습니다:", error);
      } finally {
        loading.value = false;
      }
    };

    // API로부터 기술 스택 데이터를 가져오는 함수
    const fetchTechSkills = async () => {
      loading.value = true;
      try {
        const response = await axios.get("http://localhost:8080/api/tech-skill");
        techSkills.value = response.data;

        // 기술 스택을 카테고리별로 나누기
        frontendLinks.value = techSkills.value.filter(skill =>
          ["HTML", "CSS", "JavaScript", "React", "Vue.js", "Angular", "Svelte", "Ember.js", "Bootstrap", "Tailwind CSS", "jQuery", "TypeScript", "Redux", "Webpack", "Gulp"].includes(skill.name)
        );

        backendLinks.value = techSkills.value.filter(skill =>
          ["Java", "Node.js", "MyBatis", "Django", "ServletJSP", "Spring", "Spring Boot", "Oracle", "MS-SQL", "MySQL", "Ruby on Rails", "Flask", "Laravel", "ASP.NET", "PHP", "PostgreSQL", "MongoDB", "Redis"].includes(skill.name)
        );

        aiLinks.value = techSkills.value.filter(skill =>
          ["TensorFlow", "PyTorch", "Keras", "Scikit-learn", "Pandas", "OpenCV", "NLTK", "spaCy", "MXNet", "Hugging Face Transformers", "ONNX", "Caffe", "Theano", "Matplotlib", "XGBoost"].includes(skill.name)
        );

        infraLinks.value = techSkills.value.filter(skill =>
          ["AWS", "Docker", "Kubernetes", "Terraform", "Ansible", "Azure", "Google Cloud", "Jenkins", "GitLab", "Prometheus", "Grafana", "ELK Stack", "Nagios", "Chef", "Puppet"].includes(skill.name)
        );
      } catch (error) {
        console.error("기술 스택 데이터를 가져오는 데 실패했습니다:", error);
      } finally {
        loading.value = false;
      }
    };

    // 선택된 과정에 맞춰 기술 스택을 자동으로 선택
    const updateSelectedTechSkills = () => {
      const selectedCourseData = courses.value.find(course => course.value === selectedCourse.value);
      if (selectedCourseData) {
        selectedItems.value = selectedCourseData.techSkills.map(skill => skill.name); // 기술 스택의 이름만 뽑아서 selectedItems에 추가
      } else {
        selectedItems.value = []; // 과정이 없으면 초기화
      }
    };

    // 컴포넌트가 마운트될 때 데이터 가져오기
    onMounted(() => {
      fetchCourses();
      fetchTechSkills();
    });

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
      selectedItems,
      frontendLinks,
      backendLinks,
      aiLinks,
      infraLinks,
      handleComplete,
      handleFilterCourses,
      loading,
      updateSelectedTechSkills // 추가된 메서드
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

.pill-container {
  margin-bottom: 20px;
}

.pill-category {
  font-size: 18px;
  font-weight: bold;
  color: #3E66DF;
  margin-bottom: 10px;
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

.pill-icon {
  width: 25px; /* 아이콘 크기 */
  height: 25px; /* 아이콘 크기 */
  margin-right: 3px; /* 이름과 아이콘 사이 간격 */
  object-fit: contain; /* 비율을 유지하며 크기를 조절 */
}


</style>
