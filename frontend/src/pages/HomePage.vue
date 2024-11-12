<template>
  <div class="welcome-message">
    <h2>
      <div class="fw-light">안녕하세요. <br /></div>
      <div>
        <span class="fw-bold">CStarter</span>
        <span class="fw-light">입니다! <br /></span>
      </div>
    </h2>
  </div>

  <div class="d-flex justify-content-center">
    <a
      href="/CSTest/SelectCurriculum"
      class="btn btn-primary d-flex flex-column align-items-center"
    >
      <div class="d-flex justify-content-between align-items-center w-100">
        <span class="fw-bold me-auto">CS 테스트</span>
        <i class="fas fa-arrow-right"></i>
      </div>
      <span class="fw-light mt-3">코딩 테스트처럼 한 문제씩 풀기</span>
    </a>

    <!-- 두 번째 버튼 -->
    <a
      href="/Letter/SelectStack"
      class="btn btn-primary d-flex flex-column align-items-center ms-3"
    >
      <div class="d-flex justify-content-between align-items-center w-100">
        <span class="fw-bold me-auto">모의 면접</span>
        <i class="fas fa-arrow-right"></i>
      </div>
      <span class="fw-light mt-3">내 자기소개서로 모의 면접하기</span>
    </a>
  </div>
</template>

<style scoped>
/* 안녕하세요 CStarter입니다 부분 스타일 */
.welcome-message {
  font-size: 36px;
  font-weight: bold;
  color: #ff8000;
  text-align: center;
  margin-bottom: 30px;
}

.highlighted-text {
  font-size: 42px; /* 텍스트 크기 증가 */
  font-weight: bold;
  color: #ff8000;
}

/* 버튼 스타일 */
.btn {
  display: inline-block;
  background-color: #007bff;
  color: white;
  padding: 15px 25px;
  font-size: 18px;
  text-align: center;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s;
  margin: 10px;
  width: auto;
}

.btn:hover {
  background-color: #0056b3;
}

.ms-3 {
  margin-left: 20px;
}

.fas {
  margin-right: 10px; /* 아이콘과 텍스트 간의 간격 */
}

/* 모바일 대응: 버튼 크기 조정 */
@media (max-width: 768px) {
  .btn {
    font-size: 16px;
    padding: 12px 20px;
  }
}
</style>
