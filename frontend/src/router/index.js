import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import SelectCurriculum from '../pages/cstest/SelectCurriculum.vue';
import SelectCompany from '../pages/letter/SelectCompany.vue';
import Interview from '../pages/interview/interview.vue';
import Result from '../pages/report/Result.vue';

import authRotes from './auth';
import boardRotes from './board';
import travelRoutes from './travel';
import galleryRoutes from './gallery';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path: '/CSTest/SelectCurriculum',
      name: 'SelectCurriculum',
      component: SelectCurriculum,
    },
    {
      path: '/Letter/SelectCompany',
      name: 'SelectCompany',
      component: SelectCompany,
    },
    {
      path: '/Interview',
      name: 'Interview',
      component: Interview,
    },
    {
      path: '/Report/Result',
      name: 'Result',
      component: Result,
    },
    ...authRotes,
    ...boardRotes,
    ...travelRoutes,
    ...galleryRoutes,
  ],
});

export default router;
