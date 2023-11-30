<template>
  <el-header :class="isCollapse ? 'nav collapse' : 'nav'">
    <i :class="isCollapse ? 'el-icon-s-unfold isCollapseIcon' : 'el-icon-s-fold isCollapseIcon'" @click="changeIsCollapse()"></i>
  </el-header>
</template>

<script lang='ts'>
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component({
  name: 'HeaderAL',
  components: {}
})
export default class extends Vue {
  @Prop() private isCollapse!: boolean;

  private changeIsCollapse() {
    this.$emit('changeIsCollapse');
  }
}
</script>

<style lang='scss' scoped>
.nav {
  height: 56px !important;
  overflow: hidden;
  position: fixed;
  width: calc(100% - 210px);
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  align-items: center;
  transition: width 0.28s;
  z-index: 9998;

  .isCollapseIcon {
    font-size: 1.5rem;
    cursor: pointer;
  }
}

.collapse {
  width: calc(100% - 64px) !important;
}
</style>
  