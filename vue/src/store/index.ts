import {createStore} from "vuex";

const store = createStore({
    state: {
        userInfo: null as any,
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
            localStorage.setItem("userInfo", JSON.stringify(userInfo));
        },
        logout(state) {
            state.userInfo = null;
            localStorage.removeItem("userInfo");
        }
    }
});

export default store;