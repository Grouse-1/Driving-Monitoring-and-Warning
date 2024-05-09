import Vuex from'vuex'
import { createStore } from 'vuex'
import auth from '@/store/auth/auth.js'
export default createStore({
    state:{
        counter: 1
    },
    modules:{
        auth,
    }
})