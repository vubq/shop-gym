import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginPage from '@/views/login/index.vue';
import ADProductPage from '@/views/admin/product/index.vue';
import AdminLayout from '@/layouts/admin/index.vue';

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'HomePage',
    component: HomeView,
    meta: {
      title: 'Home Page',
      icon: 'el-icon-user-solid',
      roleAdmin: true
    }
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage,
    meta: {
      title: 'Home Page',
      icon: 'el-icon-user-solid',
      hiddenSidebar: true
    }
  },
  {
    path: '/admin',
    name: 'AdminPage',
    component: AdminLayout,
    redirect: '/admin',
    meta: {
      hiddenSidebar: true
    },
    children: [
      {
        path: 'product',
        name: 'ADProductPage',
        component: ADProductPage,
        meta: {
          title: 'Quản lý sản phẩm',
          icon: 'el-icon-user-solid',
        }
      },
      {
        path: 'hehee',
        name: 'hehehe',
        component: ADProductPage,
        meta: {
          title: 'Hehehe',
          icon: 'el-icon-user-solid',
        }
      }
    ]
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.roleAdmin)) {
    const user = JSON.parse(localStorage.getItem('user') as any);
    let isAccess = false;
    if(user) {
      user.roles.map((e: any) => {
        if(e === 'ROLE_SALES') {
          isAccess = true;
        }
      })
    }
    if (!isAccess) {
      next({
        path: '/login',
        params: { nextUrl: to.fullPath },
      });
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router;
