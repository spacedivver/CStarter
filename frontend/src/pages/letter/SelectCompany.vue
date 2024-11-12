<template>
  <div class="container d-flex flex-column align-items-center">
    <div class="text-center">
      <h3>100대기업 자기소개서</h3>
      <h3>기존 항목 자동으로 불러오기</h3>
    </div>

    <div class="box-container">
      <div class="row">
        <div class="col-12">
          <div
            v-for="(company, index) in companies"
            :key="company.id"
            class="company-item d-flex align-items-center"
          >
            <img :src="company.image" alt="기업 사진" class="company-img" />
            <div class="company-info flex-grow-1">
              <h5 class="mb-1">{{ company.name }}</h5>
              <p class="mb-0">{{ company.recruitment }}</p>
            </div>
            <button
              class="btn btn-secondary me-1"
              @click="openModal(company)"
            >
              공고보기
            </button>
            <button
              class="btn btn-primary"
              @click="navigateToPersonalStatement(company.id)"
            >
              작성
            </button>
          </div>
        </div>
      </div>
    </div>
    <router-link to="/Write" class="btn btn-primary mt-3">
      수동으로 입력하기
    </router-link>

   
    <!-- Custom Modal -->
    <div v-if="isModalOpen" class="custom-modal-overlay" @click.self="closeModal">
      <div class="custom-modal">
        <div class="custom-modal-header">
          <h5>{{ selectedCompany.name }}</h5>
          <button @click="closeModal" class="close-button">&times;</button>
        </div>
        <div class="custom-modal-body">
          <p>{{ selectedCompany.description }}</p>
        </div>
        <div class="custom-modal-footer">
          <button class="btn btn-secondary" @click="closeModal">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const companies = ref([
  {
    id: 1,
    image: "company1.jpg",
    name: "기업 이름 1",
    recruitment: "2019년 하반기 신입사원 모집",
    description: "모집모집"
  },
  {
    id: 2,
    image: "company2.jpg",
    name: "기업 이름 2",
    recruitment: "2020년 하반기 신입사원 모집",
    description: "모집모집2"
  },
  {
    id: 3,
    image: "company3.jpg",
    name: "기업 이름 3",
    recruitment: "2021년 하반기 신입사원 모집",
    description: "모집모집3"
  },
  {
    id: 4,
    image: "company4.jpg",
    name: "기업 이름 4",
    recruitment: "2022년 하반기 신입사원 모집",
    description: "모집모집4"
  },
  {
    id: 5,
    image: "company5.jpg",
    name: "기업 이름 5",
    recruitment: "2023년 하반기 신입사원 모집",
    description: "모집모집5"
  },
  {
    id: 6,
    image: "company6.jpg",
    name: "기업 이름 6",
    recruitment: "2024년 상반기 신입사원 모집",
    description: "모집모집6"
  },
]);
const selectedCompany = ref({ name: "", description: "" });
const isModalOpen = ref(false);

function navigateToPersonalStatement(companyId) {
  router.push({ name: "Write", params: { id: companyId } });
}

function openModal(company) {
  selectedCompany.value = company;
  isModalOpen.value = true;
}

function closeModal() {
  isModalOpen.value = false;
}
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
  object-fit: cover;
  margin-right: 15px;
  border-radius: 8px;
}

.custom-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.custom-modal {
  background: white;
  width: 90%;
  max-width: 500px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  overflow: hidden;
}

.custom-modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #ddd;
  background-color: #f8f9fa;
}

.custom-modal-body {
  padding: 20px;
  color: #333;
}

.custom-modal-footer {
  display: flex;
  justify-content: flex-end;
  padding: 15px;
  border-top: 1px solid #ddd;
  background-color: #f8f9fa;
}

.close-button {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}
</style>