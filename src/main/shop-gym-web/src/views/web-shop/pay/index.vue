<template>
  <div style="padding: 50px;">
    <el-row :gutter="50" style="background-color: #fff; border-radius: 2px;">
      <el-col :span="14" style="padding: 20px;">
        <span style="font-size: 16px;">THÔNG TIN ĐẶT HÀNG</span>
        <el-divider class="pay-divider"></el-divider>
        <el-row :gutter="20">
          <el-form label-width="">
            <el-col :span="12">
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Họ và tên
                  <span style="color: red;">*</span>
                </span>
                <el-input v-model="fullNameCustomer"></el-input>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Email
                  <span style="color: red;">*</span>
                </span>
                <el-input v-model="email"></el-input>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Quận/Huyện
                  <span style="color: red;">*</span>
                </span>
                <el-select v-model="districtCode" filterable style="width: 100%;">
                  <el-option
                    v-for="d in listDistrict"
                    :key="d.code"
                    :label="d.name"
                    :value="d.code">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Địa chỉ
                  <span style="color: red;">*</span>
                </span>
                <el-input v-model="address"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Số điện thoại
                  <span style="color: red;">*</span>
                </span>
                <el-input v-model="phoneNumber"></el-input>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Tỉnh/Thành phố
                  <span style="color: red;">*</span>
                </span>
                <el-select v-model="cityCode" filterable style="width: 100%;">
                  <el-option
                    v-for="c in listCity"
                    :key="c.code"
                    :label="c.name"
                    :value="c.code">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Phường/Xã
                  <span style="color: red;">*</span>
                </span>
                <el-select v-model="wardCode" filterable style="width: 100%;">
                  <el-option
                    v-for="w in listWard"
                    :key="w.code"
                    :label="w.name"
                    :value="w.code">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item style="margin-bottom: 10px;">
                <span slot="label">
                  Ghi chú
                </span>
                <el-input v-model="noteByCustomer"></el-input>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
      </el-col>
      <el-col :span="10" style="padding: 20px;">
        <div style="background-color: #fffbf8; border: 1px solid #f9ede5; padding: 10px;">
          <span style="font-size: 16px;">ĐƠN HÀNG CỦA BẠN</span>
          <el-divider class="pay-divider"></el-divider>
          <div>
            <el-row>
              <el-col :span="16">
                <div style="font-size: 16px;">Sản phẩm</div>
              </el-col>
              <el-col :span="8" style="text-align: end;">
                <div style="font-size: 16px;">Tổng tiền</div>
              </el-col>
              <div v-for="p in productInCart" :key="p.id">
                <el-col :span="16" style="margin-top: 10px;">
                  <div style="">
                    {{ genQuantityPurchased(p.id) }} 
                    <i class="el-icon-close" style="cursor: pointer;"></i>
                    {{ p.product.name }}
                  </div>
                </el-col>
                <el-col :span="8" style="text-align: end; margin-top: 10px;">
                  <div style="color: #ee4d2d;">{{ formatCurrenyVND_d(genTotalMoneyProduct(p.id, p.price)) }}</div>
                </el-col>
              </div>
            </el-row>
          </div>
          <el-divider class="pay-divider"></el-divider>
          <div>
            <div style="font-size: 16px; margin-bottom: 10px;">Mã giảm giá</div>
            <div>
              <el-row :gutter="20">
                <el-col :span="18">
                  <el-input></el-input>
                </el-col>
                <el-col :span="6">
                  <div style="cursor: pointer; background-color: #ee4d2d; color: #fff; border-radius: 2px; padding: auto; height: 36px; display: flex; align-items: center; justify-content: center;">ÁP DỤNG</div>
                </el-col>
              </el-row>
            </div>
          </div>
          <el-divider class="pay-divider"></el-divider>
          <div>
            <div style="font-size: 16px;">Hình thức thanh toán</div>
            <el-radio-group v-model="typePay" style="margin-top: 10px;">
              <el-radio :label="'PAYMENT_ON_DELIVERY'" class="pay-radio">Khi nhận hàng</el-radio>
              <el-radio :label="'PAYMENT_ONLINE'" class="pay-radio">Online</el-radio>
            </el-radio-group>
          </div>
          <el-divider class="pay-divider"></el-divider>
          <div>
            <el-row>
              <el-col :span="16">
                <div>Giá bán</div>
                <div style="margin-top: 10px;">Giảm giá</div>
                <div style="margin-top: 10px;">Tổng thanh toán</div>
              </el-col>
              <el-col :span="8" style="text-align: end;">
                <div style="font-size: 16px; color: #ee4d2d;">{{ formatCurrenyVND_d(genTotalMoney()) }}</div>
                <div style="font-size: 16px; color: #ee4d2d; margin-top: 10px;">{{ formatCurrenyVND_d(0) }}</div>
                <div style="font-size: 16px; color: #ee4d2d; margin-top: 10px;">{{ formatCurrenyVND_d(genTotalMoney()) }}</div>
              </el-col>
            </el-row>
            <div @click="payment" style="cursor: pointer; margin-top: 10px; background-color: #ee4d2d; color: #fff; border-radius: 2px; padding: auto; height: 36px; display: flex; align-items: center; justify-content: center;">
              THANH TOÁN
            </div>
          </div>
          <el-divider class="pay-divider"></el-divider>
          <div>
            <div>
              <i class="el-icon-phone-outline"></i>
              Hotline 1900 1000
            </div>
            <div style="margin-top: 10px;">
              <i class="el-icon-tickets"></i>
              Hướng dẫn đặt hàng
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getProductByIdWebShop } from '../../../api/product'
import { getAllProductDetailByListId } from '../../../api/product-detail'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'
import { createOrder } from '../../../api/order'
import moment from 'moment'
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'

