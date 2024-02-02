// store/modules/loginStore.js
//import router from '@/router'
import axios from 'axios'
const loginStore = {
  state: {
    userInfo: null,
    isLogin: false,
  },
  mutations: { // 로그인 상태를 변경해주는 코드
    loginSuccess: function (state, payload) {
      state.userInfo = payload
      state.isLogin = true
      window.location.href="http://localhost:8080/"
    },
    logoutTest: function (state) {
      state.userInfo = null
      state.isLogin = false
      localStorage.removeItem('access_token')
      localStorage.removeItem('vuex')
      localStorage.removeItem('profileImage')
      window.location.href="http://localhost:8080/"
    },
  },
  actions: {
    login(dispatch, loginObj) {
        const formdata = new FormData();
        formdata.append("id",loginObj['id']);
        formdata.append("pwd",loginObj['pwd']);

        axios.post('http://localhost:8080/login', formdata) // 로그인 URL로 ID, PW를 보냄
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
        return axios.post('http://localhost:8080/user/getToken');
    },
    getMemberInfo( {commit} ){
        axios.get('http://localhost:8080/user/getMemberInfo')
        .then( res => {
            const userInfo = {
                name : res.data.name,
                profileimage : res.data.profileimage,
            }
            commit('loginSuccess', userInfo)
        })
    },
    logout( {commit} ){
        axios.get('http://localhost:8080/logout')
        .then( res => {
            console.log(res)
            commit('logoutTest')
        } )
    },

  }
}

export default loginStore