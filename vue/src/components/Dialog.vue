<template>
  <div class="dialog">
    <el-dialog
        :model-value="modelValue"
        :title="title"
        width="30%"
        :show-close="false"
        :close-on-click-modal="false"
        center
    >

      <slot class="slot1" name="content"/>

      <template #footer>
          <span class="dialog-footer">
            <el-button type="success" @click="onSubmit">确认</el-button>
            <el-button v-if="cancelable" type="danger" @click="this.$emit('update:modelValue',false)">取消</el-button>
          </span>
      </template>

    </el-dialog>
  </div>
</template>

<script lang="ts">
import {Options, Vue} from "vue-class-component";

@Options({
  props: {
    title: {
      type: String,
      required: true
    },
    modelValue: false,
    cancelable: {
      type: Boolean,
      default: true
    },
    // events
    onSubmit: {
      type: Function,
      default: () => {
      }
    },
  }
})
export default class Login extends Vue {
}
</script>

<style scoped>
.dialog {
  --el-bg-color: var(--bg-color);
  --el-border-radius-small: 3%;
}
</style>