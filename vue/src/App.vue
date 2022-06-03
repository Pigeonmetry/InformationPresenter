<script lang="ts" setup>

</script>

<template>
  <el-container class="root">
    <el-header class="header">
      <Navigator logo="src/assets/logo.jpeg" title="个人信息展示"/>
    </el-header>
    <router-view/>
  </el-container>
</template>

<script lang="ts">

import {Vue} from "vue-class-component";

export default class App extends Vue {
}

</script>

<style>

::-webkit-scrollbar {
  width: 5px;
}

::-webkit-scrollbar-track {
  background-color: var(--input-hover-bg-color);
}

::-webkit-scrollbar-thumb {
  background-color: #40a9ffcc;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.root{
  height: 100%;
}

.header {
  width: 100%;
  padding: 0;
}
</style>
