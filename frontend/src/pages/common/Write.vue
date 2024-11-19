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
        <button class="btn btn-primary" @click="submitCoverLetter">저장</button>
      </router-link>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import LetterHeader from '@/components/letter/LetterHeader.vue';
import axios from 'axios';
import { useCoverLetterStore } from '@/stores/coverLetterStore';

const router = useRouter();
const route = useRoute();
const isManual = ref(route.state?.manual ?? JSON.parse(localStorage.getItem('manual') || 'false'));
const selectedJob = ref(null);
const customJob = ref('');
const inputItems = ref([{ title: '', content: '' }]);
const autoInputItems = ref([]);
const jobTypes = ref([]);
const companyName = ref('');
const companyId = route.params.id;
const companyNameState = ref(route.state?.cname ?? JSON.parse(localStorage.getItem('cname') || ''));
const selectedJobName = ref('');


localStorage.setItem('manual', JSON.stringify(isManual.value));
localStorage.setItem('cname', JSON.stringify(companyNameState.value));
onMounted(async () => {
    if (!isManual.value) {
        try {
            const response = await axios.get(`http://localhost:8080/api/company/${companyId}/job`);
            const jobDataArray = response.data;
            companyName.value = isManual.value ? '' : companyNameState.value;
            jobTypes.value = jobDataArray;

            if (jobDataArray.length > 0) {
                selectedJob.value = jobDataArray[0].jno;
                onJobChange();
            }
        } catch (error) {
            console.error("직무 데이터를 가져오는 데 실패했습니다:", error);
        }
    }
});

const onJobChange = () => {
    if (!selectedJob.value) return;

    const selectedJobData = jobTypes.value.find(job => job.jno === selectedJob.value);
    // 선택된 직무의 이름을 저장
    if (selectedJobData) {
            selectedJobName.value = selectedJobData.type; // 직무 이름 저장
        }

    if (selectedJobData && selectedJobData.coverLetterItems) {
        autoInputItems.value = selectedJobData.coverLetterItems.map(item => ({
          cino: item.cino,
            title: item.content,
            content: ''
        }));
    } else {
        autoInputItems.value = [];
    }
};

const addInputItem = () => {
    inputItems.value.push({ title: '', content: '' });
};

const resizeTextarea = (event) => {
    const textarea = event.target;
    textarea.style.height = 'auto';
    textarea.style.height = `${textarea.scrollHeight}px`;
};
// Cover letter 전송 함수
const submitCoverLetter = async () => {
    const answers = []; // 빈 배열 선언

    autoInputItems.value.forEach((item, index) => {        

        answers.push({
            answer: item.content,
            cino: item.cino // 자기소개서 제목의 번호
        });
    });

    const cpno = companyId; // 회사 ID
    const jno = selectedJob.value; // 선택된 직무 번호
    const mno = 1; // 고정값

    const data = {
        answers,
        cpno,
        jno,
        mno
    };

    try {
        const response = await axios.post('http://localhost:8080/api/cover-letter', data);

        // Pinia 스토어에 데이터 저장
        const coverLetterStore = useCoverLetterStore();
        coverLetterStore.setCoverLetterData({
            clno: response.data.clno,
            companyName: companyName.value,
            job: selectedJobName.value
        });

        // 다음 단계로 이동
        router.push('/Interview/Setting');
    } catch (error) {
        console.error('자기소개서 제출에 실패했습니다:', error);
    }
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
