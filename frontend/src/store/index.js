import { createStore } from 'vuex'
import loginStore from './modules/loginStore'
import createPersistedState from 'vuex-persistedstate'
export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    loginStore
  },
  plugins: [
    createPersistedState({
      path: ['loginStore'] 
    })
  ],
})
