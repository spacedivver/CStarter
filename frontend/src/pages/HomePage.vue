<template>
  <div class="row-bg">
    <div class="container">
      <div class="row d-flex align-items-center">
        <!-- 왼쪽 이미지 -->
        <div class="col-6 d-flex justify-content-center">
          <img
            src="@/assets/images/header.png"
            alt=""
            style="width: 100%; height: auto"
          />
        </div>
        <!-- 오른쪽 버튼 -->
        <div class="col-6 d-flex justify-content-center">
          <div class="btn-container">
            <!-- 첫 번째 버튼 -->
            <a
              href="/CSTest/SelectCurriculum"
              class="btn btn-primary d-flex flex-column align-items-center"
            >
              <div
                class="d-flex justify-content-between align-items-center w-100"
              >
                <span class="fw-bold me-auto">CS 테스트</span>
                <i class="fas fa-arrow-right"></i>
              </div>
              <span class="fw-light mt-3">코딩 테스트처럼 한 문제씩 풀기</span>
            </a>

            <!-- 두 번째 버튼 -->
            <a
              href="/Letter/SelectStack"
              class="btn btn-primary d-flex flex-column align-items-center mt-3"
            >
              <div
                class="d-flex justify-content-between align-items-center w-100"
              >
                <span class="fw-bold me-auto">모의 면접</span>
                <i class="fas fa-arrow-right"></i>
              </div>
              <span class="fw-light mt-3">내 자기소개서로 모의 면접하기</span>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <img
      src="@/assets/images/intro.png"
      alt=""
      style="width: 100%; height: auto"
    />
  </div>
</template>

<style scoped>
/* 버튼 컨테이너 */
.btn-container {
  display: flex;
  flex-direction: column;
  align-items: center;
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
  gap: 20px;
}
/* Row에 배경색 추가 */
.row-bg {
  background-color: #d9e8f6; /* 원하는 색상으로 변경 */
  padding: 20px; /* 내용과 배경색의 여백 */
}
</style>
