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
                    <div style="font-size: 13px; line-height: 0.5; color: red;">{{ Utils.formatCurrenyVND(p.price) }}</div>
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
          <ProductsCustomersChoose :listProductSelected="bill.orderDetails" @remove-product="removeProduct"></ProductsCustomersChoose>
        </div>
      </el-col>
      <el-col :span="8">
        <div style="border: 1px solid #DCDFE6; border-radius: 4px; padding: 10px;">
          <span style="cursor: pointer; user-select: none;">
            Thông tin khách hàng
            <!-- <i :class="`${ !isInformationCustomer ? 'el-icon-arrow-down' : 'el-icon-arrow-up' }`"></i> -->
          </span>

          <el-form label-position="right" label-width="" style="margin-top: 10px;" v-if="isInformationCustomer">
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

        <div style="border: 1px solid #DCDFE6; border-radius: 4px; padding: 10px; margin-top: 10px;">
          <span>Thanh toán</span>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Tổng tiền ({{ genTotalNumberProduct() }} sản phẩm): </span>
            <span>{{ Utils.formatCurrenyVND(genTotalAmount()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Voucher: </span>
            <div>
              <el-input class="input-payment" v-model="voucherString" @change="getVoucher"></el-input>
            </div>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Giảm giá {{ voucherValue }}: </span>
            <span> - {{ Utils.formatCurrenyVND(genReducedMoney()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Khách cần thanh toán: </span>
            <span>{{ Utils.formatCurrenyVND(moneyToBePaid()) }}</span>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Tiền khách trả: </span>
            <div>
              <el-input class="input-payment" v-model="money" :disabled="moneyToBePaid() === 0"></el-input>
            </div>
          </div>

          <div style="margin-top: 10px; display: flex; justify-content: space-between;">
            <span>Tiền trả lại khách: </span>
            <span>{{ Utils.formatCurrenyVND(money - moneyToBePaid() > 0 ? money - moneyToBePaid() : 0) }}</span>
          </div>

          <div style="margin-top: 10px;">
            <span>Ghi chú: </span>
            <el-input class="input-payment note" v-model="note" type="textarea" :rows="1"></el-input>
          </div>

          <div style="margin-top: 10px;">
            <el-button class="button-payment" style="width: 100%; background-color: #8492a6; border-color: #8492a6;" type="success" @click="payment">Thanh toán</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script lang='ts'>
import { Component, Vue, Prop } from 'vue-property-decorator'
import ProductInCart from './product-in-cart/index.vue'
import ProductsCustomersChoose from './products-customers-choose/index.vue'
import { getAllProductInOfStock } from '@/services/product/ProductService'
import { OrderDetailModel } from '@/models/OrderDetailModel'
import { getProductById } from '@/services/product/ProductService'
import { getAllProductDetailInOfStock } from '@/services/product-detail/ProductDetailService'
import { Utils } from '@/utils/utils'
import { createInvoice } from '@/services/order/OrderService'
import { getVoucherByCode } from '@/services/voucher/VoucherService'

@Component({
  name: 'Bill',
  components: {
    ProductInCart,
    ProductsCustomersChoose
  }
})
export default class extends Vue {
  @Prop() private bill: any;
  @Prop() private isLoading: any;
  private user: any;
  private products: any = [];
  private Utils = Utils;
  private isInformationCustomer = true;
  private money = '';
  private voucherString = '';
  private voucher: any;
  private note = '';
  private voucherValue = '';

  private created() {
    this.user = JSON.parse(localStorage.getItem('user') as any);

    // getAllProductInOfStock().then((res: any) => {
    //   this.product = res.data.data;
    // })

    // console.log(this.bill)
    getAllProductDetailInOfStock().then((res: any) => {
      if(res.data && res.data.code === 0) {
        this.products = res.data.data;
      }
    })
  }

  private moneyToBePaid() {
    return this.genTotalAmount() - this.genReducedMoney() > 0 ? this.genTotalAmount() - this.genReducedMoney() : 0;
  }

  private genReducedMoney() {
    if(this.voucher) {
      let money = 0;
      if(this.voucher.type === 'PERCENT') {
        money = this.genTotalAmount() * (this.voucher.value / 100);
      }
      if(this.voucher.type === 'MONEY') {
        money = this.voucher.value;
      }
      return money > 0 ? money : 0;
    } else {
      return 0;
    }
  }

  private getVoucher() {
    getVoucherByCode(this.voucherString).then((res: any) => {
      if(res.data) {
        console.log(res.data)
        const startDate = new Date(res.data.startDate);
        const endDate = new Date(res.data.endDate);
        const nowDate = new Date();
        if(nowDate >= startDate && nowDate <= endDate) {
          this.voucher = res.data;
          if(res.data.type === 'PERCENT') {
            this.voucherValue = '(' + res.data.value + '%' + ')';
          }
          if(res.data.type === 'MONEY') {
            this.voucherValue = '(' + Utils.formatCurrenyVND(res.data.value) + ')';
          }
        }
      }
    })
  }

  private removeProduct(index: number) {
    this.bill.orderDetails = this.bill.orderDetails.filter((e: any, i: number) => i !== index);
  }

  private addProduct(productDetail: any) {
    const index = this.bill.orderDetails.findIndex((e: any) => e.productDetail.id === productDetail.id);
    if(index >= 0) {
      this.bill.orderDetails = this.bill.orderDetails.map((e: any, i: number) => {
        if(i === index) {
          return {
            ...e,
            quantity: e.quantity + 1
          }
        } else {
          return e;
        }
      })
    } else {
      this.bill.orderDetails.push({
        ... new OrderDetailModel(),
        productDetail: productDetail,
        productDetailId: productDetail.id,
        quantity: 1
      })
    }
  }

  private genTotalNumberProduct() {
    let total = 0;
    this.bill.orderDetails.map((e: any) => total += e.quantity);
    return total;
  }

  private genTotalAmount() {
    let total = 0;
    this.bill.orderDetails.map((e: any) => total += e.quantity * e.productDetail.price);
    return total;
  }

  private payment() {
    this.$confirm('', 'Xác nhận thanh toán', {
      confirmButtonText: 'Thanh toán',
      cancelButtonText: 'Hủy',
      type: 'warning'
    }).then(() => {
      this.isLoading = true;
      const user = JSON.parse(localStorage.getItem('user') as any);
      if(user) {
        this.bill.createdBy = user.id;
        this.bill.type = 'ONLINE';
        if(this.voucher) {
          this.bill.voucherId = this.voucher.id;
        }
        createInvoice(this.bill).then((res: any) => {
          if(res.data && res.data.code === 0) {
            console.log(res.data.data)
          }
        }).finally(() => this.isLoading = false);
        console.log(this.bill)
      }
    }).catch(() => {
      console.log('a')
    });
  }
  
}
</script>

<style lang='scss' scoped>
.button-payment:hover {
  opacity: 0.8;
}
</style>
<style>
.input-payment .el-input__inner {
  padding: 0;
  border: 0;
  border-bottom: 1px solid #8492a6;
  border-radius: 0;
  height: 100%;
  text-align: end;
  font-size: 16px;
}
.note .el-textarea__inner {
  padding: 0;
  border: 0;
  border-bottom: 1px solid #8492a6;
  border-radius: 0;
  height: auto;
  text-align: start;
  font-size: 16px;
}
</style>