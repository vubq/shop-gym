<template>
  <div class="container">
    <h3>Quản lý hóa đơn tại quầy</h3>

    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
      <div>
        <el-date-picker
          v-model="datePicker"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="~"
          start-placeholder="Từ ngày"
          end-placeholder="Tới ngày"
          :clearable="false"
          format="dd-MM-yyyy"
          value-format="yyyy-MM-dd"
          @change="changeDateFromAndTo()"
        />
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

      <el-table-column label="Tên khách hàng" align="center">
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

      <el-table-column label="Hoàn thành" align="center">
        <template slot-scope="{row}">
          <span>{{ Moment(row.completedAt).format('HH:mm:ss DD-MM-YYYY ') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Trạng thái" align="center">
        <template slot-scope="{row}">
          <el-tag type="success" v-if="row.status === 'SUCCESS'">Hoàn thành</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="" align="center">
        <template slot-scope="{row}">
          <el-button @click="showOrder(row.id)">
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

    <el-dialog title="Chi tiết hóa đơn" :visible.sync="showModal" :close-on-click-modal="false" width="50%">
      <div el-loading="isLoadingModal">
        <el-row v-if="order && orderDetails.length > 0">
          <el-col :span="12">
            <div>Khách hàng: {{ order.fullNameCustomer }}</div>
            <div style="margin-top: 20px;">SĐT: {{ order.phoneNumber }}</div>
            <div style="margin-top: 20px;">Địa chỉ: {{ order.address }}</div>
          </el-col>
          <el-col :span="12">
            <div>Mã HĐ: {{ order.id }}</div>
            <div style="margin-top: 20px;">Nhân viên: {{ order.createdBy.fullName }}</div>
            <div style="margin-top: 20px;">Ngày thanh toán: {{ Moment(order.completedAt).format('HH:mm:ss DD-MM-YYYY ') }}</div>
            <div style="margin-top: 20px;" v-if="order.voucher">Voucher: {{ order.voucher.code }} ({{ order.voucher.type === 'MONEY' ? '-' + FormatCurrenyVND(order.voucher.value) : '-' + FormatCurrenyVND(genPrecent(order.voucher.value)) }})</div>
            <div style="margin-top: 20px;">Tổng tiền: {{ FormatCurrenyVND(order.totalAmount) }}</div>
          </el-col>
        </el-row>

        <el-table
          :key="tableKey"
          v-loading="isLoadingModal"
          :data="orderDetails"
          :header-cell-style="{ background: '#f5f7fa' }"
          border
          show-summary
          :summary-method="getSummaries"
          style="margin-top: 20px;"
        >
          <el-table-column align="center" width="50">
            <template slot-scope="scope">
              <span>#{{ scope.$index + 1 }}</span>
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

          <el-table-column label="Màu sắc" align="center">
            <template slot-scope="{row}">
              <span>{{ row.productDetail.material.name }}</span>
            </template>
          </el-table-column>

          <el-table-column label="SL" align="center">
            <template slot-scope="{row}">
              <span>{{ row.quantity }}</span>
            </template>
          </el-table-column>

          <el-table-column label="Đơn giá" align="center">
            <template slot-scope="{row}">
              <span>{{ FormatCurrenyVND(row.productPrice) }}</span>
            </template>
          </el-table-column>

          <el-table-column label="Thành tiền" align="center">
            <template slot-scope="{row}">
              <span>{{ FormatCurrenyVND(row.productPrice * row.quantity) }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getOrderDetailsByOrderId, getListOfOrdersByCriteriaSellAtTheShop } from '../../../api/order'
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
        sortDesc: true,
        dateFrom: moment(new Date()).format('YYYY-MM-DD'),
        dateTo: moment(new Date()).format('YYYY-MM-DD')
      },
      Moment: moment,
      FormatCurrenyVND: formatCurrenyVND,
      Status: Status,
      showModal: false,
      listOfOrders: [],
      datePicker: [
        moment(new Date()).format('YYYY-MM-DD'),
        moment(new Date()).format('YYYY-MM-DD')
      ],
      order: null,
      orderDetails: []
    }
  },
  methods: {
    getListOfOrders() {
      this.isLoadingTable = true
      getListOfOrdersByCriteriaSellAtTheShop(this.listQuery)
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
    showOrder(orderId) {
      this.showModal = true
      this.isLoadingModal = true
      getOrderDetailsByOrderId(orderId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.order = res.data.data.order
            this.orderDetails = res.data.data.orderDetails
          }
        }).finally(() => (this.isLoadingModal = false))
    },
    changeDateFromAndTo() {
      this.listQuery.dateFrom = this.datePicker[0]
      this.listQuery.dateTo = this.datePicker[1]
      this.reloadTable()
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
