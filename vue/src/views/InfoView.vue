<template>
  <div class="root container">
    <div class="top-bar">
      <span class="username">{{ username }}</span>
    </div>
    <div class="header">
      <el-avatar class="icon"/>
      <span style="font-size: 3ex;margin-left: 10px;margin-top: 5px;">ID:xxx</span>
    </div>
    <div class="content">
      <el-form label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('sex')" label="性别">
              <span>{{ data.sex }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('height')" label="身高">
              <span>{{ data.height }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('phone')" label="手机">
              <span>{{ data.phone }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('email')" label="邮箱">
              <span>{{ data.email }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('education')" label="学历">
              <span>{{ data.education }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('school')" label="学校">
              <span>{{ data.school }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('address')" label="住址">
              <span>{{ data.address }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('skills')" label="技能">
              <span>{{ data.skills }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="no-click" label="个人简介">
          <el-input
              class="comment"
              v-model="data.text"
              @change="this.changed=true"
              type="textarea"
              maxlength="200"
              resize="none"
              rows="8"
              show-word-limit/>
        </el-form-item>
        <el-button type="primary" :disabled="!changed" @click="submitUpdate" round>保存修改</el-button>
      </el-form>
    </div>
    <!-- 一般信息修改弹窗 -->
    <Dialog
        v-model="editor.visible"
        :title="editor.title"
        @submit="commit">
      <template #content>
        <el-input v-model="editor.data"/>
      </template>
    </Dialog>
  </div>
</template>
<script lang="ts">

import {Vue} from "vue-class-component";
import store from "../store";
import Request from "../api/Request";
import {ElMessage} from "element-plus";

interface Response {
  status: string,
  data: any;
  msg: string;
}

export default class InfoView extends Vue {

  mounted() {
    // if(!store.state.userInfo) this.$router.replace("/login");
    // else this.data = store.state.userInfo;
  }

  public get username() {
    return store.state.userInfo?.username ?? '未知';
  }

  private data: any = {
    sex: '未知',
    height: '未知',
    phone: '未知',
    email: '未知',
    education: '未知',
    school: '未知',
    address: '未知',
    skills: '未知',
    text: '这个人很懒\n什么都没有写...',
  }

  private editor = {
    visible: false,
    title: '修改信息',
    key: '',
    data: '',
  }

  private changed = false;

  private edit(key: string) {
    this.editor.key = key;
    this.editor.data = this.data[key];
    this.editor.visible = true;
  }

  public commit() {
    this.data[this.editor.key] = this.editor.data;
    this.editor.visible = false;
    this.changed = true;
  }

  public submitUpdate() {
    Request.inst({
      url: 'info/update',
      data: qs.stringify(this.data),
      method: 'post',
    }).then((res) => {
      console.log(res);
      let resp = JSON.parse(res.data) as Response;
      ElMessage({
        message: resp.msg,
        type: resp.status == "success" ? 'success' : 'error',
        duration: 1000,
      })
      if (resp.status == "success") {
        this.changed = false;
        store.commit('setUserInfo', this.data);
      }
    }).catch((err) => {
      console.log(err);
      ElMessage({
        message: err.messages,
        type: 'error',
        duration: 1000,
      })
    });
  }

}
</script>

<style scoped>

::-webkit-scrollbar {
  display: none;
}

.root {
  margin: 30px auto;
  border-radius: 20px;
  box-shadow: 0 10px 20px gray;
  overflow: auto;
  border: 3px solid var(--bg-color);
}

.top-bar {
  height: 180px;
  background-color: var(--hover-bg-color);
  display: flex;
  align-items: flex-end;
}

.username {
  font-size: 2em;
  margin-left: 168px;
  margin-bottom: 10px;
}

.header {
  height: 80px;
  padding-left: 32px;
  display: flex;
  border: 6px solid var(--hover-bg-color);
  border-radius: 0 0 10px 10px;
}

.icon {
  width: 120px;
  height: 120px;
  transform: translateY(-50%);
}

.content {
  margin: 30px;
}

.content .el-form-item:not(.no-click):hover {
  background-color: var(--hover-bg-color);
  cursor: pointer;
}

.comment {
  --el-input-text-color: none;
  --el-input-border-color: var(--input-border-color);
  --el-input-hover-border-color: var(--input-border-focus-color);
}

</style>