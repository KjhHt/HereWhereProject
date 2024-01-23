import { createStore } from 'vuex'
import loginStore from './modules/loginStore'
import menuStore from './modules/menuStore'
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
    loginStore,menuStore
  },
  plugins: [
    createPersistedState({
      path: ['loginStore'] 
    })
  ],
})
