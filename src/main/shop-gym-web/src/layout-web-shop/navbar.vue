<template>
  <div class="top-navbar">
    <el-menu
      mode="horizontal" 
      text-color="#fff"
      active-text-color="#fff">
      <router-link to="/home" style="display: flex;">
        <div style="padding: 10px 0px 10px 20px;">
          <el-image src="../../../static/image/logo/logo.jpg" style="width: 32.7px; border-radius: 4px;"></el-image>
        </div>
        <el-menu-item index="1" class="title-name" style="padding-left: 10px;">NoLan Shop</el-menu-item>
      </router-link>
      
      <!-- <change-theme class="theme-container"></change-theme>

      <el-tooltip effect="dark" :content="$t('navbar.screenfull')" placement="bottom">
        <screenfull class="screenfull"></screenfull>
      </el-tooltip>

      <div class="lang-select">
        <lang-select></lang-select>
      </div> -->

      <div class="avatar-container">
        <el-dropdown trigger="click">
          <div class="avatar-wrapper">
            <img class="user-avatar" :src="allInfor.avatar">
            <div class="username-wrapper">
              <span class="user-name">{{ allInfor.fullName }}</span>
              <i class="el-icon-caret-bottom"></i>
            </div>
          </div>
          <el-dropdown-menu class="user-dropdown" slot="dropdown">
            <router-link class='inlineBlock' to="/user/profile">
              <el-dropdown-item>
                {{$t('navbar.profile')}}
              </el-dropdown-item>
            </router-link>
            <a target='_blank' href="https://github.com/Neveryu/vue-cms">
              <el-dropdown-item>
                {{$t('navbar.github')}}
              </el-dropdown-item>
            </a>
            <el-dropdown-item @click.native="logout">
              <span style="display:block;">{{$t('navbar.logOut')}}</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>  
    </el-menu>
  </div>
</template>
<script>
import { confirm } from '@/decorator/confirm'
import { mapGetters, mapActions } from 'vuex'
import LangSelect from '@/components/lang-select'
import Screenfull from '@/components/screenfull'
import ChangeTheme from '@/components/theme'
export default {
  name: '',
  components: {
    LangSelect,
    Screenfull,
    ChangeTheme
  },
  computed: {
    ...mapGetters([
      'allInfor'
    ])
  },
  methods: {
    ...mapActions({
      userLogout: 'logout'
    }),
    logout() {
      const loading = this.$loading({
        lock: true
      })
      this.userLogout().then(() => {
        // 为了重新实例化vue-router对象 避免bug
        location.reload()
      }).catch(err => {
        console.log(err)
      }).finally(() => {
        loading.close()
      })
    }
  },
  created() {
    console.log(this.allInfo)
  }
}
</script>
<style lang="stylus" scoped>
.top-navbar
  position fixed
  width 100%
  z-index 10
  .el-menu
    border-bottom: none !important
    .lang-select
      position absolute
      top 18px
      right 150px
    .lang-select /deep/ .el-dropdown
      font-size 20px  
      color #fff
    .avatar-container
      position absolute
      top 15px
      right 40px
      .avatar-wrapper
        cursor pointer
    .avatar-container /deep/ .user-avatar
          width 30px
          height 30px
          border-radius 50%
          vertical-align middle
      .username-wrapper
        display inline-block
        height 30px
        line-height 30px
        color #fff

.top-navbar /deep/ .el-menu-item {
  font-size 20px
  &:hover {
    background-color: transparent
  }
  &:focus {
    background-color: transparent
  }
}
.top-navbar /deep/ .theme-container {
  position absolute
  top 15px
  right 225px
  color #fff
  font-size 24px
  cursor pointer
  // background-image linear-gradient(red, blue)
}
.top-navbar .screenfull {
  position: absolute
  top: 20px
  right: 190px
}

</style>
