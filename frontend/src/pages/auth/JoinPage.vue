<template>
  <div class="row" style="height: 720px">
    <div
      class="col-md-6 d-lg-flex flex-lg-column back back-img align-items-center justify-content-center"
    >
      <img
        src="@/assets/images/header.png"
        alt="Welcome Image"
        style="width: 500px; margin-left: 200px; margin-top: -70px"
        class="img-fluid"
      />
    </div>
    <div class="col-4 d-flex justify-content-center align-items-center">
      <div class="container ms-5">
        <div class="mb-3">
          <h1 class="fw-bolder h3 mt-5">회원가입</h1>
          <div class="mt-3 text-sm text-muted">
            <span>이미 회원이라면</span>
            <a href="/auth/login" class="fw-semibold"> 로그인 </a>페이지로 이동
          </div>
        </div>
        <form @submit.prevent="submitForm">
          <div class="row g-4">
            <div class="col-sm-12">
              <label for="name" class="form-label">이름</label>
              <input
                type="text"
                v-model="form.name"
                class="form-control"
                id="name"
                required
              />
            </div>
            <div class="col-sm-12">
              <label for="id" class="form-label">사용자 아이디</label>
              <div class="input-group">
                <input
                  type="text"
                  v-model="form.id"
                  class="form-control"
                  id="id"
                  required
                />
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  @click="checkDuplicate"
                >
                  중복 확인
                </button>
              </div>
              <p v-if="duplicateChecked" class="text-success small">
                사용 가능한 아이디입니다.
              </p>
            </div>
            <div class="col-sm-12">
              <label for="password" class="form-label">사용자 비밀번호</label>
              <input
                type="password"
                v-model="form.password"
                id="password"
                class="form-control"
                required
              />
            </div>
            <div class="col-sm-12">
              <label for="confirmPassword" class="form-label"
                >비밀번호 확인</label
              >
              <input
                type="password"
                v-model="form.confirmPassword"
                id="confirmPassword"
                class="form-control"
                required
              />
              <p
                v-if="
                  form.password !== form.confirmPassword &&
                  form.confirmPassword.length > 0
                "
                class="text-danger small"
              >
                비밀번호가 일치하지 않습니다.
              </p>
            </div>
            <div
              class="d-flex align-items-center justify-content-center mt-5 mb-4"
            >
              <div class="col-sm-6">
                <button
                  type="submit"
                  class="btn btn-dark w-100 mb-5"
                  :disabled="!isFormValid"
                  @click="navigateToLogin"
                >
                  회원가입
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const form = ref({
  id: "",
  password: "",
  confirmPassword: "",
  name: "",
});

const duplicateChecked = ref(false);

const isFormValid = computed(() => {
  return (
    form.value.id &&
    form.value.password &&
    form.value.confirmPassword &&
    form.value.name &&
    form.value.password === form.value.confirmPassword &&
    duplicateChecked.value
  );
});

const checkDuplicate = () => {
  duplicateChecked.value = true;
};

const navigateToLogin = () => {
  router.push("/auth/login");
};
</script>

<style scoped>
.back {
  background-color: #d9e8f6;
}

.back-img {
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.mt-7 {
  margin-top: 7rem;
}

.fw-bolder {
  font-weight: bolder;
}

.text-success {
  color: green;
}

.mb-5 {
  margin-bottom: 5rem;
}
</style>
