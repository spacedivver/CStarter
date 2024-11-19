<script setup>
import MypageHeader from "@/components/mypage/MypageHeader.vue";
import { ref, onMounted } from "vue";
import CStestList from "@/components/mypage/CStestList.vue";
import InterviewList from "@/components/mypage/InterviewList.vue";
const props = defineProps({ id: String });

const currentView = ref("CStest"); // 초기값: CS테스트 리스트
const changeView = (view) => {
  currentView.value = view;
};

const member = ref({
  name: "김길동",
  email: "kkdong@example.com",
  phone: "010-7777-1234",
  message: "취업 할 수 있다! 열심히 해야지 ",
});

const memberEdit = ref({
  name: "",
  email: "",
  phone: "",
  message: "",
});

onMounted(() => {
  const storedId = localStorage.getItem("id");
  if (storedId) {
    member.value.name = storedId;
    console.log(storedId);
  }
})
</script>

<template>
  <MypageHeader />
  <div class="container py-4">
    <div class="profile-section mb-4">
      <section class="card p-4 shadow-sm">
        <div class="d-flex align-items-center">
          <div class="profile-image me-4">
            <img
              class="rounded-circle"
              src="@/assets/images/profile.jpg"
              width="120"
              height="120"
              alt="Profile Image"
            />
          </div>
          <div class="profile-details">
            <h3 class="h4 mb-2 text-dark fw-bold">{{ member.name }}</h3>
            <p class="text-muted mb-1">
              <i class="fa-regular fa-envelope me-2"></i>{{ member.email }}
            </p>
            <p class="text-muted mb-1">
              <i class="fa-solid fa-phone me-2"></i>{{ member.phone }}
            </p>
            <p class="text-muted mb-1">
              <i class="fa-solid fa-comment me-2"></i>{{ member.message }}
            </p>
          </div>
          <a class="btn btn-outline-primary ms-auto" href="#edit-section">
            <i class="fa-solid fa-user-pen me-2"></i>수정하기
          </a>
        </div>
      </section>
    </div>
    <div class="row d-flex align-items-center ps-4">
      <div class="d-flex align-items-center pb-4">
        <h4 class="mb-0 me-4 fw-bold"><i class="fa-solid fa-copy me-2"></i>푼 문제</h4>
        <ul class="nav nav-tabs mb-0">
          <li class="nav-item">
            <button
              class="nav-link"
              :class="{ active: currentView === 'CStest' }"
              @click="changeView('CStest')"
            >
              CS테스트
            </button>
          </li>
          <li class="nav-item">
            <button
              class="nav-link"
              :class="{ active: currentView === 'Interview' }"
              @click="changeView('Interview')"
            >
              모의면접
            </button>
          </li>
        </ul>
      </div>
    </div>
    <div>
      <component :is="currentView === 'CStest' ? CStestList : InterviewList" />
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
}

.profile-section {
  background-color: #f9f9f9;
  border-radius: 10px;
  padding: 20px;
}

.profile-image img {
  border: 3px solid #e9ecef;
}

.profile-details {
  color: #495057;
}

.nav-tabs .nav-link {
  font-weight: bold;
  border: none;
  color: #6c757d;
  transition: all 0.3s ease-in-out;
}

.nav-tabs .nav-link.active {
  color: #007bff;
  border-bottom: 2px solid #007bff;
}

.card {
  border: none;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.card h3 {
  color: #343a40;
}

.text-muted {
  color: #6c757d !important;
}

.btn-outline-primary {
  color: #007bff;
  border-color: #007bff;
}

.btn-outline-primary:hover {
  background-color: #007bff;
  color: #fff;
}

ul.nav-tabs {
  border-bottom: none;
}

.nav-tabs .nav-item {
  margin-right: 20px;
}

.nav-tabs .nav-link {
  border-radius: 0;
}
</style>
