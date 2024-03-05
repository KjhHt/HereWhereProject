
const flightStore = {
    namespaced:true,
    state: {
        flightInfo: null,
    },
    mutations: {
        setFlightInfo(state,value){
            state.flightInfo = value;
        }
    },
    actions: {
        
    },
    getters: {
        getFlightInfo(state){
            return state.flightInfo
        }
    }
}


export default flightStore