export default {
  components: {
  },
  data() {
    return {
      listCity: [],
      listDistrict: [],
      listWard: [],
      cityCode: '',
      districtCode: '',
      wardCode: '',
      city: '',
      district: '',
      ward: '',
      productInCart: [],
      typePay: 'PAYMENT_ON_DELIVERY',
      fullNameCustomer: '',
      phoneNumber: '',
      address: '',
      email: '',
      voucherId: '',
      noteByCustomer: ''
    }
  },
  computed: {
    ...mapGetters([
      'cart',
      'allInfor'
    ])
  },
  watch: {
    cityCode: function() {
      axios.get('https://provinces.open-api.vn/api/p/' + this.cityCode + '?depth=2')
        .then(res => {
          if(res.data) {
            this.listDistrict = res.data.districts
          }
        })
      this.city = this.listCity.find(e => e.code === this.cityCode).name
    },
    districtCode: function() {
      axios.get('https://provinces.open-api.vn/api/d/' + this.districtCode + '?depth=2')
        .then(res => {
          if(res.data) {
            this.listWard = res.data.wards
          }
        })
      this.district = this.listDistrict.find(e => e.code === this.districtCode).name
    },
    wardCode: function() {
      this.ward = this.listWard.find(e => e.code === this.wardCode).name
    },
    cart: function() {
      this.getListProductDetail()
    }
  },
  methods: {
    ...mapActions([
      'clearCart'
    ]),
    formatCurrenyVND_d(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
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
    payment() {
      createOrder({
        city: this.city,
        district: this.district,
        ward: this.ward,
        paymentType: this.typePay,
        fullNameCustomer: this.fullNameCustomer,
        phoneNumber: this.phoneNumber,
        address: this.address,
        voucherId: this.voucherId,
        noteByCustomer: this.noteByCustomer,
        totalAmount: this.genTotalMoney(),
        createdBy: this.allInfor.id,
        orderDetails: this.cart,
        email: this.email
      }).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.clearCart()
        }
      })
    }
  },
  created() {
    axios.get('https://provinces.open-api.vn/api/')
      .then(res => {
        if(res.data) {
          this.listCity = res.data
        }
      })
    if(this.cart.length > 0) {
      this.getListProductDetail()
    }
  }
}
</script>

<style scoped lang="css">
.pay-divider {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>

<style>
.pay-radio .el-radio__input.is-checked+.el-radio__label {
  color: #ee4d2d;
}
.pay-radio .el-radio__input.is-checked .el-radio__inner {
  background-color: #ee4d2d;
  background: #ee4d2d;
  border-color: #ee4d2d;
}
.pay-radio .el-radio__inner:hover {
  border-color: #ee4d2d;
}
</style>


