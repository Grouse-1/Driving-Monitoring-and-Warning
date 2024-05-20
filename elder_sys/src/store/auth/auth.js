import ULogin from "@/api/Login/index.js";
import {GetElderly, GetSeller} from "@/api/getInfo/index.js";

const state = {
    token: null,
    role: null,
    elderid:null,
    shopid:null
}

const mutations = {
    SET_TOKEN(state, token) {
        state.token = token;
    },
    CLEAR_TOKEN(state) {
        state.token = null;
    },
    SET_ROLES(state, roles) {
        state.roles = roles;
    },
    SET_ELDERID(state, elderid) {
        state.elderid = elderid;
    }
}

const actions = {
    login({ commit }, { username, password, role }) {
        console.log("加载action")
        return new Promise((resolve, reject) => {
            ULogin.login({ name: username, password: password, role: role })
                .then(response => {

                    commit('SET_TOKEN', response.data.data.token);
                    commit('SET_ROLES', role);
                    console.log("记录token返回数据")
                    localStorage.setItem('token', response.data.data.token);
                    localStorage.setItem('role', role);
                    resolve(response);
                    if(role === 'family') {
                        GetElderly.getID(localStorage.getItem('token')).then(res1 =>{
                            localStorage.setItem('elderid', res1.data);
                        })
                    }
                    if(role === 'seller') {
                        GetSeller.getID(localStorage.getItem('token')).then(res2 =>{
                            localStorage.setItem('shopid', res2.data);
                        })
                    }
                })
                .catch(error => {
                    reject(error);
                });
        })
    },

    logout({ commit }) {
        return new Promise((resolve) => {
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            localStorage.removeItem('elderid');
            localStorage.removeItem('shopid');
            // 执行异步退出登录操作
            // 成功时，通过 commit('SET_TOKEN', null) 清除 token
            // 失败时，通过 resolve() 放弃退出登录
        })
    }
}

const getters = {
    getToken(state) {
        return state.token;
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions,
    getters
}
