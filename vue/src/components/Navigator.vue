<template>
  <el-header class="root">
    <div class="navigator container">
      <!--左侧-->
      <div class="left">
        <!--标题图片-->
        <div class="logo" onclick="alert('to home')">
          <svg t="1653983164086" class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"
               p-id="1899" width="200" height="200">
            <path
                d="M341.35 661.338c-105.872 0-191.994-86.152-191.994-191.994 0-105.872 86.124-191.992 191.994-191.992s191.994 86.122 191.994 191.992c0 105.842-86.124 191.994-191.994 191.994z"
                fill="#FFCE54" p-id="1900"></path>
            <path
                d="M341.35 256.008c-117.808 0-213.338 95.496-213.338 213.336 0 117.81 95.53 213.306 213.338 213.306s213.338-95.496 213.338-213.306c0-117.84-95.53-213.336-213.338-213.336z m120.684 333.99c-32.248 32.248-75.092 49.998-120.684 49.998s-88.436-17.75-120.684-49.998c-32.216-32.218-49.998-75.092-49.998-120.654 0-45.592 17.782-88.464 49.998-120.684 32.248-32.248 75.092-49.998 120.684-49.998s88.436 17.75 120.684 49.998C494.25 380.878 512 423.752 512 469.344c0 45.562-17.75 88.436-49.966 120.654z"
                fill="#F6BB42" p-id="1901"></path>
            <path
                d="M145.262 635.278l-45.28 45.248c-8.312 8.312-8.312 21.812 0 30.156a21.354 21.354 0 0 0 30.186 0l45.25-45.25c8.344-8.344 8.344-21.842 0-30.154-8.344-8.344-21.844-8.344-30.156 0zM582.686 227.976c-8.312-8.312-21.812-8.312-30.154 0l-45.25 45.25a21.33 21.33 0 0 0 0 30.186 21.3 21.3 0 0 0 15.094 6.25c5.438 0 10.906-2.094 15.062-6.25l45.248-45.248a21.332 21.332 0 0 0 0-30.188z"
                fill="#FFCE54" p-id="1902"></path>
            <path
                d="M341.35 128.012c-11.782 0-21.344 9.562-21.344 21.344v63.998c0 11.78 9.562 21.31 21.344 21.31s21.342-9.532 21.342-21.31V149.356c0-11.782-9.562-21.344-21.342-21.344z"
                fill="#F6BB42" p-id="1903"></path>
            <path
                d="M145.262 303.412a21.28 21.28 0 0 0 15.062 6.25 21.3 21.3 0 0 0 15.094-6.25 21.33 21.33 0 0 0 0-30.186l-45.25-45.25c-8.344-8.312-21.842-8.312-30.186 0-8.312 8.344-8.312 21.844 0 30.188l45.28 45.248z"
                fill="#FFCE54" p-id="1904"></path>
            <path
                d="M106.702 469.344c0-11.782-9.562-21.342-21.344-21.342H21.36c-11.782 0-21.344 9.56-21.344 21.342s9.562 21.312 21.344 21.312h63.998c11.78 0 21.344-9.53 21.344-21.312z"
                fill="#F6BB42" p-id="1905"></path>
            <path
                d="M765.336 378.692c-74.56 0-141.744 31.56-188.932 82.028-25.31-29.124-62.592-47.56-104.214-47.56-76.186 0-137.934 61.78-137.934 137.966 0 1.156 0 2.282 0.032 3.436-78.716 15.968-137.996 85.56-137.996 168.996 0 95.214 77.216 172.432 172.432 172.432h396.614c142.87 0 258.648-115.81 258.648-258.648-0.002-142.874-115.78-258.65-258.65-258.65z"
                fill="#CCD1D9" p-id="1906"></path>
            <path
                d="M925.642 434.346c34.876 44.092 55.688 99.78 55.688 160.308 0 142.87-115.808 258.68-258.65 258.68H326.068c-36.716 0-70.748-11.5-98.714-31.062 31.186 44.562 82.874 73.716 141.37 73.716h396.614c142.87 0 258.648-115.81 258.648-258.648-0.002-82.31-38.438-155.622-98.344-202.994z"
                fill="#AAB2BC" p-id="1907"></path>
          </svg>
          <span class="title">{{ title }}</span>
        </div>
        <!--导航-->
      </div>
      <!--右侧-->
      <div class="right">
        <el-menu
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false">
          <el-menu-item index="1" @click="dialog.signup=true">注册</el-menu-item>
          <el-menu-item index="2" @click="dialog.login=true">登录</el-menu-item>
        </el-menu>
        <el-input
            class="searcher"
            placeholder="搜索"
            v-model="data.searcher"
            maxlength="38"
            clearable>
          <template #prefix>
            <el-icon class="el-input__icon">
              <search/>
            </el-icon>
          </template>
        </el-input>
      </div>
    </div>
    <!--登录弹窗-->
    <Dialog
        title="登录"
        v-model="dialog.login">
      <template #content>
        <el-form class="dialog-content" id="form-login" label-width="60px">

          <el-form-item label="用户名">
            <el-input name="username" v-model="data.login.username"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input name="password" type="password" v-model="data.login.username"></el-input>
          </el-form-item>

        </el-form>
      </template>
    </Dialog>
    <!--注册弹窗-->
    <Dialog title="注册" v-model="dialog.signup">
      <template #content>
        <el-form class="dialog-content" id="form-register" label-width="80px">
          <el-form-item label="用户名">
            <el-input name="username" v-model="data.signup.username"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input name="password" type="password" v-model="data.signup.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input
                name="password_confirmation"
                type="password"
                v-model="data.signup.password_confirmation"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
  </el-header>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";
import {Search} from "@element-plus/icons-vue";
import Dialog from "./Dialog.vue";

@Options({
  components: {Dialog, Search},
  props: {
    logo: {
      type: String,
      default: ""
    },
    title: {
      type: String,
      default: 'Vue.js'
    }
  }
})
export default class Navigator extends Vue {

  public data = {
    searcher: '',
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

  public dialog = {
    login: false,
    signup: false,
  }
}
</script>

<style scoped>
.root {
  width: 100%;
  background: #ffe7ba;
}

.navigator {
  height: 100%;
  color: #fa8c16;
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
}

/*left*/

.left {
  display: inline-flex;
  align-items: stretch;
}

.icon {
  width: 50px;
  height: 50px;
}

.logo {
  cursor: pointer;
  display: inline-flex;
  align-items: center;
}

.title {
  font-size: large;
  margin: 0 10px;
}

/*right*/
.right {
  display: inline-flex;
  flex-direction: row-reverse;
  flex-wrap: nowrap;
}

.searcher {
  width: 225px;
  margin: 11px;
  display: inline-flex;
  --el-input-bg-color: #ffd7a0;
  --el-input-placeholder-color: #fa8c16;
  --el-input-border-color: #ffd7a0;
  --el-input-focus-border-color: #fa8c16;
}

.dialog-content{
  width: 260px;
  margin: 0 auto;
}
</style>