// 사이드 메뉴 선택
const menuStore = {
    state: {
      menu:0
    },
    getters: {
      getMenu(state){
        return state.menu;
      }
    },
    mutations: {
      setMenu(state, value){
          state.menu = value;
        }
    },
    actions: {
    }
  }
  export default menuStore