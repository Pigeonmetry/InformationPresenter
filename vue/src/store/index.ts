import {createStore} from "vuex";

const store = createStore({
    state: {
        userInfo: null as any,
    },
    mutations: {
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
            localStorage.setItem("userInfo", JSON.stringify(userInfo));
        }
    }
});

export default store;