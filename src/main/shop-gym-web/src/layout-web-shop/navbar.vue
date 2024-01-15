<template>
  <div class="top-navbar">
    <el-menu
      mode="horizontal" 
      text-color="#fff"
      active-text-color="#fff">
      <div style="display: flex; align-items: center;">
        <router-link to="/" >
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
          <!-- Cart -->
          <el-popover
            placement="bottom-end"
            v-model="showCart"
            width="800"
          >
            <!--Content cart-->
            <div style="margin: 10px;">
              <div style="display: flex; justify-content: space-between; font-size: 16px;">
                <span>GIỎ HÀNG ({{ cart.length }} sản phẩm)</span>
                <div></div>
                <div @click="showCart = false">
                  <i class="el-icon-close" style="cursor: pointer;"></i>
                </div>
              </div>
              <el-divider></el-divider>
              <div style="overflow-y: scroll; max-height: calc(100vh - 400px);">
                <div v-for="(p, i) in productInCart" :key="p.id" :style="i === 0 ? 'display: flex; position: relative; margin-right: 60px;' : 'margin-top: 15px; display: flex; position: relative; margin-right: 60px;'">
                  <el-image style="width: 150px;" :src="p.product.image"></el-image>
                  <div style="margin-left: 10px;">
                    <span>{{ p.product.name }}</span>
                    <div style="display: flex; margin-top: 10px;">
                      <div>
                        <div style="font-weight: 100;">Kích cỡ:</div>
                        <div style="font-weight: 100; margin-top: 5px;">Màu sắc:</div>
                        <div style="font-weight: 100; margin-top: 5px;">Chất liệu:</div>
                        <div style="font-weight: 100; margin-top: 5px;">Số lượng:</div>
                        <div style="font-weight: 100; margin-top: 5px;">Tổng tiền:</div>
                      </div>
                      <div style="margin-left: 40px;">
                        <div style="font-weight: 100;">{{ p.size.name }}</div>
                        <div style="font-weight: 100; margin-top: 5px;">{{ p.color.name }}</div>
                        <div style="font-weight: 100; margin-top: 5px;">{{ p.material.name }}</div>
                        <div style="font-weight: 100; margin-top: 5px;">{{ genQuantityPurchased(p.id) }}</div>
                        <div style="font-weight: 100; margin-top: 5px; color: #ee4d2d;">{{ formatCurrenyVND_d(genTotalMoneyProduct(p.id, p.price)) }}</div>
                      </div>
                    </div>
                  </div>
                  <div style="position: absolute; top: 0; right: -50px; cursor: pointer;">
                    <i class="el-icon-close" @click="removeProduct(p.id)"></i>
                  </div>
                </div>
              </div>
              <el-divider></el-divider>
              <div>
                <div style="display: flex; justify-content: space-between;">
                  <div>
                    <div style="font-size: 16px;">Tổng tiền tạm tính:</div>
                    <div style="margin-top: 5px; font-size: 16px;">TỔNG HÓA ĐƠN:</div>
                  </div>
                  <div></div>
                  <div>
                    <div style="color: #ee4d2d; font-size: 16px;">{{ FormatCurrenyVND(genTotalMoney()) }}</div>
                    <div style="color: #ee4d2d; font-size: 16px; margin-top: 5px;">{{ FormatCurrenyVND(genTotalMoney()) }}</div>
                  </div>
                </div>
                <div style="margin-top: 20px; display: flex; justify-content: space-between;">
                  <span @click="showCart = false" style="cursor: pointer; font-size: 16px; padding: 10px 50px; background-color: #ee4d2d; color: #fff; border-radius: 2px;">TIẾP TỤC MUA SẮM</span>
                  <div></div>
                  <span @click="pay" style="cursor: pointer; font-size: 16px; padding: 10px 50px; background-color: #ee4d2d; color: #fff; border-radius: 2px;">ĐẶT HÀNG</span>
                </div>
              </div>
            </div>
            <!--Content cart-->

            <!-- Button add to cart -->
            <div slot="reference">
              <el-badge :value="cart.length" :max="99" class="item" style="margin-right: 7px;">
                <i class="el-icon-shopping-cart-full" style="background-color: #f0f2f5; padding: 5px; border-radius: 5px;"></i>
              </el-badge>
              Giỏ hàng
            </div>
            <!-- Button add to cart -->
          </el-popover>
          <!-- Cart -->
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
            <router-link class='inlineBlock' to="/user">
              <el-dropdown-item>
                Giỏ hàng
              </el-dropdown-item>
            </router-link>
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
import { getAllProductDetailByListId } from '../api/product-detail'
import { ResponseCode } from '../enums/enums'
import { formatCurrenyVND } from '../utils/utils'

export default {
  name: '',
  data() {
    return {
      searchProduct: '',
      name: this.$route.name,
      showCart: false,
      productInCart: [],
      FormatCurrenyVND: formatCurrenyVND
    }
  },
  watch: {
    cart: function() {
      this.getListProductDetail()
    }
  },
  components: {
    LangSelect,
    Screenfull,
    ChangeTheme
  },
  computed: {
    ...mapGetters([
      'allInfor',
      'cart'
    ])
  },
  methods: {
    formatCurrenyVND_d(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
    },
    ...mapActions({
      userLogout: 'logout',
      removeProductToCart: 'removeProductToCart'
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
    },
    genQuantityPurchased(productDetailId) {
      console.log(this.cart.find(e => e.productDetailId === productDetailId))
      if(this.cart.length > 0) {
        return this.cart.find(e => e.productDetailId === productDetailId).quantity
      } else {
        return 0
      }
    },
    genTotalMoneyProduct(productDetailId, price) {
      if(this.cart.length > 0) {
        return this.cart.find(e => e.productDetailId === productDetailId).quantity * price
      } else {
        return 0
      }
    },
    genTotalMoney() {
      if(this.cart.length > 0) {
        let total = 0
        this.productInCart.forEach(e => {
          total += e.price * this.cart.find(x => x.productDetailId === e.id).quantity
        })
        return total
      } else {
        return 0
      }
    },
    removeProduct(productDetailId) {
      this.removeProductToCart(productDetailId)
    },
    getListProductDetail() {
      getAllProductDetailByListId({
        listProductDetailId: this.cart.map(e => e.productDetailId)
      }).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.productInCart = res.data.data
        }
      })
    },
    pay() {
      this.$router.push({
        name: 'pay'
      })
    }
  },
  created() {
    if(this.cart.length > 0) {
      this.getListProductDetail()
    }
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
