import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import SelectCompany from '../pages/letter/SelectCompany.vue';
import Interview from '../pages/interview/Interview.vue';
import CStestInterview from '@/pages/cstest/CStestInterview.vue';
import Setting from '../pages/interview/Setting.vue';
import CStestSetting from '@/pages/cstest/CStestSetting.vue';
import SelectTests from '../pages/cstest/SelectTests.vue';
import SelectStack from '../pages/letter/SelectStack.vue';
import Result from '../pages/report/Result.vue';
import Write from '../pages/common/Write.vue';
import Mypage from '../pages/mypage/Mypage.vue';

import authRotes from './auth';
import boardRotes from './board';
import travelRoutes from './travel';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    
    {
      path:'/CSTest/SelectTests',
      name: 'SelectTests',
      component: SelectTests,
    },
    {
      path:'/CSTest/CStestSetting',
      name: 'CStestSetting',
      component: CStestSetting,

    },{
      path:'/CSTest/CStestInterview',
      name:'CStestInterview',
      component: CStestInterview,
    },
    {
      path: '/Letter/SelectCompany',
      name: 'SelectCompany',
      component: SelectCompany,
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
    {
      path: '/Mypage',
      name: 'Mypage',
      component: Mypage,
    },
    ...authRotes,
    ...boardRotes,
    ...travelRoutes,
  ],
});

export default router;
