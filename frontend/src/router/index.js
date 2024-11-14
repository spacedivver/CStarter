import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import SelectCurriculum from '../pages/cstest/SelectCurriculum.vue';
import SelectCompany from '../pages/letter/SelectCompany.vue';
import Interview from '../pages/interview/Interview.vue';
import Setting from '../pages/interview/Setting.vue';
import SelectTests from '../pages/cstest/SelectTests.vue';
import SelectStack from '../pages/letter/SelectStack.vue';
import Result from '../pages/report/Result.vue';
import Write from '../pages/common/Write.vue';
import ViewInfo from '../pages/letter/ViewInfo.vue';

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
      path:'/CSTest/SelectTests',
      name: 'SelectTests',
      component: SelectTests,
    },
    {
      path: '/Letter/SelectCompany',
      name: 'SelectCompany',
      component: SelectCompany,
    },
    {
      path: '/Letter/ViewInfo/:id?',
      name: 'ViewInfo',
      component: ViewInfo,
    },
    {
      path: '/Letter/SelectStack',
      name: 'SelectStack',
      component: SelectStack,
    },
    {
      path: '/Interview',
      name: 'Interview',
      component: Interview,
    },
    {
      path: '/Interview/Setting',
      name: 'Setting',
      component: Setting,
    },
    {
      path: '/Report/Result',
      name: 'Result',
      component: Result,
    },
    {
      path: '/Write/:id?',
      name: 'Write',
      component: Write,
    },
    ...authRotes,
    ...boardRotes,
    ...travelRoutes,
    ...galleryRoutes,
  ],
});

export default router;
