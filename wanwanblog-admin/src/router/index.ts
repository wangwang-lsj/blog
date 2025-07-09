import { createRouter, createWebHistory } from 'vue-router';

import NotFoundView from '@/views/NotFoundView.vue';
import LoginView from '@/views/LoginView.vue';
import RegisterView from '@/views/RegisterView.vue';
import AdminView from '@/views/AdminView.vue';
import AdminHomeView from '@/views/AdminViews/AdminHomeView.vue';
import DashBoardView from '@/views/AdminViews/DashBoardView.vue';
import GaodeMapView from '@/views/AdminViews/GaodeMapView.vue';
import PersonalInfoView from '@/views/AdminViews/PersonalInfoView.vue';
import UpdataPWView from '@/views/AdminViews/UpdataPWView.vue';
import HomeManageView from '@/views/AdminViews/WebManageViews/HomeManageView.vue';
import ArticleManageView from '@/views/AdminViews/WebManageViews/ArticleManageView.vue';
import MessageManageView from '@/views/AdminViews/WebManageViews/MessageManageView.vue';
import UserManageView from '@/views/AdminViews/SystemManageViews/UserManageView.vue';
import RoleManageView from '@/views/AdminViews/SystemManageViews/RoleManageView.vue';
import MenuManageView from '@/views/AdminViews/SystemManageViews/MenuManageView.vue';
import FileManageView from '@/views/AdminViews/SystemManageViews/FileManageView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.VITE_API_BASE_URL),
  routes: [
    {
      path: '/404',
      name: 'notfound',
      component: NotFoundView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
    },
    {
      path: '/',
      name: 'admin',
      component: AdminView,
      redirect: '/home',
      children: [
        {
          path: 'home',
          name: 'home',
          component: AdminHomeView,
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: DashBoardView,
        },
        {
          path: 'gaodemap',
          name: 'gaodemap',
          component: GaodeMapView,
        },
        {
          path: 'personalInfo',
          name: 'personalInfo',
          component: PersonalInfoView,
        },
        {
          path: 'updatapw',
          name: 'updatapw',
          component: UpdataPWView,
        },
        {
          path: 'webmanage',
          name: 'webmanage',
          redirect: '/webmanage/home',
          children: [
            {
              path: 'home',
              name: 'home',
              component: HomeManageView,
            },
            {
              path: 'article',
              name: 'article',
              component: ArticleManageView,
            },
            {
              path: 'message',
              name: 'message',
              component: MessageManageView,
            },
          ],
        },
        {
          path: 'systemmanage',
          name: 'systemmanage',
          redirect: '/systemmanage/user',
          children: [
            {
              path: 'user',
              name: 'user',
              component: UserManageView,
            },
            {
              path: 'role',
              name: 'role',
              component: RoleManageView,
            },
            {
              path: 'menu',
              name: 'menu',
              component: MenuManageView,
            },
            {
              path: 'file',
              name: 'file',
              component: FileManageView,
            },
          ],
        },
      ],
    },
  ],
});

export default router;
