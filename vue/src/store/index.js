import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user: null
    },
    mutations: {
        setUser(state, userInfo) {
            state.user = userInfo
        }
    },
    actions: {
        login({ commit }, userInfo) {
            commit('setUser', userInfo)
        }
    },
    getters: {
        getUser: state => state.user
    }
})
