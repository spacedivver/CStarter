<template>
  <div class="container">
    <h3 class="text-center">자기소개서 작성</h3>
    <div class="form-group mb-3 ">
      <input
        type="text"
        class="form-control"
        v-model="companyName"
        :placeholder="isManual ? '기업명을 입력하세요' : '자동으로 기업명이 입력됩니다'"
        :readonly="!isManual"
      />
    </div>

    <el-select v-model="selectedJob" placeholder="직무를 선택하세요" class="mb-3">
      <el-option label="백엔드" value="백엔드"></el-option>
      <el-option label="프론트엔드" value="프론트엔드"></el-option>
      <el-option label="인프라" value="인프라"></el-option>
      <el-option label="DBA" value="DBA"></el-option>
      <el-option label="기획" value="기획"></el-option>
    </el-select>


    <div v-if="isManual">
      <!-- 수동 입력일 때 제목과 내용 동적으로 추가 -->
      <div v-for="(item, index) in inputItems" :key="index" class="input-item mb-3">
        <div class="form-group">
          <input type="text" class="form-control" v-model="item.title" :id="'title-' + index" placeholder="자기소개서 문항을 입력하세요">
        </div>
        <!-- 제목과 내용 사이에 구분선 추가 -->
        <div class="divider"></div>
        <div class="form-group">
          <textarea class="form-control" v-model="item.content" :id="'content-' + index" placeholder="내용을 입력하세요"></textarea>
        </div>
      </div>
      <button class="btn btn-secondary mt-3" @click="addInputItem">항목 추가하기</button>
    </div>

    <div v-else>
      <!-- 자동 입력일 때 제목은 자동으로, 내용은 사용자가 입력 -->
      <div v-for="(item, index) in autoInputItems" :key="index" class="input-item mb-3">
        <div class="form-group">
          <input type="text" class="form-control" v-model="item.title" :id="'auto-title-' + index" placeholder="자동으로 제목이 입력됩니다" readonly>
        </div>
        <!-- 제목과 내용 사이에 구분선 추가 -->
        <div class="divider"></div>
        <div class="form-group">
          <textarea class="form-control" v-model="item.content" :id="'auto-content-' + index" placeholder="내용을 입력하세요"></textarea>
        </div>
      </div>
    </div>

    <!-- '다음 단계' 버튼 추가 -->
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
import { ElSelect, ElOption } from 'element-plus';
import 'element-plus/dist/index.css';

const router = useRouter();
const route = useRoute();
const selectedJob = ref('프론트엔드'); // 기본값 설정

// 기본값이 없을 경우 false로 설정
const isManual = ref(route.state?.manual ?? JSON.parse(localStorage.getItem('manual') || 'false'));
const companyName = ref(isManual.value ? '' : '자동기업명');

// 상태가 바뀔 때마다 `localStorage`에 저장하여 새로고침해도 유지
onMounted(() => {
  localStorage.setItem('manual', JSON.stringify(isManual.value));
});

// 수동 입력 항목들 관리
const inputItems = ref([{ title: '', content: '' }]);

// 자동 입력 항목 5개 (제목만 자동으로, 내용은 사용자 입력)
const autoInputItems = ref([
  { title: '자동 제목 1', content: '' },
  { title: '자동 제목 2', content: '' },
  { title: '자동 제목 3', content: '' },
  { title: '자동 제목 4', content: '' },
  { title: '자동 제목 5', content: '' }
]);

// 항목 추가하기
const addInputItem = () => {
  inputItems.value.push({ title: '', content: '' });
};
</script>

<style scoped>


.form-group {
  margin-bottom: 15px;
}

.input-item {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.input-item:not(:last-child) {
  margin-bottom: 15px;
}

.input-item .form-group {
  margin-bottom: 10px;
}

.input-item .form-group input,
.input-item .form-group textarea {
  border-radius: 8px;
  border: none; /* 선 제거 */
  background-color: white; /* 배경을 흰색으로 설정 */
  box-shadow: none; /* 그림자 제거 */
  outline: none; /* 포커스 시 테두리 제거 */
  padding: 10px; /* 내부 여백 추가 */
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
