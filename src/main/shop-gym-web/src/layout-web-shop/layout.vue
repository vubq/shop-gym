<template>
  <!-- <div class="app-wrapper"> -->
  <el-scrollbar class="app-wrapper">
    <navbar></navbar>
    <!-- <sidebar></sidebar> -->
    <div class="main-container">
      <!-- <tabs-view></tabs-view> -->
      <app-main></app-main>
    </div>
    <div style="background-color: #fff;">
      <el-row>
        <el-col :span="8" style="padding: 40px;">
          <el-image style="width: 30%;" src="../../../static/image/logo/logo-web-shop.jpg"></el-image>
        </el-col>
        <el-col :span="8" style="padding: 40px;">
          <div style="font-size: 16px;">CHÍNH SÁCH KHÁCH HÀNG</div>
          <div style="margin-top: 10px;">Chính sách khách hàng thân thiết</div>
          <div style="margin-top: 10px;">Chính sách đổi trả</div>
          <div style="margin-top: 10px;">Chính sách bảo hành</div>
          <div style="margin-top: 10px;">Câu hỏi thường gặp</div>
          <div style="margin-top: 10px;">Hướng dẫn mua hàng online</div>
          <div style="margin-top: 10px;">Hướng dẫn kiểm tra hạng thẻ thành viên</div>
        </el-col>
        <el-col :span="8" style="padding: 40px;">
          <div style="font-size: 16px;">CHÍNH SÁCH KHÁCH HÀNG</div>
          <div style="margin-top: 10px;">Chính sách khách hàng thân thiết</div>
          <div style="margin-top: 10px;">Chính sách đổi trả</div>
          <div style="margin-top: 10px;">Chính sách bảo hành</div>
          <div style="margin-top: 10px;">Câu hỏi thường gặp</div>
          <div style="margin-top: 10px;">Hướng dẫn mua hàng online</div>
          <div style="margin-top: 10px;">Hướng dẫn kiểm tra hạng thẻ thành viên</div>
        </el-col>
      </el-row>
    </div>
  </el-scrollbar>
  <!-- </div> -->
</template>
<script>
import Navbar from './navbar'
import Sidebar from './sidebar/sidebar'
import TabsView from './tabs-view'
import AppMain from './app-main'
export default {
  components: {
    Navbar,
    Sidebar,
    TabsView,
    AppMain
  }
}
</script>
<style scoped lang="stylus">
.app-wrapper
  position absolute
  width 100%
  height 100vh
  /deep/ .el-scrollbar__wrap
    overflow-x hidden
  .main-container

    padding-top 56px
    background-color #fefefe
</style>
