<template>
  <div style="padding: 50px;">
    <el-row :gutter="50" style="background-color: #fff; border-radius: 2px;">
      <el-col :span="8" style="padding: 20px;">
        <span style="font-size: 16px;">THÔNG TIN TÀI KHOẢN</span>
        <el-divider class="user-divider"></el-divider>
      </el-col>
      <el-col :span="16" style="padding: 20px;">
        <div style="background-color: #fffbf8; border: 1px solid #f9ede5; padding: 10px;">
          <span style="font-size: 16px;">ĐƠN HÀNG CỦA BẠN</span>
          <el-tabs v-model="tabName" style="margin-top: 10px;" class="tab-order">
            <el-tab-pane label="Chờ xác nhận" name="waitForConfirmation">Chờ xác nhận</el-tab-pane>
            <el-tab-pane label="Đang giao" name="delivering">Đang giao</el-tab-pane>
            <el-tab-pane label="Đã giao" name="delivered">Đã giao</el-tab-pane>
          </el-tabs>
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
import { getAllOrderWaitForConfirmationByUserId, getAllOrderSuccessByUserId, getAllOrderCancelledByUserId } from '../../../api/order'

export default {
  components: {
  },
  data() {
    return {
      tabName: 'waitForConfirmation',
      listOrderWaitForConfirmation: [],
      listOrderSuccess: [],
      listOrderCancelled: []
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
</style>


