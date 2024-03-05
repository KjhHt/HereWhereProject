import { createRouter, createWebHistory } from 'vue-router'
import MainPage from '../views/MainPage.vue'
import store from '@/store'
import axios from 'axios';
import AirTicketPage from '../views/AirTicketTemp.vue' 
import ExchangePage from '../views/CurrencyView.vue'  
import TravelPage from '../views/TravelView.vue' 
const routes = [
  {
    path: '/',
    name: 'main',
    component: MainPage,
    beforeEnter: (to, from, next) => {
      //
      if (store.state.loginStore.isLogin){
        next();
      }
      else{
        axios.get(process.env.VUE_APP_API_URL+'/isSocialLogin')
        .then(res=>{
          console.log(res.data)
          if(res.data==null || res.data==""){
            console.log('토큰이 유효하지 않거나 없습니다.')
            next();
          }
          else{
            store.dispatch('saveToken', res.data)
            next();
          }
        })
      }
    },
  },

  {
    path: '/AirTicket',  // 추가
    name: 'AirTicket',  // 추가
    component: AirTicketPage,  // 추가
  },
  {
    path: '/exchange',  // 추가
    name: 'exchange',  // 추가
    component: ExchangePage,  // 추가
  },

  {
    path: '/travel',  // 추가
    name: 'travel',  // 추가
    component: TravelPage,  // 추가
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
