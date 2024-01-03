<template>
  <div class="container">
    <div style="display: flex; justify-content: space-between;">
      <h3>Quản lý thương hiệu</h3>
      <div style="display: flex; align-items: center; margin-left: 10px;">
        <el-button @click="showModalCreateBrand">
          <i class="el-icon-plus" />
        </el-button>
      </div>
    </div>
    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
      <div style="display: flex; align-items: center;">
        <el-radio v-model="listQuery.status" :label="Status.ACTIVE" @change="replaceQuery">Hoạt động</el-radio>
        <el-radio v-model="listQuery.status" :label="Status.IN_ACTIVE" @change="replaceQuery">Ngừng hoạt động</el-radio>
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
      :data="listOfBrands"
      :header-cell-style="{ background: '#f5f7fa' }"
      border
    >
      <el-table-column align="center" width="50">
        <template slot-scope="scope">
          <span>#{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Tên" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Mô tả" align="center">
        <template slot-scope="{row}">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Trạng thái" align="center">
        <template slot-scope="{row}">
          <el-tag type="success" v-if="row.status === Status.ACTIVE">Hoạt động</el-tag>
          <el-tag type="danger" v-else>Ngừng hoạt động</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="" align="center">
        <template slot-scope="{row}">
          <el-button @click="showBrand(row.id)">
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

    <el-dialog :title="brand.id ? 'Thông tin thương hiệu' : 'Thêm mới thương hiệu'" :visible.sync="showModal" :close-on-click-modal="false" width="30%">
      <el-form v-loading="isLoadingModal">
        <el-form-item label="Tên" label-width="8">
          <el-input autocomplete="off" v-model="brand.name"></el-input>
        </el-form-item>
        <el-form-item label="Mô tả" label-width="8">
          <el-input autocomplete="off" v-model="brand.description"></el-input>
        </el-form-item>
        <el-radio v-model="brand.status" :label="Status.ACTIVE">Hoạt động</el-radio>
        <el-radio v-model="brand.status" :label="Status.IN_ACTIVE">Ngừng hoạt động</el-radio>
        <div style="margin-top: 30px; display: flex; justify-content: end;">
          <el-button @click="showModal = false">Hủy</el-button>
          <el-button type="primary" v-if="!brand.id" @click="create">Thêm mới</el-button>
          <el-button type="primary" v-else @click="update">Cập nhật</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getListOfBrandsByCriteria, getBrandById, createBrand, updateBrand } from '../../../api/brand'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'

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
        status: Status.ACTIVE
      },
      Status: Status,
      showModal: false,
      brand: {
        id: '',
        name: '',
        description: '',
        createdAt: new Date(),
        status: Status.ACTIVE
      },
      listOfBrands: []
    }
  },
  methods: {
    getListOfBrands() {
      this.isLoadingTable = true
      getListOfBrandsByCriteria(this.listQuery)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.listOfBrands = res.data.items
            this.total = res.data.totalRows
          }
        }).finally(() => (this.isLoadingTable = false))
    },
    reloadTable() {
      this.getListOfBrands()
    },
    replaceQuery() {
      this.reloadTable()
    },
    search() {
      this.listQuery.currentPage = 1
      this.replaceQuery()
    },
    showBrand(brandId) {
      this.showModal = true
      this.isLoadingModal = true
      getBrandById(brandId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.brand = res.data.data
          }
        }).finally(() => (this.isLoadingModal = false))
    },
    showModalCreateBrand() {
      this.showModal = true
      this.brand = {
        id: '',
        name: '',
        description: '',
        status: Status.ACTIVE
      }
    },
    create() {
      this.isLoadingModal = true
      if(!this.brand.id) {
        createBrand(this.brand)
          .then(res => {
            if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
              this.isLoadingModal = false
              this.listQuery.status = res.data.data.status
              this.showModal = false
              this.reloadTable()
            }
          })
      }
    },
    update() {
      this.isLoadingModal = true
      if(this.brand.id) {
        updateBrand(this.brand)
          .then(res => {
            if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
              this.isLoadingModal = false
              this.listQuery.status = res.data.data.status
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
