import './assets/main.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'vue-awesome-paginate/dist/style.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'; // 스타일 파일 추가
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import VueAwesomePaginate from 'vue-awesome-paginate';

import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(VueAwesomePaginate);
app.use(createPinia());
app.use(router);
app.use(ElementPlus); // Element Plus 사용

app.mount('#app');
