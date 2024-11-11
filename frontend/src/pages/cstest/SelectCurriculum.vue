<template>
  <div>
    <div>커리큘럼</div>
    <label for="keyword" class="col-2 col-form-label">키워드 검색</label>
    <div class="col-9">
      <div class="mt-2">
        <div class="input-group input-group-sm">
          <input
            type="text"
            class="form-control ms-1 rounded"
            placeholder="과정명을 입력해 주세요."
            v-model="searchValue"
          />
        </div>
      </div>
    </div>

    <!-- Toggle button for pill container -->
    <label
      for="keyword"
      class="col-2 col-form-label"
      @click="togglePills"
      style="cursor: pointer"
    >
      Frontend
    </label>

    <!-- Pills container -->
    <div class="col-9" v-show="showPills">
      <ul class="nav nav-pills">
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill1"
            class="nav-checkbox"
            data-value="Link1"
            v-model="selectedItems"
            value="Link1"
          />
          <label for="pill1" class="nav-label">Link 1</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill2"
            class="nav-checkbox"
            data-value="Link2"
            v-model="selectedItems"
            value="Link2"
          />
          <label for="pill2" class="nav-label">Link 2</label>
        </li>
        <li class="nav-item">
          <input
            type="checkbox"
            id="pill3"
            class="nav-checkbox"
            data-value="Link3"
            v-model="selectedItems"
            value="Link3"
          />
          <label for="pill3" class="nav-label">Link 3</label>
        </li>
      </ul>
    </div>
    <button @click="sendSelectedItems">선택된 항목 보내기</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchValue: "",
      showPills: false,
      selectedItems: [],
    };
  },
  methods: {
    togglePills() {
      this.showPills = !this.showPills;
    },
    sendSelectedItems() {
      console.log("선택된 항목:", this.selectedItems); // 백엔드 전송용 데이터
      fetch("/api/selected-items", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ selectedItems: this.selectedItems }),
      })
        .then((response) => response.json())
        .then((data) => console.log("서버 응답:", data))
        .catch((error) => console.error("오류:", error));
    },
  },
};
</script>

<style>
.nav-item {
  display: inline-block;
  margin-right: 5px;
}
.nav-checkbox {
  display: none;
}
.nav-label {
  padding: 8px 16px;
  border: 1px solid #007bff;
  border-radius: 4px;
  color: #007bff;
  cursor: pointer;
  background-color: #fff;
}
.nav-checkbox:checked + .nav-label {
  background-color: #007bff;
  color: white;
}
.disabled {
  color: #6c757d;
  cursor: not-allowed;
  border-color: #6c757d;
}
</style>
