import ULogin from "@/api/Login/index.js";

const state = {
    token: null,
    role: null
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
                })
                .catch(error => {
                    reject(error);
                });
            // 执行异步登录操作
            // 成功时，通过 commit('SET_TOKEN', token) 设置 token
            // 失败时，通过 reject(reason) 抛出错误信息
        })
    },

    logout({ commit }) {
        return new Promise((resolve) => {
            localStorage.removeItem('token');
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
