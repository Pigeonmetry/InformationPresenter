<template>
  <div class="root container">
    <div class="top-bar">
      <el-button class="username" text round @click="edit('username')">{{ data.username }}</el-button>
    </div>
    <div class="header">
      <div class="icon-box">
        <el-avatar class="icon" :key="avatar" :src="avatar" @error="handleAvatarLoadError">
          <el-icon class="el-avatar--icon" :size="120">
            <UserFilled/>
          </el-icon>
        </el-avatar>
        <el-upload
            class="icon-mask"
            name="Img"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :on-error="handleAvatarError"
            :before-upload="beforeAvatarUpload"
        >
          <el-icon :size="60" color="#1890ffdc">
            <Upload/>
          </el-icon>
        </el-upload>
      </div>
      <span style="font-size: 3ex;margin-left: 12px;margin-top: 5px;">ID:xxxxx</span>
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
            <el-form-item class="no-click">
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
          <editor
              id="rich-editor"
              v-model="data.text"
              api-key="hjuzlcxdwdlr1z0enadsllgmsjldgww9y2lqf7cc6cqz8wa6"
              :init="richOptions"
          />
        </el-form-item>
        <el-button type="primary" ::disabled="!changed" @click="submitUpdate" round>保存修改</el-button>
      </el-form>
    </div>
    <!-- 一般信息修改弹窗 -->
    <Dialog
        v-model="editor.visible"
        :title="editor.title"
        @submit="commit">
      <template #content>
        <el-input maxlength="28"  v-model="editor.data"/>
      </template>
    </Dialog>
  </div>
</template>
<script lang="ts">

import {Options, Vue} from "vue-class-component";
import store from "../store";
import Request from "../api/Request";
import {ElMessage, UploadProps} from "element-plus";
import qs from "qs";
import Editor from '@tinymce/tinymce-vue';

interface Response {
  status: string,
  data: any;
  msg: string;
}

@Options({
  components: {
    'editor': Editor,
  },
})
export default class InfoView extends Vue {

  mounted() {
    document.title = "个人信息";
    if (!store.state.userInfo) this.$router.replace("/login");
    else {
      Object.assign(this.data, store.state.userInfo);
      this.requestAvatar();
    }
  }

  private async requestAvatar() {
    Request.inst({
      url: 'avatar/get',
      method: 'post',
      headers: {
        'Accept': 'image/jpeg,image/png',
      },
      responseType: 'blob',
    }).then(res => {
      console.log(res);
      if (res.data.size > 0) {
        this.avatar = URL.createObjectURL(res.data);
      }else {
        this.avatar = '';
      }
    }).catch(err => {
      console.log(err)
      ElMessage({
        message: '获取头像失败',
        type: 'error',
        duration: 1000,
      });
    })
  }

  private static get baseUrl() {
    return Request.inst.defaults.baseURL;
  }

  public get uploadUrl() {
    return InfoView.baseUrl + "avatar/upload";
  }

  private data: any = {
    username: "XXX",
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

  private richOptions = {
    height: 500,
    width: 800,
    menubar: false,
    language: 'zh_CN',
    selector: '#rich-editor',
    inline: true,
    plugins: [
      'advlist autolink lists link image charmap print preview anchor',
      'searchreplace visualblocks code fullscreen',
      'insertdatetime media table paste code help wordcount',
    ],
    toolbar: 'undo redo | styleselect | bold italic forecolor backcolor | alignleft aligncenter alignright alignjustify | outdent indent | removeformat | help',
  }

  private editor = {
    visible: false,
    title: '修改信息',
    key: '',
    data: '',
  }

  private avatar: string = '';

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
    console.log(this.data);
    Request.inst({
      url: 'info/update',
      data: qs.stringify(this.data),
      method: 'post',
    }).then((res) => {
      console.log(res);
      let resp = res.data as Response;
      ElMessage({
        message: resp.msg,
        type: resp.status == "ok" ? 'success' : 'error',
        duration: 1000,
      })
      if (resp.status == "ok") {
        this.changed = false;
        store.commit('setUserInfo', this.data);
      }
    }).catch((err) => {
      console.log(err);
      ElMessage({
        message: err.message,
        type: 'error',
        duration: 1000,
      })
    });
  }

  public handleAvatarLoadError(event): boolean {
    console.log(event)
    ElMessage({
      message: '头像加载失败',
      type: 'error',
      duration: 1000,
    });
    return true;
  }

  private handleAvatarSuccess: UploadProps["onSuccess"] = (
      response,
      uploadFile
  ) => {
    this.avatar = URL.createObjectURL(uploadFile.raw);
    console.log(this.avatar)
    ElMessage({
      message: response.msg,
      type: response.status == "ok" ? 'success' : 'error',
      duration: 1000,
    })
  }

  private handleAvatarError: UploadProps["onError"] = (
      err
  ) => {
    console.log(err);
    ElMessage({
      message: '上传失败',
      type: 'error',
      duration: 1000,
    })
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
  background-color: #e6f7ff;
  margin: 30px auto;
  border-radius: 20px;
  box-shadow: 0 10px 20px lightgray;
  overflow: auto;
  border: 3px solid var(--bg-color);
}

.top-bar {
  height: 120px;
  background-image: linear-gradient(to left bottom, var(--bg-color), var(--hover-bg-color));
  display: flex;
  align-items: flex-end;
}

.username {
  font-size: 2em;
  margin-left: 152px;
  --el-fill-color-light: var(--bg-color);
  --text-color: #36cfc9;
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
  background-color: #bae7ff88;
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

#rich-editor {
  min-width: 100%;
  min-height: 300px;
  text-align: left;
}

#rich-editor:hover {
  background-color: var(--hover-bg-color);
  cursor: pointer;
}

</style>