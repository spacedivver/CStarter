<template>
  <LetterHeader />
  <div class="container mb-5">
    <div class="form-group mb-3">
      <h5 class="mb-3">기업명</h5>
      <input
        type="text"
        class="form-control"
        v-model="companyName"
        :placeholder="isManual ? '기업명을 입력하세요' : '자동으로 기업명이 입력됩니다'"
        :readonly="!isManual"
      />
    </div>

    <div>
      <h5 class="mb-3">직무선택</h5>
      <el-select v-model="selectedJob" placeholder="직무를 선택하세요" class="mb-3" @change="onJobChange">
        <template v-if="isManual"> <!-- isManual이 true일 경우 -->
          <el-option label="백엔드 개발" value="백엔드 개발"></el-option>
          <el-option label="프론트엔드 개발" value="프론트엔드 개발"></el-option>
          <el-option label="풀스택 개발" value="풀스택 개발"></el-option>
          <el-option label="정보보안" value="정보보안"></el-option>
          <el-option label="DBA" value="DBA"></el-option>
          <el-option label="Infra" value="Infra"></el-option>
          <el-option label="IT기획" value="IT기획"></el-option>
          <el-option label="IT구매" value="IT구매"></el-option>
          <el-option label="직접 입력" value="직접 입력"></el-option>
        </template>
        
        <template v-else> <!-- isManual이 false일 경우 -->
          <el-option
            v-for="job in jobTypes"
            :key="job.jno"
            :label="job.type"
            :value="job.jno">
          </el-option>
        </template>
      </el-select>
      <input v-if="selectedJob === '직접 입력'" type="text" class="form-control mb-3" v-model="customJob" placeholder="직무를 입력하세요">
    </div>

    <!-- 수동 -->
    <h5 class="mb-3">자기소개서 입력</h5>
    <div v-if="isManual">
      <div v-for="(item, index) in inputItems" :key="index" class="input-item mb-3">
        <div class="form-group">
          <input type="text" class="form-control" v-model="item.title" :id="'title-' + index" placeholder="자기소개서 문항을 입력하세요" >
        </div>
        <div class="divider"></div>
        <div class="form-group">
          <textarea
            class="form-control"
            v-model="item.content"
            :id="'content-' + index"
            placeholder="내용을 입력하세요"
            @input="resizeTextarea($event)">
          </textarea>
        </div>
      </div>
      <button class="btn btn-secondary mt-3" @click="addInputItem">항목 추가하기</button>
    </div>

    <!-- 자동 -->
    <div v-else>
      <div v-for="(item, index) in autoInputItems" :key="index" class="input-item mb-3">
        <div class="form-group">
          <div v-html="item.title" :id="'auto-title-' + index" class="ps-3 pe-4 content"></div>
        </div>
        <div class="divider"></div>
        <div class="form-group">
          <textarea
            class="form-control"
            v-model="item.content"
            :id="'auto-content-' + index"
            placeholder="내용을 입력하세요"
            @input="resizeTextarea($event)">
          </textarea>
        </div>
      </div>
    </div>

    <div class="text-center mt-4">
      <router-link to="/Interview/Setting">
        <button class="btn btn-primary">다음 단계</button>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import LetterHeader from '@/components/letter/LetterHeader.vue';
import axios from 'axios';

const router = useRouter();
const route = useRoute();
const isManual = ref(route.state?.manual ?? JSON.parse(localStorage.getItem('manual') || 'false'));
const selectedJob = ref(null); // 초기값을 null로 설정
const customJob = ref('');
const inputItems = ref([{ title: '', content: '' }]);
const autoInputItems = ref([]); // 초기값을 빈 배열로 설정
const jobTypes = ref([]); // 직무 타입을 저장할 배열
const companyName = ref(''); // 기업명 상태 변수 추가

const companyId = route.params.id; // URL에서 companyId 가져오기
const companyNameState = ref(route.state?.cname ?? JSON.parse(localStorage.getItem('cname') || ''));
localStorage.setItem('manual', JSON.stringify(isManual.value));
localStorage.setItem('cname', JSON.stringify(companyNameState.value));

onMounted(async () => {
      if (!isManual.value) {

        // API 요청하여 직무 타입 및 자기소개서 제목 가져오기
        
        try {
          const response = await axios.get(`http://localhost:8080/api/company/${companyId}/job`);
          const jobDataArray = response.data; // 배열 형태로 응답 받음

          companyName.value = isManual.value ? '' : companyNameState.value; // 기업명 설정

          // 직무 타입을 jobTypes에 저장
          jobTypes.value = jobDataArray;

          // 첫 번째 직무를 자동으로 선택
          if (jobDataArray.length > 0) {
            selectedJob.value = jobDataArray[0].jno; // 0번째 직무 선택
            onJobChange(); // 직무 변경 함수 호출
          }
        } catch (error) {
          console.error("직무 데이터를 가져오는 데 실패했습니다:", error);
        }
    }

});

// 직무 변경 시 호출되는 함수
const onJobChange = () => {
  if (!selectedJob.value) return; // 선택된 직무가 없으면 종료

  // 선택된 직무에 해당하는 데이터 찾기
  const selectedJobData = jobTypes.value.find(job => job.jno === selectedJob.value);

  if (selectedJobData && selectedJobData.coverLetterItems) {
    // coverLetterItems의 내용을 inputItems에 자동으로 설정
    autoInputItems.value = selectedJobData.coverLetterItems.map(item => ({
      title: item.content, // 자기소개서 제목으로 content 사용
      content: '' // 내용은 빈 문자열로 초기화
    }));
    
  } else {
    autoInputItems.value = []; // 기본값 설정
  }
};

const addInputItem = () => {
  inputItems.value.push({ title: '', content: '' });
};

// 텍스트 영역 자동 높이 조정
const resizeTextarea = (event) => {
  const textarea = event.target;
  textarea.style.height = 'auto'; // 우선 높이를 auto로 설정하여 기존의 높이를 초기화
  textarea.style.height = `${textarea.scrollHeight}px`; // scrollHeight에 맞게 높이 조정
};
</script>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

.input-item {
  padding: 0px;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding-top: 10px;
}

.input-item:not(:last-child) {
  margin-bottom: 15px;
}

.input-item .form-group {
  margin-bottom: 10px;
}

.input-item .form-group input {
  border-radius: 8px;
  border: none;
  background-color: white;
  box-shadow: none;
  outline: none;
  padding: 10px;
  word-wrap: break-word;
  white-space: pre-wrap;
}

.input-item .form-group textarea {
  border-radius: 8px;
  border: none;
  background-color: white;
  box-shadow: none;
  outline: none;
  padding: 10px;  
  word-wrap: break-word;
  white-space: pre-wrap;
}

.divider {
  border-bottom: 1px solid #ddd;
  margin: 10px 0;
}

.btn {
  display: block;
  width: 100%;
  margin-top: 20px;
}

.text-center {
  text-align: center;
}

.btn-primary {
  width: 150px;
  margin: 0 auto;
}
</style>
