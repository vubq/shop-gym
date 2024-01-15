<template>
  <div style="padding: 50px;">
    <el-row :gutter="50" style="background-color: #fff; border-radius: 2px;">
      <el-col :span="24" style="padding: 20px;">
        <div style="background-color: #fffbf8; border: 1px solid #f9ede5; padding: 10px;">
          <span style="font-size: 16px;">ĐƠN HÀNG CỦA BẠN</span>

              <el-table
                :key="1"
                :data="listOrder"
                style="margin-top: 10px;"
              >
                <el-table-column align="center" width="50">
                  <template slot-scope="scope">
                    <span>#{{ scope.$index + 1 }}</span>
                  </template>
                </el-table-column>

                <el-table-column label="Ngày đặt hàng" align="center" width="200">
                  <template slot-scope="{row}">
                    <span>{{ moment(new Date(row.createdAt)).format('DD-MM-YYYY HH:mm:ss') }}</span>
                  </template>
                </el-table-column>

                <el-table-column label="Sản phẩm" align="center">
                  <template slot-scope="{row}">
                    <span v-html="genProductName(row.orderDetails)"></span>
                  </template>
                </el-table-column>

                <el-table-column label="Tổng tiền" align="center" width="150">
                  <template slot-scope="{row}">
                    <span>{{ formatCurrenyVND_d(row.totalAmount) }}</span>
                  </template>
                </el-table-column>

                <el-table-column label="Trạng thái" align="center" width="150">
                  <template slot-scope="{row}">
                    <el-tag v-if="row.status === 'WAIT_FOR_CONFIRMATION'">Chờ xác nhận</el-tag>
                    <el-tag type="success" v-if="row.status === 'SUCCESS'">Thành công</el-tag>
                    <el-tag type="info" v-if="row.status === 'PREPARING_GOODS'">Chuẩn bị hàng</el-tag>
                    <el-tag type="warning" v-if="row.status === 'DELIVERING'">Đang giao</el-tag>
                    <el-tag type="success" v-if="row.status === 'DELIVERED'">Đã giao</el-tag>
                    <el-tag type="danger" v-if="row.status === 'CANCELLED'">Đã hủy</el-tag>
                  </template>
                </el-table-column>


                <el-table-column label="" align="center" width="100">
                  <template slot-scope="{row}">
                    <span @click="order = row; showModalOrder = true" style="padding: 5px 10px; background-color: #ee4d2d; color: #fff; border-radius: 4px; cursor: pointer;">
                      Chi tiết
                    </span>
                  </template>
                </el-table-column>
              </el-table>

          <el-dialog v-if="order" title="Chi tiết đơn hàng" :visible.sync="showModalOrder" :close-on-click-modal="false" width="70%">
            
            <el-row>
              <el-col :span="12">
                <div>Họ và tên: {{ order.fullNameCustomer }}</div>
                <div style="margin-top: 10px;">Số điện thoại: {{ order.phoneNumber }}</div>
                <div style="margin-top: 10px;">Email: {{ order.email }}</div>
              </el-col>
              <el-col :span="12">
                <div>Ngày đặt hàng: {{ moment(new Date(order.createdAt)).format('DD-MM-YYYY HH:mm:ss') }}</div>
                <div style="margin-top: 10px;">Tổng tiền: {{ formatCurrenyVND_d(order.totalAmount) }}</div>
                <div style="margin-top: 10px;">
                  Trạng thái: 
                  <el-tag v-if="order.status === 'WAIT_FOR_CONFIRMATION'">Chờ xác nhận</el-tag>
                  <el-tag type="success" v-if="order.status === 'SUCCESS'">Thành công</el-tag>
                  <el-tag type="info" v-if="order.status === 'PREPARING_GOODS'">Chuẩn bị hàng</el-tag>
                  <el-tag type="warning" v-if="order.status === 'DELIVERING'">Đang giao</el-tag>
                  <el-tag type="success" v-if="order.status === 'DELIVERED'">Đã giao</el-tag>
                  <el-tag type="danger" v-if="order.status === 'CANCELLED'">Đã hủy</el-tag>
                </div>
              </el-col>
            </el-row>
            
            <el-table
              :key="1"
              :data="order.orderDetails"
              style="margin-top: 20px;"
            >
              <el-table-column align="center" width="50">
                <template slot-scope="scope">
                  <span>#{{ scope.$index + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="" align="center" >
                <template slot-scope="{row}">
                  <el-image 
                    style="width: 50px; height: 50px"
                    :src="row.productDetail.product.image">
                  </el-image>
                </template>
              </el-table-column>

              <el-table-column label="Sản phẩm" align="center">
                <template slot-scope="{row}">
                  <span>{{ row.productDetail.product.name }}</span>
                </template>
              </el-table-column>

              <el-table-column label="Kích thước" align="center">
                <template slot-scope="{row}">
                  <span>{{ row.productDetail.size.name }}</span>
                </template>
              </el-table-column>

              <el-table-column label="Màu sắc" align="center">
                <template slot-scope="{row}">
                  <span>{{ row.productDetail.color.name }}</span>
                </template>
              </el-table-column>

              <el-table-column label="Chất liệu" align="center">
                <template slot-scope="{row}">
                  <span>{{ row.productDetail.material.name }}</span>
                </template>
              </el-table-column>

              <el-table-column label="Số lượng" align="center">
                <template slot-scope="{row}">
                  <span>{{ row.quantity }}</span>
                </template>
              </el-table-column>

              <el-table-column label="Tổng tiền" align="center">
                <template slot-scope="{row}">
                  <span>{{ formatCurrenyVND_d(row.productDetail.price * row.quantity) }}</span>
                </template>
              </el-table-column>
            </el-table>

            <div v-if="order.status === 'DELIVERED'" style="margin-top: 20px; text-align: end;">
              <el-button @click="success(order.id)">Đã nhận được hàng</el-button>
            </div>
          </el-dialog>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ResponseCode, Status } from '../../../enums/enums'
