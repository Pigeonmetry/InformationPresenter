<template>
  <el-container class="root">
    <el-main>
      <div class="container">
        <div class="card">
          <div class="content">
            <span>登录</span>
            <el-form class="login-form">
              <el-form-item>
                <el-input maxlength="28"  class="input-light" type="email" v-model="data.login.email" required clearable>
                  <template #prefix>
                    <el-icon color="#1890ff" size="24px" class="el-input__icon">
                      <User/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-input minlength="6" maxlength="16"  class="input-light" type="password" v-model="data.login.password" required clearable
                          show-password @keyup.enter="submitLogin">
                  <template #prefix>
                    <el-icon color="#1890ff" size="24px" class="el-input__icon">
                      <Unlock/>
                    </el-icon>
                  </template>
                </el-input>
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
import qs from "qs";
import {ElMessage} from "element-plus";

interface Response {
  status: string,
  data: any;
  msg: string;
}

export default class LoginView extends Vue {

  mounted(){
    document.title="登录"
  }

  private data = {
    login: {
      email: '',
      password: ''
    }
  }

  public submitLogin() {
    let form = document.querySelector(".login-form") as HTMLFormElement;
    if (!form.reportValidity()) return;
    Request.inst({
      url: "/login",
      method: "post",
      data: qs.stringify(this.data.login)
    }).then(res => {
      console.log(res);
      let resp = res.data;
      if (resp.status == "ok") {
        store.commit('setUserInfo', resp.data);
        ElMessage({
          message: '登录成功',
          type: 'success',
          duration: 1000,
        });
        this.$router.push("/info");
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1000,
        });
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
  box-shadow: 0 10px 20px lightgray;
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

.login-form {
  width: 280px;
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