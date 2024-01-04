<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="16">
        <div>
          <el-select value="" filterable placeholder="Tìm kiếm sản phẩm (Tên SP size / màu / chất liệu)" style="width: 100%; height: 100% !important;">
            <el-option
              v-for="p in products"
              :key="p.id"
              :label="p.product.name + ' ' + p.size.name + ' / ' + p.color.name + ' / ' + p.material.name"
              :value="p.id"
              style="height: auto;">
              <div style="display: flex; margin: 10px 0; justify-content: space-between;" @click="addProduct(p)">
                <div style="display: flex;">
                  <el-image 
                    style="width: 50px; height: 50px"
                    :src="p.product.image">
                  </el-image>
                  <div style="margin-left: 20px;">
                    <div>{{ p.product.name }}</div>
                    <div style="font-size: 13px; line-height: 0.5; color: red;">{{ FormatCurrenyVND(p.price) }}</div>
                  </div>
                </div>
                <div style="color: #8492a6;">
                  <div>{{ p.size.name + ' / ' + p.color.name + ' / ' + p.material.name }}</div>
                  <div style="text-align: end;">SL: {{ p.quantity }}</div>
                </div>
              </div>
            </el-option>
          </el-select>
        </div>

        <div style="margin-top: 10px;">
          <!-- <ProductInCart 
            v-for="orderDetail in bill.orderDetails" 
            :key="orderDetail" 
            :orderDetail="orderDetail"
            style="margin-top: 20px;">
          </ProductInCart> -->
          <product-customer-choose :listProductSelected="bill.orderDetails" @remove-product="removeProduct"></product-customer-choose>
        </div>
      </el-col>
      <el-col :span="8">
        <div style="border: 1px solid #DCDFE6; border-radius: 4px;">
          <div class="title">
            <p class="title-value">Thông tin khách hàng</p>
          </div>

          <el-form label-position="right" label-width="" style="padding: 10px; padding-bottom: 0;" v-if="isInformationCustomer">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="Tên khách hàng">
                  <el-input v-model="bill.fullNameCustomer"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Số điện thoại">
                  <el-input v-model="bill.phoneNumber"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Địa chỉ">
                  <el-input v-model="bill.address"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div style="border: 1px solid #DCDFE6; border-radius: 4px; margin-top: 10px;">
          <div class="title">
            <p class="title-value">Thanh toán</p>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <span>Tổng tiền ({{ genTotalNumberProduct() }} sản phẩm): </span>
            <span>{{ FormatCurrenyVND(genTotalAmount()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <div style="display: flex; align-items: center;">Voucher: </div>
            <div style="display: flex; align-items: center;">
              <el-input class="input-payment" v-model="voucherString" @change="getVoucher"></el-input>
            </div>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <span>Giảm giá {{ voucherValue }}: </span>
            <span> - {{ FormatCurrenyVND(genReducedMoney()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <span>Khách cần thanh toán: </span>
            <span>{{ FormatCurrenyVND(moneyToBePaid()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <div style="display: flex; align-items: center;">Tiền khách trả: </div>
            <div style="display: flex; align-items: center;">
              <el-input class="input-payment" v-model="money" :disabled="moneyToBePaid() === 0"></el-input>&nbsp;VND
            </div>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between; padding: 5px 10px;">
            <span>Tiền trả lại khách: </span>
            <span>{{ FormatCurrenyVND(money - moneyToBePaid() > 0 ? money - moneyToBePaid() : 0) }}</span>
          </div>

          <div style="margin-top: 10px; padding: 5px 10px;">
            <span>Ghi chú: </span>
            <el-input class="input-payment note" v-model="note" type="textarea" :rows="1"></el-input>
          </div>

          <div style="margin-top: 10px; padding: 10px;">
            <el-button style="width: 100%;" type="primary" @click="payment">Thanh toán</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ResponseCode, Status } from '../../../enums/enums'
import { formatCurrenyVND } from '../../../utils/utils'
import { createInvoice } from '../../../api/order'
import { getVoucherByCode } from '../../../api/voucher'
import { getProductById } from '../../../api/product'
import { getAllProductDetailInOfStock } from '../../../api/product-detail'
import ProductCustomerChoose from './product-customer-choose.vue'
import { mapGetters } from 'vuex'

export default {
  props: {
    bill: {
      type: null
    },
    isLoading: {
      type: Boolean
    }
  },
  components: {
    ProductCustomerChoose
  },
  computed: {
    ...mapGetters([
      'allInfor'
    ])
  },
  data() {
    return {
      user: null,
      products: [],
      FormatCurrenyVND: formatCurrenyVND,
      isInformationCustomer: true,
      money: '',
      voucherString: '',
      voucher: null,
      note: '',
      voucherValue: ''
    }
  },
  methods: {
    moneyToBePaid() {
      return this.genTotalAmount() - this.genReducedMoney() > 0 ? this.genTotalAmount() - this.genReducedMoney() : 0
    },
    genReducedMoney() {
      if(this.voucher) {
        let money = 0
        if(this.voucher.type === 'PERCENT') {
          money = this.genTotalAmount() * (this.voucher.value / 100)
        }
        if(this.voucher.type === 'MONEY') {
          money = this.voucher.value
        }
        return money > 0 ? money : 0
      } else {
        return 0
      }
    },
    getVoucher() {
      getVoucherByCode(this.voucherString).then((res) => {
        if(res.data) {
          console.log(res.data)
          const startDate = new Date(res.data.startDate)
          const endDate = new Date(res.data.endDate)
          const nowDate = new Date()
          if(nowDate >= startDate && nowDate <= endDate) {
            this.voucher = res.data
            if(res.data.type === 'PERCENT') {
              this.voucherValue = '(' + res.data.value + '%' + ')'
            }
            if(res.data.type === 'MONEY') {
              this.voucherValue = '(' + formatCurrenyVND(res.data.value) + ')'
            }
          }
        }
      })
    },
    removeProduct(index) {
      this.bill.orderDetails = this.bill.orderDetails.filter((e, i) => i !== index)
    },
    addProduct(productDetail) {
      const index = this.bill.orderDetails.findIndex((e) => e.productDetail.id === productDetail.id)
      if(index >= 0) {
        this.bill.orderDetails = this.bill.orderDetails.map((e, i) => {
          if(i === index) {
            return {
              ...e,
              quantity: e.quantity + 1
            }
          } else {
            return e
          }
        })
      } else {
        this.bill.orderDetails.push({
          id: '',
          orderId: '',
          productDetailId: productDetail.id,
          quantity: 1,
          totalAmount: '',
          isVoucher: false,
          status: Status.ACTIVE,
          productDetail: productDetail
        })
      }
    },
    genTotalNumberProduct() {
      let total = 0
      this.bill.orderDetails.map((e) => (total += e.quantity))
      return total
    },
    genTotalAmount() {
      let total = 0
      this.bill.orderDetails.map((e) => (total += e.quantity * e.productDetail.price))
      return total
    },
    payment() {
      this.$confirm('', 'Xác nhận thanh toán', {
        confirmButtonText: 'Thanh toán',
        cancelButtonText: 'Hủy',
        type: 'warning'
      }).then(() => {
        this.$emit('update:isLoading', true)
        if(this.allInfor.id) {
          this.bill.createdBy = this.allInfor.id
          this.bill.type = 'AT_THE_SHOP'
          if(this.voucher) {
            this.bill.voucherId = this.voucher.id
          }
          createInvoice(this.bill).then((res) => {
            if(res.data && res.data.code === 0) {
              this.$emit('remove-tab', this.bill.id)
              this.$emit('update:isLoading', false)
              window.scrollTo(0, 0)
            }
          })
          this.$emit('update:isLoading', false)
        }
      }).catch(() => {
        console.log('a')
      })
    }
  },
  created() {
    getAllProductDetailInOfStock().then((res) => {
      if(res.data && res.data.code === 0) {
        this.products = res.data.data
      }
    })
  }
}
</script>

<style scoped lang="css">
.title {
  background: #dde3ef;
  padding: 10px 0;
}
.title .title-value {
  margin-left: 4px;
  text-indent: 4px;
}
.title .title-value::before {
  display: inline-block;
  content: '';
  width: 4px;
  height: 16px;
  background: purple;
  margin-right: 4px;
  border-radius: 4px;
  vertical-align: middle;
}
</style>
<style>
.input-payment .el-input__inner {
  padding: 0;
  border: 0;
  border-bottom: 1px solid #8492a6;
  border-radius: 0;
  height: 30px;
  text-align: end;
  font-size: inherit;
}
.note .el-textarea__inner {
  padding: 0;
  border: 0;
  border-bottom: 1px solid #8492a6;
  border-radius: 0;
  height: auto;
  text-align: start;
  font-size: inherit;
}
</style>
