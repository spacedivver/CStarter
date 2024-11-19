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
    <div class="col-4 d-flex justify-content-center align-items-center">
      <div class="container ms-5">
        <div class="mb-3">
          <h1 class="ls-tight fw-bolder h3 mt-5">로그인</h1>
          <div class="mt-3 text-sm text-muted">
            <span>아직 회원이 아니라면 </span>
            <a href="/auth/join" class="fw-semibold">회원가입하기 </a>
          </div>
        </div>
        <form @submit.prevent="login">
          <div class="row g-4">
            <div class="col-sm-12">
              <label for="id" class="form-label">아이디</label>
              <input
                type="text"
                class="form-control"
                placeholder="사용자 ID"
                v-model="member.id"
                autocomplete="current-id"
                required
              />
            </div>
            <div class="col-sm-12">
              <label for="password" class="form-label">비밀번호</label>
              <input
                type="password"
                class="form-control"
                placeholder="비밀번호"
                v-model="member.password"
                autocomplete="current-password"
                required
              />
            </div>
            <div
              class="d-flex align-items-center justify-content-center mt-5 mb-4"
            >
              <div class="col-sm-6">
                <button
                  href="#"
                  class="btn btn-dark w-100 mb-5"
                  :disabled="disableSubmit"
                  @click="navigateToMain"
                >
                  로그인
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
import { computed, reactive, ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

const member = reactive({
  id: "",
  password: "",
});

const error = ref("");
const disableSubmit = computed(() => !(member.id && member.password));

// const login = async () => {
//   console.log(member);
//   try {
//     await auth.login(member);
//     if (route.query.next) {
//       router.push({ name: route.query.next });
//     } else {
//       router.push("/");
//     }
//   } catch (e) {
//     console.log("error", e);
//     alert(e.response.data);
//     error.value = e.response.data;
//   }
// };

const navigateToMain = () => {
  if (disableSubmit) {
    router.push("/");
  }
};
</script>

<style scoped>
.back {
  background-color: #d9e8f6;
}

.back-img {
  background-size: contain; /* 이미지가 div를 채우도록 설정 */
  background-position: top; /* 이미지를 중앙에 위치시킴 */
  background-repeat: no-repeat; /* 이미지를 반복하지 않도록 설정 */
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
