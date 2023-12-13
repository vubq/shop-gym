import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginPage from '@/views/login/index.vue';
import ADProductPage from '@/views/admin/product/index.vue';
import ADProductDetailPage from '@/views/admin/product/product-detail/index.vue';
import AdminLayout from '@/layouts/admin/index.vue';
import ADSalesAtTheCounter from '@/views/admin/sales-at-the-counter/index.vue'

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'home.page',
    component: HomeView,
    redirect: '/admin/product',
    meta: {
      title: 'Home Page',
      icon: 'el-icon-user-solid',
      roleAdmin: true
    }
  },
  {
    path: '/login',
    name: 'login.page',
    component: LoginPage,
    meta: {
      title: 'Home Page',
      icon: 'el-icon-user-solid',
      hiddenSidebar: true
    }
  },
  {
    path: '/admin',
    name: 'admin.page',
    component: AdminLayout,
    redirect: '/admin/product',
    meta: {
      hiddenSidebar: true
    },
    children: [
      {
        path: 'product',
        name: 'admin.product.page',
        component: ADProductPage,
        meta: {
          title: 'Quản lý sản phẩm',
          icon: 'el-icon-user-solid',
        }
      },
      {
        path: 'product/product-detail',
        name: 'admin.product.product_detail.page',
        component: ADProductDetailPage,
        meta: {
          hiddenSidebar: true
        }
      },
      {
        path: 'product/product-detail/:productId',
        name: 'admin.product.product_detail.id.page',
        component: ADProductDetailPage,
        meta: {
          hiddenSidebar: true
        }
      },
      {
        path: 'sales-at-the-counter',
        name: 'admin.sales-at-the-counter.page',
        component: ADSalesAtTheCounter,
        meta: {
          title: 'Bán hàng tại quầy',
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
