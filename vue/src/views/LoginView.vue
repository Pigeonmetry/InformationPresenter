<template>
  <el-container class="root">
    <el-main>
      <div class="container">
        <div class="card">
          <div class="content">
            <span>登录</span>
            <el-form label-width="60px">
              <el-form-item label="用户名">
                <el-input class="input-light" name="username" v-model="data.login.username"></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input class="input-light" name="password" type="password" v-model="data.login.password"></el-input>
              </el-form-item>
            </el-form>
            <el-button class="button" @click="submitLogin" style="--el-button-hover-bg-color: var(--hover-bg-color)"
                       circle>
              <el-icon color="#ffffff" :size="30">
                <Right/>
              </el-icon>
            </el-button>
          </div>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import {Vue} from "vue-class-component";
import Request from "../api/Request";
import store from "../store";
import {ElMessage} from "element-plus";

enum DialogType {
  login,
  signup
}

export default class LoginView extends Vue {

  mounted() {
    let loginInfo = JSON.parse(localStorage.getItem("loginInfo"));
    if (loginInfo) {
      store.state.userInfo = loginInfo;
      this.$router.replace("/info");
    }
  }

  private data = {
    login: {
      username: '',
      password: ''
    },
    signup: {
      username: '',
      password: '',
      password_confirmation: ''
    }
  }

  public submitLogin() {
    Request.inst({
      url: "/login",
      method: "post",
      data: qs.stringify(this.data.login)
    }).then(res => {
      console.log(res);
      let resp = JSON.parse(res.data);
      if (resp.status == "success") {
        let userInfo = resp.data;
        store.commit('setUserInfo', userInfo);
        ElMessage({
          message: '登录成功',
          type: 'success',
          duration: 1000,
        });
        this.$router.push("/info");
      }
    }).catch(err => {
      console.log(err);
      ElMessage({
        message: err.message,
        type: 'error',
        duration: 1000,
      });
    })
  }
}
</script>

<style scoped>

.root {
  height: 100%;
}

.container {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  width: 600px;
  height: 400px;
  border-radius: 10px;
  background-color: var(--bg-color);
  box-shadow: 0 10px 20px gray;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.content {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.content > span {
  font-size: 3ex;
  font-weight: bold;
  margin-top: 40px;
  color: var(--text-color);
}

.content > .el-button {
  margin-bottom: 50px;
}

.el-form-item {
  margin: 40px 0;
}

.button {
  width: 40px;
  height: 40px;
  transform: scale(1.5);
  --el-button-bg-color: var(--input-border-color);
}

</style>