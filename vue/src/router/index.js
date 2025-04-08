import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/manager',
    name: 'manager',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManagerLayout.vue'),
    children: [
      { path: 'home', component: () => import('../views/manager/HomeView.vue') },
      { path: 'admin', component: () => import('../views/manager/Admin.vue') },
      { path: 'consumer', component: () => import('../views/manager/Consumer.vue') },
      // { path: 'search', component: () => import('../views/manager/SearchView.vue') },
      { path: 'collect', component: () => import('../views/manager/Collect.vue') },
      { path: 'history', component: () => import('../views/manager/History.vue') },
      { path: 'song_manager', component: () => import('../views/manager/SongManager.vue') },
      { path: 'user_info', component: () => import('../views/manager/UserInfo.vue') }
      
    ]
  },
  {
    path: '/register',
    name: 'register',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
  },
  // {
  //   path: '/music_player',
  //   name: 'music_player',
  //   component: () => import(/* webpackChunkName: "about" */ '../views/MusicPlayer.vue'),
  // },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 路由守卫
router.beforeEach((to ,from, next) => {
  if (to.path === '/') {
    return next("/login");
  }
  let user = localStorage.getItem("user");
  if (!user && to.path !== '/login' && to.path !== '/register' && to.path !== '/front/index') {
    return next("/login");
  }
  next();
})

export default router
