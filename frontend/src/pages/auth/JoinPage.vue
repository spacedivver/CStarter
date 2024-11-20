<template>
  <div
    class="mt-2 row g-0 justify-content-center gradient-bottom-right middle-indigo end-pink"
  >
    <div
      class="col-md-6 col-lg-5 col-xl-5 position-absolute start-0 vh-100 overflow-y-hidden d-none d-lg-flex flex-lg-column back-img"
    >
      <div class="p-150">
        <div class="mt-20">
          <h1 class="ls-tight fw-bolder display-6 text-white mb-5">
            환영합니다!
            <br />CStarter 입니다.
          </h1>
          <p class="text-white text-opacity-75 pe-xl-24">
            회원가입을 완료하시면 기술면접을 연습하고,
            <BR /> 모의면접 리포트를 저장할 수 있어요. <br />
          </p>
        </div>
      </div>
    </div>
    <div
      class="col-12 col-md-12 col-lg-7 offset-lg-5 vh-100 d-flex justify-content-center align-items-center border-start-lg shadow-soft-5"
    >
    <div class="w-md-50 mx-auto px-10 px-md-0 py-10 container">
        <div class="mb-3">
          <a class="d-inline-block d-lg-none mb-10" href="/pages/dashboard.html"
            ><img
              src="../../img/logos/logo-dark.svg"
              class="h-rem-10"
              alt="..."
          /></a>
          <h1 class="ls-tight fw-bolder h3 mt-7">회원가입</h1>
          <div class="mt-3 text-sm text-muted">
            <span>이미 회원이라면</span>
            <a href="/auth/login" class="fw-semibold"> 로그인 </a>페이지로 이동
          </div>
        </div>
        <form @submit.prevent="submitForm">
          <div class="row g-5">
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
              <label for="email" class="form-label">이메일</label>
              <div class="input-group">
                <input
                  type="email"
                  v-model="form.email"
                  class="form-control"
                  id="email"
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
<<<<<<< Updated upstream
              <p v-if="isDuplicate" class="text-danger small">
                해당 아이디는 이미 사용 중입니다.
              </p>
              <p v-if="isAvailable" class="text-success small">
                사용 가능한 아이디입니다.
=======
              <p v-if="duplicateChecked" class="text-success small">
                사용 가능한 이메일입니다.
>>>>>>> Stashed changes
              </p>
            </div>
            <div class="col-sm-12">
              <label for="password" class="form-label">비밀번호</label>
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
<<<<<<< Updated upstream
            
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
            <div class="col-sm-12">
              <button
                type="submit"
                class="btn btn-dark w-100 mb-5"
                :disabled="!isFormValid || isDuplicate"
              >
                회원가입
              </button>
=======
            <div
              class="d-flex align-items-center justify-content-center mt-5 mb-4"
            >
              <div class="col-sm-6">
                <button
                  type="submit"
                  class="btn btn-dark w-100 mb-5"
                  :disabled="!isFormValid"
                >
                  회원가입
                </button>
              </div>
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
import { ref, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const form = ref({
  id: '',
  password: '',
  confirmPassword: '',
  name: '',
  email: '',
=======
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();

const form = ref({
  email: "",
  password: "",
  confirmPassword: "",
  name: "",
>>>>>>> Stashed changes
});

const isDuplicate = ref(false);
const isAvailable = ref(false);
const router = useRouter();

const isFormValid = computed(() => {
  return (
    form.value.email &&
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
<<<<<<< Updated upstream
    const response = await axios.get(
      `http://localhost:8080/api/member/checkid/${form.value.id}`
    );
    isDuplicate.value = response.data; // 중복 여부 설정
    isAvailable.value = !isDuplicate.value;
  } catch (error) {
    console.error('중복 확인 실패:', error);
=======
    const response = await axios.get("http://localhost:8080/api/users/check-duplicate", {
      params: {
        email: form.value.email, // 이메일 기반 중복 확인
      },
    });
    if (response.data) {
      // duplicateChecked.value = true;
      alert("사용 가능한 이메일입니다.");
    } else {
      // duplicateChecked.value = false;
      alert("이미 사용 중인 이메일입니다.");
    }
  } catch (error) {
    console.error(error);
    alert("중복 확인 중 오류가 발생했습니다.");
>>>>>>> Stashed changes
  }
};

const submitForm = async () => {
<<<<<<< Updated upstream
  let formData = new FormData();

  formData.append('id', form.value.id);
  formData.append('password', form.value.password);
  formData.append('name', form.value.name);
  formData.append('email', form.value.email);

  try {
    const response = await axios.post(
      'http://localhost:8080/api/member',
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    console.log('회원가입 성공:', response.data);
    router.push('/welcome');
  } catch (error) {
    console.error('회원가입 실패:', error);
=======
  try {
    const response = await axios.post("http://localhost:8080/api/users/signup", {
      email: form.value.email,
      password: form.value.password,
    });
    alert(response.data); // "회원가입 성공!"
  } catch (error) {
    console.error(error);
    alert("회원가입 중 오류가 발생했습니다.");
>>>>>>> Stashed changes
  }
};
</script>

<style scoped>
.back-img {
  background-image: url('@/assets/images/register/register.jpg');
  background-size: cover; /* 이미지가 div를 채우도록 설정 */
  background-position: center; /* 이미지를 중앙에 위치시킴 */
}

.p-150 {
  padding: 100px;
}

.mt-20 {
  margin-top: 20px;
}

.ls-tight {
  letter-spacing: -0.02em;
}

.fw-bolder {
  font-weight: bolder;
}

.display-6 {
  font-size: 2.5rem;
}

.text-white {
  color: white;
}

.text-opacity-75 {
  opacity: 0.75;
}

.pe-xl-24 {
  padding-right: 1.5rem;
}

.shadow-soft-5 {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
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

.px-md-0 {
  padding-left: 0;
  padding-right: 0;
}

.py-10 {
  padding-top: 10px;
  padding-bottom: 10px;
}

.mb-5 {
  margin-bottom: 5px;
}
</style>
