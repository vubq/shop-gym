import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/login'
import Layout from '@/layout/layout'

Vue.use(Router)

/**
 * 关于 route 的配置属性说明：
 *
 * alwaysShow: true       // if set true, will always show the root menu, whatever its child routes length
 *                        // if not set alwaysShow, only more than ont route under the children
 *                        // it will becomes nested mode, otherwise not show the root menu
 *                        // 如果设置为true,它将总是出现在根目录。如果不设置的话，当它只有1个子路由的时候，会把
 *                        // 它的唯一子路由放到跟目录上来，而不显示它自己本身。
 *
 * hidden: true           // if set ture, 将不会出现在左侧菜单栏中
 */

/**
 * 基础路由： 任何角色都包含的路由
 * @type {Array}
 */
export const constantRouterMap = [
  {
    path: '/login',
    name: 'login',
    hidden: true,
    component: Login,
    meta: {
      title: '登录'
    }
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/',
    // hidden: true,
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/homepage'),
        meta: {icon: 's-home', title: '首页'}
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    meta: {
      icon: 'tickets',
      title: '个人中心'
    },
    children: [
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/user/profile'),
        meta: {icon: 'warning', title: '个人中心'}
      }
    ]
  },
  {
    path: '/product-management',
    meta: {
      icon: 'svg-pifu', title: 'QL Sản phẩm'
    },
    component: Layout,
    redirect: '/product',
    children: [
      {
        path: 'product',
        name: 'product-management.product',
        component: () => import('@/views/manage-system/product'),
        meta: {
          icon: 'svg-pifu', title: 'Sản phẩm'
        }
      },
      {
        path: 'category',
        name: 'product-management.category',
        component: () => import('@/views/manage-system/category'),
        meta: {
          icon: 'svg-pifu', title: 'Danh mục'
        }
      },
      {
        path: 'brand',
        name: 'product-management.brand',
        component: () => import('@/views/manage-system/product'),
        meta: {
          icon: 'svg-pifu', title: 'Thương hiệu'
        }
      },
      {
        path: 'color',
        name: 'product-management.color',
        component: () => import('@/views/manage-system/product'),
        meta: {
          icon: 'svg-pifu', title: 'Màu sắc'
        }
      },
      {
        path: 'size',
        name: 'product-management.size',
        component: () => import('@/views/manage-system/product'),
        meta: {
          icon: 'svg-pifu', title: 'Kích cỡ'
        }
      },
      {
        path: 'material',
        name: 'product-management.material',
        component: () => import('@/views/manage-system/product'),
        meta: {
          icon: 'svg-pifu', title: 'Chất liệu'
        }
      }
    ]
  }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [

]