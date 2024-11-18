<template>
  <LetterHeader />
  <div class="container d-flex flex-column align-items-center mb-5">
    <div class="text-center">
      <h3>100대기업 자기소개서</h3>
      <h3>기존 항목 자동으로 불러오기</h3>
    </div>

    <div class="box-container">
      <div class="row">
        <div class="col-12">
          <div
            v-for="(company, index) in companies"
            :key="company.cpno" 
            class="company-item d-flex align-items-center"
          >
            <img :src="company.image" alt="기업 사진" class="company-img" />
            <div class="company-info flex-grow-1">
              <h5 class="mb-1">{{ company.name }}</h5>
              <p class="mb-0">{{ company.title }}</p> 
            </div>
            <button
              class="btn btn-secondary me-1"
              @click="navigateToViewInfo(company.notice)"
            >
              공고보기
            </button>
            <button
              class="btn btn-primary"
              @click="navigateToPersonalStatement(company.cpno)"
            >
              작성
            </button>
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-primary mt-3" @click="navigateToManualInput">
      수동으로 입력하기
    </button>
  </div>
</template>

<script setup>
import LetterHeader from "@/components/letter/LetterHeader.vue";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";

const router = useRouter();

const companies = ref([]); // 기업 목록을 저장할 ref
const route = useRoute();
const isManual = ref(false);

const fetchCompanies = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/company/notice`, {
      params: { cno: route.query.cno } // cno를 쿼리 파라미터로 추가
    });    
    companies.value = response.data.map(company => ({
      cpno: company.cpno,
      image: company.logo ? company.logo : 'default-image.jpg',
      name: company.name,
      title: company.title,
      notice: company.notice // notice 필드를 추가
    }));
    console.log(companies.value); // 응답 확인
  } catch (error) {
    console.error("기업 목록을 가져오는 데 실패했습니다:", error);
  }
};

// 새 탭에서 공고보기
function navigateToViewInfo(noticeUrl) {
  console.log("공고 URL:", noticeUrl); // URL 확인
  if (noticeUrl) {
    window.open(noticeUrl, '_blank'); // 새 탭에서 열기
  } else {
    alert("공고 링크가 없습니다.");
  }
}

//자동
function navigateToPersonalStatement(companyId, isManual = false) {
  localStorage.setItem('manual', JSON.stringify(isManual));
  router.push({ name: "Write", params: { id: companyId }, state: { manual: isManual } });
}


// 수동 입력 페이지로 이동
function navigateToManualInput() {
  localStorage.setItem('manual', 'true');
  router.push({ name: "Write", state: { manual: true } });
}

// 컴포넌트가 마운트될 때 데이터 가져오기
onMounted(() => {
  fetchCompanies();
});
</script>

<style scoped>
.container {
  max-width: 800px;
  padding: 20px;
}

.box-container {
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  background-color: white;
  margin: 20px auto;
}

.company-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
}

.company-item:not(:last-child) {
  border-bottom: 1px solid #ddd;
}

.company-img {
  width: 80px;
  height: 80px;
  object-fit: contain; /* 변경된 부분: contain으로 설정 */
  margin-right: 15px;
  border-radius: 8px;
}
</style>
