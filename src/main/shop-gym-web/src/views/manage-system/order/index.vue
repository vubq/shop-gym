<template>
  <div class="container">
    <h3>Quản lý hóa đơn online</h3>

    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
      <div style="display: flex; align-items: center;">
        <el-checkbox-group v-model="orderStatus" @change="replaceQuery()">
          <el-checkbox label="WAIT_FOR_CONFIRMATION">Chờ xác nhận</el-checkbox>
          <el-checkbox label="PREPARING_GOODS">Chuẩn bị hàng</el-checkbox>
          <el-checkbox label="DELIVERING">Đang giao</el-checkbox>
          <el-checkbox label="DELIVERED">Đã giao</el-checkbox>
          <el-checkbox label="SUCCESS">Thành công</el-checkbox>
          <el-checkbox label="CANCELLED">Đã hủy</el-checkbox>
        </el-checkbox-group>
      </div>
      <el-form @submit.native.prevent>
        <el-form-item label-width="10rem" style="margin-bottom: 0;">
          <template slot="label">
            <span class="font-bold">Tìm kiếm: </span>
          </template>
          <el-input style="width: 200px;" class="filter-item" v-model="listQuery.filter" @keyup.enter.native="search" />
        </el-form-item>
      </el-form>
    </div>

    <el-table
      :key="tableKey"
      v-loading="isLoadingTable"
      :data="listOfOrders"
      :header-cell-style="{ background: '#f5f7fa' }"
      border
    >
      <el-table-column align="center" width="50">
        <template slot-scope="scope">
          <span>#{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Họ và tên" align="center">
        <template slot-scope="{row}">
          <span>{{ row.fullNameCustomer }}</span>
        </template>
      </el-table-column>

      <el-table-column label="SĐT" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phoneNumber }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Địa chỉ" align="center">
        <template slot-scope="{row}">
          <span>{{ row.address }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Ngày đặt hàng" align="center">
        <template slot-scope="{row}">
          <span>{{ moment(row.createdAt).format('DD-MM-YYYY HH:mm:ss') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Trạng thái" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.status === 'WAIT_FOR_CONFIRMATION'">Chờ xác nhận</el-tag>
          <el-tag type="success" v-if="row.status === 'SUCCESS'">Thành công</el-tag>
          <el-tag type="info" v-if="row.status === 'PREPARING_GOODS'">Chuẩn bị hàng</el-tag>
          <el-tag type="warning" v-if="row.status === 'DELIVERING'">Đang giao</el-tag>
          <el-tag type="success" v-if="row.status === 'DELIVERED'">Đã giao</el-tag>
          <el-tag type="danger" v-if="row.status === 'CANCELLED'">Đã hủy</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="" align="center">
        <template slot-scope="{row}">
          <el-button @click="showModalOrder = true; order=row">
            <i class="el-icon-view" />
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.currentPage"
      :limit.sync="listQuery.perPage"
      @pagination="replaceQuery"
    />

    <el-dialog v-if="order" title="Chi tiết đơn hàng" :visible.sync="showModalOrder" :close-on-click-modal="false" width="70%">
            
      <el-row>
        <el-col :span="12">
          <div>Họ và tên: {{ order.fullNameCustomer }}</div>
          <div style="margin-top: 10px;">Số điện thoại: {{ order.phoneNumber }}</div>
          <div style="margin-top: 10px;">Email: {{ order.email }}</div>
        </el-col>
        <el-col :span="12">
          <div>Ngày đặt hàng: {{ moment(new Date(order.createdAt)).format('DD-MM-YYYY HH:mm:ss') }}</div>
          <div style="margin-top: 10px;">Tổng tiền: {{ formatCurrenyVND(order.totalAmount) }}</div>
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
            <span>{{ formatCurrenyVND(row.productDetail.price * row.quantity) }}</span>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 20px; text-align: end;">
        <el-button @click="changeStatusOrder(order.id)">{{ genButton() }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getOrderDetailsByOrderId, getListOfOrdersByCriteria, updateOrder } from '../../../api/order'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'
import moment from 'moment'
import { formatCurrenyVND } from '../../../utils/utils'

export default {
  components: {
    Pagination
  },
  data() {
    return {
      tableKey: 0,
      total: 0,
      isLoadingTable: false,
      isLoadingModal: false,
      listQuery: {
        currentPage: 1,
        perPage: 10,
        filter: '',
        sortBy: '',
        sortDesc: true
      },
      moment: moment,
      formatCurrenyVND: formatCurrenyVND,
      Status: Status,
      showModalOrder: false,
      listOfOrders: [],
      order: null,
      orderDetails: [],
      orderStatus: []
    }
  },
  methods: {
    getListOfOrders() {
      this.isLoadingTable = true
      getListOfOrdersByCriteria(this.listQuery, {
        orderStatus: this.orderStatus
      })
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.listOfOrders = res.data.items
            this.total = res.data.totalRows
          }
        }).finally(() => (this.isLoadingTable = false))
    },
    reloadTable() {
      this.getListOfOrders()
    },
    replaceQuery() {
      this.reloadTable()
    },
    search() {
      this.listQuery.currentPage = 1
      this.replaceQuery()
    },
    genPrecent(precent) {
      if(this.orderDetails.length > 0) {
        let total = 0
        this.orderDetails.forEach(e => {
          total += e.productPrice * e.quantity
        })
        return total * precent / 100
      }
      return 0
    },
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = ''
          return
        }
        if (index === 6) {
          sums[index] = 'Tổng'
          return
        }
        if (index === 7) {
          let total = 0
          this.orderDetails.forEach(e => {
            total += e.productPrice * e.quantity
          })
          sums[index] = formatCurrenyVND(total)
        }
      })
      return sums
    },
    changeStatusOrder(id) {
      let action = ''
      if(this.order.status === 'WAIT_FOR_CONFIRMATION') {
        action = 'xacnhan'
      }
      if(this.order.status === 'PREPARING_GOODS') {
        action = 'dagui'
      }
      if(this.order.status === 'DELIVERING') {
        action = 'dagiao'
      }
      updateOrder({
        orderId: id,
        action: action
      }).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.showModalOrder = false
          this.reloadTable()
        }
      })
    },
    genButton() {
      let string = ''
      if(this.order.status === 'WAIT_FOR_CONFIRMATION') {
        string = 'Xác nhận'
      }
      if(this.order.status === 'PREPARING_GOODS') {
        string = 'Gửi hàng'
      }
      if(this.order.status === 'DELIVERING') {
        string = 'Đã giao'
      }
      return string
    }
  },
  created() {
    this.reloadTable()
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
  color: #666;
}
.title .title-value:before {
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
