<template>
  <div class="container">
    <div class="image-container">
      <img
        src="@/assets/images/header.png"
        alt=""
        style="width: 800px; height: auto"
      />
      <div class="btn-container">
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
          class="btn btn-primary d-flex flex-column align-items-center"
        >
          <div class="d-flex justify-content-between align-items-center w-100">
            <span class="fw-bold me-auto">모의 면접</span>
            <i class="fas fa-arrow-right"></i>
          </div>
          <span class="fw-light mt-3">내 자기소개서로 모의 면접하기</span>
        </a>
      </div>
    </div>
    <img
        src="@/assets/images/intro.png"
        alt=""
        style="width: 800px; height: auto;"
      />
  </div>
</template>

<style scoped>
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
  margin: 5px;
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

/* 이미지 컨테이너 스타일 */
.image-container {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 버튼 컨테이너 중앙 배치 */
.btn-container {
  position: absolute;
  top: 50%; /* 이미지의 수직 중앙에 위치하도록 설정 */
  left: 58%;
  transform: translate(-0%, -50%); /* 수평, 수직 모두 중앙에 위치 */
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 2;
}
</style>
