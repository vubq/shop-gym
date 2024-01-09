<template>
  <div class="top-navbar">
    <el-menu
      mode="horizontal" 
      text-color="#fff"
      active-text-color="#fff">
      <div style="display: flex; align-items: center;">
        <router-link to="/home" >
          <div>
            <el-image src="../../../static/image/logo/logo-web-shop.jpg" style="width: 120px; border-radius: 4px;"></el-image>
          </div>
        </router-link>
        <el-menu-item index="1" style="padding-left: 20px; padding-right: 0; color: #000; font-size: 16px; font-weight: 500;">TRANG CHỦ</el-menu-item>
        <el-menu-item index="2" style="padding-left: 20px; padding-right: 0; color: #000; font-size: 16px; font-weight: 500;">GIỚI THIỆU</el-menu-item>
        <el-menu-item index="3" style="padding-left: 20px; padding-right: 0; color: #000; font-size: 16px; font-weight: 500;">LIÊN HỆ</el-menu-item>
        <el-menu-item v-if="name !== 'filter-product'" index="3" style="padding-left: 100px; padding-right: 0; color: #000; font-size: 16px;">
          <div style="display: flex;">
            <el-input class="search" style="display: block; width: 300px;" placeholder="Nhập sản phẩm cần tìm" v-model="searchProduct">
              <i slot="suffix" class="el-input__icon el-icon-search" @click="pushSearchProduct()"></i>
            </el-input>
          </div>
        </el-menu-item>
      </div>
      
      <!-- <change-theme class="theme-container"></change-theme>

      <el-tooltip effect="dark" :content="$t('navbar.screenfull')" placement="bottom">
        <screenfull class="screenfull"></screenfull>
      </el-tooltip>

      <div class="lang-select">
        <lang-select></lang-select>
      </div> -->

      <div class="avatar-container">
        <div style="margin-right: 30px; cursor: pointer;">
          <el-badge :value="1" :max="99" class="item" style="margin-right: 15px;">
            <i class="el-icon-shopping-cart-full" style="background-color: #f0f2f5; padding: 5px; border-radius: 5px;"></i>
          </el-badge>
          Giỏ hàng
        </div>
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
  data() {
    return {
      searchProduct: '',
      name: this.$route.name
    }
  },
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
    },
    pushSearchProduct() {
      this.$router.push({
        name: 'filter-product',
        params: {
          searchProduct: this.searchProduct
        }
      })
    }
  },
  created() {
    console.log(this.allInfo)
  }
}
</script>

<style>
.search .el-input__inner {
  border: 0;
  background-color: #f0f2f5;
  border-radius: 5px;
}
</style>
<style lang="stylus" scoped>
.top-navbar
  position fixed
  width 100%
  z-index 10
  .el-menu
    box-shadow: 0 0.5rem 1rem 0 rgba(0, 0, 0, 0.1)
    border-bottom: none !important
    background-color: #fff
    padding: 0 50px 0 50px
    .lang-select
      position absolute
      top 18px
      right 150px
    .lang-select /deep/ .el-dropdown
      font-size 20px  
      color #fff
    .avatar-container
      display flex
      align-items center
      position absolute
      top 15px
      right 50px
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
        color #000

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
