import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
library.add(faUserSecret)
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css';
//수진 2024/01/29
import swal from 'sweetalert2'
import VueJsTour from '@globalhive/vuejs-tour';
import '@globalhive/vuejs-tour/dist/style.css';
window.Swal = swal
//수진 2024/01/29

// 앱이 시작될 때 푸시 알림 설정
store.dispatch('setupPushNotification')

createApp(App).component('font-awesome-icon', FontAwesomeIcon)
    .use(store).use(router).use(VueJsTour).mount('#app')
