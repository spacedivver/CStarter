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
            오늘도 CStarter에 접속하시고,
            면접을 연습해 보세요. <br />
            또한, 결과 리포트를 통해 약점을 보완해 보세요.
          </p>
        </div>
      </div>
    </div>
    <div
      class="col-12 col-md-12 col-lg-7 offset-lg-5 vh-100 d-flex justify-content-center align-items-center border-start-lg shadow-soft-5"
    >
      <div class="w-md-50 mx-auto px-10 px-md-0 py-10">
        <div class="mb-3">
          <a class="d-inline-block d-lg-none mb-10" href="/pages/dashboard.html"
            ><img
              src="../../img/logos/logo-dark.svg"
              class="h-rem-10"
              alt="..."
          /></a>
          <h1 class="ls-tight fw-bolder h3">로그인</h1>
          <div class="mt-3 text-sm text-muted">
            <span>아직 회원이 아니라면 </span>
            <a href="/auth/join" class="fw-semibold">회원가입하기 </a>
          </div>
        </div>
        <form @submit.prevent="login">
          <div class="row g-5">
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
            <div class="col-sm-12">
              <button
                href="#"
                class="btn btn-dark w-100 mb-5"
                :disabled="disableSubmit"
              >
                로그인
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

const member = reactive({
  id: '',
  password: '',
});

const error = ref('');
const disableSubmit = computed(() => !(member.id && member.password));

const login = async () => {
  console.log(member);
  try {
    await auth.login(member);
    if (route.query.next) {
      router.push({ name: route.query.next });
    } else {
      router.push('/');
    }
  } catch (e) {
    console.log('error', e);
    alert(e.response.data);
    error.value = e.response.data;
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
