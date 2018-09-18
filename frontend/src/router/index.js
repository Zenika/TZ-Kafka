import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/Login'
import Logout from '@/components/Logout'
import Prono from '@/components/Prono'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      beforeEnter: checkAuth
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout,
      beforeEnter: checkAuth
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/prono',
      name: 'Prono',
      component: Prono,
      beforeEnter: checkAuth
    }
  ]
})

function checkAuth (to, from, next) {
  const authed = localStorage.getItem('user-token')
  if (!authed) {
    next('/login')
  } else {
    next()
  }
}
