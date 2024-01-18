import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '../views/MainPage.vue'
import store from '@/store'
import axios from 'axios';

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainPage,
    beforeEnter: (to, from, next) => {
      //
      if (store.state.loginStore.isLogin){
        console.log('로그인 된 상태')
        next();
      }
      else{
        console.log('로그인 안 된 상태')
        axios.get('http://localhost:8080/isSocialLogin')
        .then(res=>{
          console.log(res.data)
          if(res.data==null || res.data==""){
            console.log('토큰이 유효하지 않거나 없습니다.')
          }
          else{
            console.log('들어오지????')
            store.dispatch('saveToken', res.data)
          }
        })
      }
    },
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
