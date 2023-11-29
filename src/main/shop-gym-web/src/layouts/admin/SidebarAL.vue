<template>
  <el-aside class="aside" :width="isCollapse ? '64px' : '210px'">
    <div class="headerLogo">
      <el-image class="logo" :src="require('@/assets/image/logo/logo.jpg')"></el-image>
      <!-- <el-image v-else class="logo" :src="require('@/assets/image/logo/logo2.jpg')"></el-image> -->
    </div>
    <el-menu
      class='el-menu-vertical-demo sidebar'
      background-color='rgb(48, 65, 86)'
      text-color='#fff'
      active-text-color="#ffd04b"
    >
      <el-menu-item 
        v-for="(r, i) in route" :key="i"
        @click="linkTo(r.path, r.name)" 
        :index="(i + 1).toString()" 
        :class="$route.path === '/admin/' + r.path ? 'isActive' : ''"
      >
        <i :class='r.meta.icon'></i>
        <span v-if="!isCollapse">{{ r.meta.title }}</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script lang='ts'>
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component({
  name: 'SidebarAL',
  components: {}
})
export default class extends Vue {
  @Prop() private isCollapse!: boolean;
  private route = (this.$router.options.routes as any).find((e: any) => e.path === '/admin').children;

  // private created() {
  //   console.log(this.route);
  // }

  private linkTo(path: string, name: string) {
    if(this.$route.path != '/admin/' + path) {
      this.$router.push({
        name: name
      })
    }
  }
}
</script>

<style lang='scss' scoped>
.aside{
  transition: width 0.28s;
  min-height: 100vh;
  background-color: rgb(48, 65, 86);

  .el-menu-item:hover {
    background-color: rgb(38, 52, 69) !important;
  }

  .sidebar {
    border: 0;

    .isActive {
      color: #ffd04b !important;
    }
  }

  .headerLogo {
    background-color: #2b2f3a;
    height: 46px !important;
    padding: 5px;
    text-align: center;

    .logo {
      height: 100%;
      border-radius: 4px;
    }
  }
}
</style>
  