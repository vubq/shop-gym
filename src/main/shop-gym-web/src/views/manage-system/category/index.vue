<template>
  <div class="container">
    <div style="display: flex; justify-content: space-between;">
      <h3>Quản lý danh mục sản phẩm</h3>
      <div style="display: flex; align-items: center; margin-left: 10px;">
        <el-button @click="showModalCreateCategory">
          <i class="el-icon-plus" />
        </el-button>
      </div>
    </div>
    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
      <div style="display: flex; align-items: center;">
        <el-radio v-model="listQuery.status" :label="Status.ACTIVE" @change="replaceSearch">Hoạt động</el-radio>
        <el-radio v-model="listQuery.status" :label="Status.IN_ACTIVE" @change="replaceSearch">Ngừng hoạt động</el-radio>
      </div>
      <el-form @submit.native.prevent>
        <el-form-item label-width="10rem" style="margin-bottom: 0;">
          <template slot="label">
            <span class="font-bold">Tìm kiếm: </span>
          </template>
          <el-input placeholder="" style="width: 200px;" class="filter-item" />
        </el-form-item>
      </el-form>
    </div>


    <el-table
      :key="tableKey"
      v-loading="isLoading"
      :data="listOfCategories"
      border
      :default-sort="sortDefault"
      @sort-change="sortTable"
      :header-cell-style="{ background: '#f5f7fa' }"
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
          <el-button @click="showCategory(row.id)">
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
      @pagination="replaceSearch"
    />

    <el-dialog title="Thêm mới danh mục" :visible.sync="showModal" width="30%">
      <el-form>
        <el-form-item label="Tên" label-width="8">
          <el-input autocomplete="off" v-model="category.name"></el-input>
        </el-form-item>
        <el-form-item label="Mô tả" label-width="8">
          <el-input autocomplete="off" v-model="category.description"></el-input>
        </el-form-item>
        <el-radio v-model="category.status" :label="Status.ACTIVE">Hoạt động</el-radio>
        <el-radio v-model="category.status" :label="Status.IN_ACTIVE">Ngừng hoạt động</el-radio>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showModal = false">Hủy</el-button>
        <el-button type="primary" v-if="!category.id">Thêm mới</el-button>
        <el-button type="primary" v-else>Cập nhật</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getListOfCategoriesByCriteria, getCategoryById } from '../../../api/category'
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
      isLoading: true,
      sortOrder: '',
      sortBy: '',
      sortDefault: {
        prop: '',
        order: ''
      },
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
      category: {
        id: '',
        name: '',
        description: '',
        status: Status.ACTIVE
      },
      listOfCategories: []
    }
  },
  methods: {
    reLoadTable() {
      this.getListOfCategories()
    },
    getListOfCategories() {
      this.isLoading = true
      getListOfCategoriesByCriteria(this.listQuery)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.listOfCategories = res.data.items
            this.total = res.data.totalRows
          }
        }).finally(() => (this.isLoading = false))
    },
    replaceSearch() {
      if (this.$route.query.pageSize && Number.parseInt(this.$route.query.pageSize) !== this.listQuery.perPage) {
        this.listQuery.currentPage = 1
      }
      this.$router.replace({
        name: 'product-management.category',
        query: {
          filter: this.listQuery.filter,
          currentPage: this.listQuery.currentPage.toString(),
          pageSize: this.listQuery.perPage.toString(),
          sortBy: this.listQuery.sortBy.toString(),
          sortDesc: this.listQuery.sortDesc.toString(),
          status: this.listQuery.status
        }
      }).catch((err) => err)
    },
    sortTable(sortChange) {
      this.listQuery.sortBy = sortChange.prop
      this.listQuery.sortDesc = sortChange.order === 'descending'
      this.replaceSearch()
    },
    showCategory(categoryId) {
      this.showModal = true
      getCategoryById(categoryId).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          this.category = res.data.data
        }
      })
    },
    showModalCreateCategory() {
      this.showModal = true
      this.category = {
        id: '',
        name: '',
        description: '',
        status: Status.ACTIVE
      }
    }
  },
  created() {
    this.listQuery.currentPage = this.$route.query.currentPage ? this.$route.query.currentPage : this.listQuery.currentPage
    this.listQuery.perPage = this.$route.query.pageSize ? this.$route.query.pageSize : this.listQuery.perPage
    this.listQuery.filter = this.$route.query.filter ? this.$route.query.filter : this.listQuery.filter
    this.listQuery.sortBy = this.$route.query.sortBy ? this.$route.query.sortBy : this.listQuery.sortBy
    if (this.$route.query.sortDesc) {
      this.listQuery.sortDesc = this.$route.query.sortDesc === 'true'
    }
    this.sortOrder = this.$route.query.sortDesc && this.$route.query.sortDesc === 'false' ? 'ascending' : 'descending'
    this.sortBy = this.$route.query.sortBy ? this.$route.query.sortBy : this.listQuery.sortBy
    this.sortDefault = {
      prop: this.sortBy,
      order: this.sortOrder
    }
    this.listQuery.status = this.$route.query.status ? this.$route.query.status : this.listQuery.status
    this.reLoadTable()
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
