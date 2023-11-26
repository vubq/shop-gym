<template>
  <el-container>
    <SidebarAL :isCollapse='isCollapse'></SidebarAL>
    <div :class="isCollapse && i > 0 ? 'container collapse' : 'container collapse2'">
      <HeaderAL @changeIsCollapse="changeIsCollapse()" :isCollapse='isCollapse'></HeaderAL>
      <el-main class="containerBody">
        <router-view></router-view>
      </el-main>
    </div>
  </el-container>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import HeaderAL from './HeaderAL.vue';
import SidebarAL from './SidebarAL.vue';

@Component({
  name: 'AdminLayout',
  components: {
    HeaderAL,
    SidebarAL
  }
})
export default class extends Vue {
  private isCollapse = false;
  private i = 0;

  private created() {
    console.log(this.$route)
    console.log(this.$router)
  }

  private changeIsCollapse() {
    this.isCollapse = !this.isCollapse;
    this.i++;
  }
}
</script>

<style lang='scss' scoped>
.container {
  width: 100%;
  transition: width 0.28s;

  .containerBody {
    background-color: #f0f2f5 !important;
    height: 100vh;
    padding: 76px 20px 20px 20px;
  }
}

.collapse {
  width: calc(100% - 64px) !important;
}

.collapse2 {
  width: calc(100% - 210px) !important;
}
</style>
  