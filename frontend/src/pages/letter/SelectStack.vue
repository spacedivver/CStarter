<template>
  <CStestHeader />
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
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import CStestHeader from "/src/pages/cstest/CStestHeader.vue";

export default {
  components: {
    CStestHeader
  },
  setup() {
    const router = useRouter();
    const selectedCourse = ref(null);
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
      { name: "Angular", icon: "/src/assets/images/icons/angular.png" },
      { name: "Svelte", icon: "/src/assets/images/icons/svelte.png" },
      { name: "Ember.js", icon: "/src/assets/images/icons/ember.png" },
      { name: "Bootstrap", icon: "/src/assets/images/icons/bootstrap.png" },
      { name: "Tailwind CSS", icon: "/src/assets/images/icons/tailwind.png" },
      { name: "jQuery", icon: "/src/assets/images/icons/jquery.png" },
      { name: "TypeScript", icon: "/src/assets/images/icons/typescript.png" },
      { name: "Redux", icon: "/src/assets/images/icons/redux.png" },
      { name: "Webpack", icon: "/src/assets/images/icons/webpack.png" },
      { name: "Gulp", icon: "/src/assets/images/icons/gulp.png" },
    ]);

    const backendLinks = ref([
      { name: "Node.js", icon: "/src/assets/images/icons/nodejs.png" },
      { name: "Express", icon: "/src/assets/images/icons/expressjs.png" },
      { name: "Django", icon: "/src/assets/images/icons/django.png" },      
      { name: "Spring", icon: "/src/assets/images/icons/spring.png" },
      { name: "Spring Boot", icon: "/src/assets/images/icons/springboot.png" },
      { name: "MySQL", icon: "/src/assets/images/icons/mysql.png" },
      { name: "Ruby on Rails", icon: "/src/assets/images/icons/rails.png" },
      { name: "Flask", icon: "/src/assets/images/icons/flask.png" },
      { name: "Laravel", icon: "/src/assets/images/icons/laravel.png" },
      { name: "ASP.NET", icon: "/src/assets/images/icons/aspnet.png" },
      { name: "PHP", icon: "/src/assets/images/icons/php.png" },
      { name: "PostgreSQL", icon: "/src/assets/images/icons/postgresql.png" },
      { name: "MongoDB", icon: "/src/assets/images/icons/mongodb.png" },
      { name: "GraphQL", icon: "/src/assets/images/icons/graphql.png" },
      { name: "Redis", icon: "/src/assets/images/icons/redis.png" },
      { name: "Firebase", icon: "/src/assets/images/icons/firebase.png" },
    ]);

    const aiLinks = ref([
      { name: "TensorFlow", icon: "/src/assets/images/icons/tensorflow.png" },
      { name: "PyTorch", icon: "/src/assets/images/icons/pytorch.png" },
      { name: "Keras", icon: "/src/assets/images/icons/keras.png" },
      { name: "Scikit-learn", icon: "/src/assets/images/icons/scikit-learn.png" },
      { name: "Pandas", icon: "/src/assets/images/icons/pandas.png" },
      { name: "OpenCV", icon: "/src/assets/images/icons/opencv.png" },
      { name: "NLTK", icon: "/src/assets/images/icons/nltk.webp" },
      { name: "spaCy", icon: "/src/assets/images/icons/spacy.png" },
      { name: "MXNet", icon: "/src/assets/images/icons/mxnet.png" },
      { name: "Hugging Face Transformers", icon: "/src/assets/images/icons/huggingface.png" },
      { name: "ONNX", icon: "/src/assets/images/icons/onnx.png" },
      { name: "Caffe", icon: "/src/assets/images/icons/caffe.webp" },
      { name: "Theano", icon: "/src/assets/images/icons/theano.png" },
      { name: "Matplotlib", icon: "/src/assets/images/icons/matplotlib.png" },
      { name: "XGBoost", icon: "/src/assets/images/icons/xgboost.png" },
    ]);

    const infraLinks = ref([
      { name: "AWS", icon: "/src/assets/images/icons/aws.png" },
      { name: "Docker", icon: "/src/assets/images/icons/docker.png" },
      { name: "Kubernetes", icon: "/src/assets/images/icons/kubernetes.png" },
      { name: "Terraform", icon: "/src/assets/images/icons/terraform.png" },
      { name: "Ansible", icon: "/src/assets/images/icons/ansible.png" },
      { name: "Azure", icon: "/src/assets/images/icons/azure.png" },
      { name: "Google Cloud", icon: "/src/assets/images/icons/gcp.png" },
      { name: "Jenkins", icon: "/src/assets/images/icons/jenkins.png" },
      { name: "GitLab", icon: "/src/assets/images/icons/gitlab.svg" },
      { name: "Prometheus", icon: "/src/assets/images/icons/prometheus.png" },
      { name: "Grafana", icon: "/src/assets/images/icons/grafana.png" },
      { name: "ELK Stack", icon: "/src/assets/images/icons/elk.svg" },
      { name: "Nagios", icon: "/src/assets/images/icons/nagios.png" },
      { name: "Chef", icon: "/src/assets/images/icons/chef.png" },
      { name: "Puppet", icon: "/src/assets/images/icons/puppet.png" },
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
      selectedItems,
      frontendLinks,
      backendLinks,
      aiLinks,
      infraLinks,
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
