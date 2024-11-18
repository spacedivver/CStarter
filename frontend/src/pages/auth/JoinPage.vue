<template>
  <div class="row">
    <div
      class="col-md-6 d-lg-flex flex-lg-column back back-img align-items-center justify-content-center"
    >
      <img
        src="@/assets/images/header.png"
        alt="Welcome Image"
        style="width: 500px; margin-left: 200px; margin-top: -70px;"
        class="img-fluid"
      />
      <!-- You can uncomment the below code to add a text block inside the left image container -->
      <!--
        <div class="text-center text-white">
          <h1 class="fw-bolder display-6 mb-5">
            환영합니다! CStarter 입니다.
          </h1>
          <p class="text-opacity-75">
            회원가입을 완료하시면 기술면접을 연습하고,<br />
            모의면접 리포트를 저장할 수 있어요.
          </p>
        </div>
        -->
    </div>
    <div class="col-4  d-flex justify-content-center align-items-center">
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
              <p v-if="isDuplicate" class="text-danger small">
                해당 아이디는 이미 사용 중입니다.
              </p>
              <p v-if="isAvailable" class="text-success small">
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

            <!-- <div class="col-sm-12">
              <label for="email" class="form-label">사용자 이메일</label>
              <input
                type="email"
                v-model="form.email"
                class="form-control"
                id="email"
                required
              />
            </div> -->
            <div class="d-flex align-items-center justify-content-center mt-5 mb-4">

              <div class="col-sm-6 ">
                <button
                type="submit"
                class="btn btn-dark w-100 mb-5"
                :disabled="!isFormValid || isDuplicate"
                >
                회원가입
              </button>
            </div>
            </div>
          </div>
        </form>

        <!-- <div class="row g-2">
          <div class="col-sm-6">
            <a href="#" class="btn btn-neutral w-100"
              ><span class="icon icon-sm pe-2"
                ><img src="../../img/social/github.svg" alt="..." /> </span
              >Github</a
            >
          </div>
          <div class="col-sm-6">
            <a href="#" class="btn btn-neutral w-100"
              ><span class="icon icon-sm pe-2"
                ><img src="../../img/social/google.svg" alt="..." /> </span
              >Google</a
            >
          </div>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const form = ref({
  id: "",
  password: "",
  confirmPassword: "",
  name: "",
  email: "",
});

const isDuplicate = ref(false);
const isAvailable = ref(false);
const router = useRouter();

const isFormValid = computed(() => {
  return (
    form.value.id &&
    form.value.password &&
    form.value.confirmPassword &&
    form.value.name &&
    form.value.email &&
    form.value.password === form.value.confirmPassword &&
    !isDuplicate.value // 중복된 경우 제출 비활성화
  );
});

const checkDuplicate = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/member/checkid/${form.value.id}`
    );
    isDuplicate.value = response.data; // 중복 여부 설정
    isAvailable.value = !isDuplicate.value;
  } catch (error) {
    console.error("중복 확인 실패:", error);
  }
};

const submitForm = async () => {
  let formData = new FormData();

  formData.append("id", form.value.id);
  formData.append("password", form.value.password);
  formData.append("name", form.value.name);
  formData.append("email", form.value.email);

  try {
    const response = await axios.post(
      "http://localhost:8080/api/member",
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
    console.log("회원가입 성공:", response.data);
    router.push("/welcome");
  } catch (error) {
    console.error("회원가입 실패:", error);
  }
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

.p-150 {
  padding: 150px;
}

.mt-7 {
  margin-top: 7rem;
}

.ls-tight {
  letter-spacing: -0.02em;
}

.fw-bolder {
  font-weight: bolder;
}

.h3 {
  font-size: 2rem;
}

.text-white {
  color: white;
}

.text-opacity-75 {
  opacity: 0.75;
}

.shadow-soft-5 {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.bg-white {
  background-color: white;
}

.rounded-right {
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
}

.w-md-50 {
  width: 50%;
}

.mx-auto {
  margin-left: auto;
  margin-right: auto;
}

.px-10 {
  padding-left: 10px;
  padding-right: 10px;
}

.py-10 {
  padding-top: 10px;
  padding-bottom: 10px;
}

.mb-5 {
  margin-bottom: 5rem;
}
</style>