import moment from 'moment'
import { mapGetters, mapActions } from 'vuex'
import axios from 'axios'
import { getAllOrderWaitForConfirmationByUserId, getAllOrderSuccessByUserId, getAllOrderCancelledByUserId, getAllOrderByUserId, updateOrder } from '../../../api/order'

export default {
  components: {
  },
  data() {
    return {
      tabName: 'waitForConfirmation',
      listOrderWaitForConfirmation: [],
      listOrderSuccess: [],
      listOrderCancelled: [],
      listOrder: [],
      moment: moment,
      showModalOrder: false,
      order: null
    }
  },
  computed: {
    ...mapGetters([
      'cart',
      'allInfor'
    ])
  },
  watch: {
  },
  methods: {
    ...mapActions([
      'clearCart'
    ]),
    formatCurrenyVND_d(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
    },
    genProductName(list) {
      let string = ''
      list.forEach((e, i) => {
        if(i === 0) {
          string += e.quantity + '<i class="el-icon-close" />' + e.productDetail.product.name
        } else {
          string += ', ' + e.quantity + '<i class="el-icon-close" />' + e.productDetail.product.name
        }
      })
      return string
    },
    success(id) {
      updateOrder({
        orderId: id,
        action: 'nhanhang'
      }).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.showModalOrder = false
          getAllOrderByUserId(this.allInfor.id)
            .then(res => {
              if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
                this.listOrder = res.data.data
              }
            })
        }
      })
    }
  },
  created() {
    getAllOrderWaitForConfirmationByUserId(this.allInfor.id)
      .then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.listOrderWaitForConfirmation = res.data.data
        }
      })
    getAllOrderSuccessByUserId(this.allInfor.id)
      .then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.listOrderSuccess = res.data.data
        }
      })
    getAllOrderCancelledByUserId(this.allInfor.id)
      .then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.listOrderCancelled = res.data.data
        }
      })
    getAllOrderByUserId(this.allInfor.id)
      .then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.listOrder = res.data.data
        }
      })
  }
}
</script>

<style scoped lang="css">
.user-divider {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>

<style>
.tab-order .el-tabs__item.is-active {
  color: #ee4d2d;
}
.tab-order .el-tabs__active-bar {
  color: #ee4d2d;
  background-color: #ee4d2d;
}
.tab-order .el-tabs__item:hover {
  color: #ee4d2d;
}
.odv1 .el-collapse-item__header {
  background-color: #fffbf8;
}
.odv1 .el-collapse-item__wrap {
  background-color: #fffbf8;
}
</style>



