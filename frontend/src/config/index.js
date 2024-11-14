export default {
  title: 'KB Fullstack',
  subtitle: '(Vue+Spring)',
  menus: [
    {
      title: 'CS테스트',
      url: '/CSTest/SelectCurriculum',
      // icon: 'fa-solid fa-paste',
    },
    {
      title: '모의면접',
      url: '/Letter/SelectStack',
      // icon: 'fa-solid fa-plane-departure',
    },
    {
      title: '마이페이지',
      url: '/gallery/list',
      // icon: 'fa-regular fa-images',
    },
  ],

  accoutMenus: {
    login: {
      url: '/auth/login',
      title: '로그인',
      // icon: 'fa-solid fa-right-to-bracket',
    },

    join: {
      url: '/auth/join',
      title: '회원가입',
      // icon: 'fa-solid fa-user-plus',
    },
  },
};
