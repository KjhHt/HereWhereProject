// store/modules/loginStore.js
//import router from '@/router'
import axios from 'axios'
import { messaging } from '@/firebase.js'

const loginStore = {
  state: {
    userInfo: null,
    isLogin: false,
  },
  mutations: { // 로그인 상태를 변경해주는 코드
    loginSuccess: function (state, payload) {
      state.userInfo = payload
      state.isLogin = true
      window.location.href=process.env.VUE_APP_API_URL+'/'
    },
    logoutTest: function (state) {
      state.userInfo = null
      state.isLogin = false
      localStorage.removeItem('access_token')
      localStorage.removeItem('vuex')
      localStorage.removeItem('profileImage')
      window.location.href=process.env.VUE_APP_API_URL+'/'
    },
  },
  actions: {
    login(dispatch, loginObj) {
        const formdata = new FormData();
        formdata.append("id",loginObj['id']);
        formdata.append("pwd",loginObj['pwd']);
        axios.post(process.env.VUE_APP_API_URL+'/login', formdata) // 로그인 URL로 ID, PW를 보냄
        .then((res) => {
          console.log(res);
          return this.dispatch('requestPushNotificationPermission') 
        })
        .then((res) => {
            console.log(res);
            return this.dispatch('getToken')
        })
        .then((getTokenResponse) => {
            const token = getTokenResponse.data
            this.dispatch('saveToken',token)
        })
        .catch(error => console.log(error))
    },
    saveToken({ dispatch },token){
        localStorage.setItem('access_token', token) // 토큰을 저장함
        dispatch('getMemberInfo')
    },
    getToken() {
        return axios.post(process.env.VUE_APP_API_URL+'/user/getToken');
    },
    getMemberInfo( {commit} ){
        axios.get(process.env.VUE_APP_API_URL+'/user/getMemberInfo')
        .then( async res => {
            const userInfo = {
                id : res.data.id,
                name : res.data.name,
                profileimage : res.data.profileimage,
                mbti : res.data.mbti,
                phonenumber : res.data.phonenumber,
                lat : res.data.lat,
                lng : res.data.lng,
            }
            commit('loginSuccess', userInfo)
        })
    },
    async logout({ commit }) {
      try {
        await axios.post(process.env.VUE_APP_API_URL+'/deletePushToken', null);
        //const res = await axios.post(process.env.VUE_APP_API_URL+'/logout');
        const res = await axios.get(process.env.VUE_APP_API_URL+'/logout');
          console.log(res);
          commit('logoutTest');
      } catch (err) {
          console.error(err);
      }
    },
    requestPushNotificationPermission({ dispatch }) {
      return new Promise((resolve) => {
        Notification.requestPermission().then(permission => {
          if (permission === 'granted') {
            dispatch('setupPushNotification');
            resolve();
          } else {
            console.log('푸시 알림 권한이 거부되었습니다.');
            resolve();  // 권한이 거부되더라도 로그인 과정이 멈추지 않도록 resolve를 호출합니다.
          }
        });
      });
    },
    setupPushNotification() {
      if (!localStorage.getItem('access_token')) return;
      return new Promise((resolve) => {
        messaging.getToken({vapidKey: 'BMjNjB-i2kPTsQgWstgTg_D47V0HzGg1V5vMY9pc7WPp2Asj3kZBN3GM5wKZoX2DjUUBYvweZEk-vPiqW5j8moI'})
          .then((token) => {
            axios.post(process.env.VUE_APP_API_URL+'/register', { token: token })
              .then((response) => {
                //console.log() 안찍으려고 if문으로 뺐음 (response)
                if(response.data)
                console.log('푸쉬로고..');
                messaging.onMessage((payload) => {
                  console.log('Message received. ', payload);
                  if (Notification.permission === 'granted') {
                    const notificationTitle = payload.notification.title;
                    const notificationOptions = {
                      body: payload.notification.body,
                      icon: '/logo.png'
                    };
                    new Notification(notificationTitle, notificationOptions);
                  }
                });
                resolve();
              })
              .catch((error) => {
                console.error(error);
              });
          }).catch((err) => {
            console.log('An error occurred while retrieving token. ', err);
          });
      });
    },
  }
}

export default loginStore