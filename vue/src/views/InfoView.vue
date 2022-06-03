<template>
  <div class="root container">
    <div class="top-bar">
      <span class="username">{{ username }}</span>
    </div>
    <div class="header">
      <div class="icon-box">
        <el-avatar class="icon">
          <el-icon class="el-avatar--icon" :size="120">
            <embed id="avatar">
            <UserFilled/>
            <embed>
          </el-icon>
        </el-avatar>
        <el-upload
            class="icon-mask"
            action="http://localhost:8081/api/icon/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <el-icon :size="60" color="#1890ff">
            <Upload/>
          </el-icon>
        </el-upload>
      </div>
      <span style="font-size: 3ex;margin-left: 10px;margin-top: 5px;">ID:xxx</span>
    </div>
    <div class="content">
      <el-form label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('sex')">
              <template #label>
                <el-icon :size="20">
                  <Male/>
                </el-icon>
                <span>性别</span>
              </template>
              <span>{{ data.sex }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('height')">
              <template #label>
                <el-icon :size="20">
                  <CaretTop/>
                </el-icon>
                <span>身高</span>
              </template>
              <span>{{ data.height }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('phone')">
              <template #label>
                <el-icon :size="20">
                  <Phone/>
                </el-icon>
                <span>手机</span>
              </template>
              <span>{{ data.phone }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('email')">
              <template #label>
                <el-icon :size="20">
                  <Message/>
                </el-icon>
                <span>邮箱</span>
              </template>
              <span>{{ data.email }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('education')">
              <template #label>
                <el-icon :size="20">
                  <Medal/>
                </el-icon>
                <span>学历</span>
              </template>
              <span>{{ data.education }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('school')">
              <template #label>
                <el-icon :size="20">
                  <School/>
                </el-icon>
                <span>学校</span>
              </template>
              <span>{{ data.school }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item @click="edit('address')">
              <template #label>
                <el-icon :size="20">
                  <HomeFilled/>
                </el-icon>
                <span>住址</span>
              </template>
              <span>{{ data.address }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item @click="edit('skills')">
              <template #label>
                <el-icon :size="20">
                  <Basketball/>
                </el-icon>
                <span>技能</span>
              </template>
              <span>{{ data.skills }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="no-click">
          <template #label>
            <el-icon :size="20">
              <Comment/>
            </el-icon>
            <span>简介</span>
          </template>
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
import {ElMessage, UploadProps} from "element-plus";
import qs from "qs";

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
    if (this.editor.data == '') {
      ElMessage.error('信息不能为空');
      return;
    }
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

  private handleAvatarSuccess: UploadProps["onSuccess"] = (
      response,
      uploadFile
  ) => {
    let avatar = document.querySelector("#avatar") as HTMLEmbedElement
    avatar.src = URL.createObjectURL(uploadFile.raw);
  }

  private beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
    if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/png") {
      ElMessage.error("图片格式必须为jpg或png!");
      return false;
    } else if ((rawFile.size >> 20) > 2) {
      ElMessage.error("图片大小不能超过2MB!");
      return false;
    }
    return true;
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

.icon-box {
  transform: translateY(-50%);
  position: relative;
}

.icon, .icon-mask {
  left: 0;
  border: 5px solid var(--hover-bg-color);
  border-radius: 50%;
  position: absolute;
}

.icon-box, .icon, .icon-mask {
  width: 120px;
  height: 120px;
}

.icon-mask {
  font-size: 20px;
  background-color: var(--bg-color);
  opacity: 0.6;
  border-radius: 50%;
  display: none;
  justify-content: center;
  align-items: center;
}

.icon-box:hover .icon-mask {
  display: inline-flex;
}

.content {
  margin: 30px;
}

.content .el-icon {
  align-self: center;
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