<template>
  <div class="container">
    <div style="display: flex; justify-content: space-between;">
      <h3>Quản voucher</h3>
      <div style="display: flex; align-items: center; margin-left: 10px;">
        <el-button @click="showModalCreateVoucher">
          <i class="el-icon-plus" />
        </el-button>
      </div>
    </div>
    <div style="display: flex; justify-content: end; margin-bottom: 20px;">
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
      :data="listOfVouchers"
      :header-cell-style="{ background: '#f5f7fa' }"
      border
    >
      <el-table-column align="center" width="50">
        <template slot-scope="scope">
          <span>#{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Mã" align="center">
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Loại" align="center">
        <template slot-scope="{row}">
          <span>{{ row.type === 'MONEY' ? 'Tiền' : 'Phần trăm' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Giá trị" align="center">
        <template slot-scope="{row}">
          <span>{{ row.type === 'MONEY' ? FormatCurrenyVND(row.value) : row.value + '%' }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Bắt đầu" align="center">
        <template slot-scope="{row}">
          <span>{{ Moment(row.startDate).format('DD-MM-YYYY HH:mm:ss') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Kết thúc" align="center">
        <template slot-scope="{row}">
          <span>{{ Moment(row.endDate).format('DD-MM-YYYY HH:mm:ss') }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="Tạo bởi" align="center">
        <template slot-scope="{row}">
          <span>{{ row.value }}</span>
        </template>
      </el-table-column> -->

      <el-table-column label="" align="center">
        <template slot-scope="{row}">
          <el-button @click="showVoucher(row.id)">
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

    <el-dialog :title="voucher.id ? 'Thông tin voucher' : 'Thêm mới voucher'" :visible.sync="showModal" :close-on-click-modal="false" width="30%">
      <el-form v-loading="isLoadingModal">
        <el-form-item label="Mã" label-width="8">
          <el-input autocomplete="off" v-model="voucher.code"></el-input>
        </el-form-item>
        <el-form-item label="Hình thức giảm giá" label-width="8">
          <el-radio v-model="voucher.type" label="PERCENT">Phần trăm</el-radio>
          <el-radio v-model="voucher.type" label="MONEY">Tiền</el-radio>
        </el-form-item>
        <el-form-item label="Giá trị" label-width="8">
          <el-input autocomplete="off" v-model="voucher.value"></el-input>
        </el-form-item>
        <el-form-item label="Thời gian" label-width="8">
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
          />
        </el-form-item>
        <el-radio v-model="voucher.status" :label="Status.ACTIVE">Hoạt động</el-radio>
        <el-radio v-model="voucher.status" :label="Status.IN_ACTIVE">Ngừng hoạt động</el-radio>
        <div style="margin-top: 30px; display: flex; justify-content: end;">
          <el-button @click="showModal = false">Hủy</el-button>
          <el-button type="primary" v-if="!voucher.id" @click="create">Thêm mới</el-button>
          <el-button type="primary" v-else @click="update">Cập nhật</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getListOfVouchersByCriteria, getVoucherById, createVoucher, updateVoucher } from '../../../api/voucher'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'
import { mapGetters } from 'vuex'
import { formatCurrenyVND } from '../../../utils/utils'
import moment from 'moment'

export default {
  components: {
    Pagination
  },
  computed: {
    ...mapGetters([
      'allInfor'
    ])
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
      Status: Status,
      showModal: false,
      voucher: {
        id: '',
        code: '',
        type: 'MONEY',
        startDate: '',
        endDate: '',
        createdAt: new Date(),
        status: Status.ACTIVE,
        value: '',
        createdBy: ''
      },
      listOfVouchers: [],
      datePicker: [],
      Moment: moment,
      FormatCurrenyVND: formatCurrenyVND
    }
  },
  methods: {
    getListOfVouchers() {
      this.isLoadingTable = true
      getListOfVouchersByCriteria(this.listQuery)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.listOfVouchers = res.data.items
            this.total = res.data.totalRows
          }
        }).finally(() => (this.isLoadingTable = false))
    },
    reloadTable() {
      this.getListOfVouchers()
    },
    replaceQuery() {
      this.reloadTable()
    },
    search() {
      this.listQuery.currentPage = 1
      this.replaceQuery()
    },
    showVoucher(voucherId) {
      this.showModal = true
      this.isLoadingModal = true
      getVoucherById(voucherId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.voucher = res.data.data
          }
        }).finally(() => (this.isLoadingModal = false))
    },
    showModalCreateVoucher() {
      this.showModal = true
      this.size = {
        id: '',
        code: '',
        type: 'MONEY',
        start_date: '',
        end_date: '',
        createdAt: new Date(),
        status: Status.ACTIVE,
        value: '',
        createdBy: ''
      }
    },
    create() {
      this.isLoadingModal = true
      if(!this.size.id) {
        this.voucher.createdBy = this.allInfor.id
        this.voucher.startDate = new Date(this.datePicker[0] + ' 00:00:00.000')
        this.voucher.endDate = new Date(this.datePicker[1] + ' 23:59:59.999')
        createVoucher(this.voucher)
          .then(res => {
            if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
              this.isLoadingModal = false
              this.showModal = false
              this.reloadTable()
            }
          })
      }
    },
    update() {
      this.isLoadingModal = true
      if(this.voucher.id) {
        updateVoucher(this.voucher)
          .then(res => {
            if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
              this.isLoadingModal = false
              this.showModal = false
              this.reloadTable()
            }
          })
      }
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
