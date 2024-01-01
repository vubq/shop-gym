<template>
  <div class="container">
    <div style="float: right;">
      <el-form @submit.native.prevent>
        <el-form-item
          label-width="10rem"
        >
          <template slot="label">
            <span class="font-bold">Tìm kiếm: </span>
          </template>
          <el-input
            placeholder=""
            style="width: 200px;"
            class="filter-item"
          />
        </el-form-item>
      </el-form>
    </div>

    <el-table
      :key="tableKey"
      v-loading="isLoading"
      :data="listOfProduct"
      border
      :default-sort="sortDefault"
      @sort-change="sortTable"
      :header-cell-style="{ background: '#f5f7fa' }"
    >
      <!-- <el-table-column
        label="Id"
        align="center"
      >
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column> -->
      <el-table-column align="center" width="50">
        <template slot-scope="scope">
          <span>
            {{ scope.$index + 1 }}
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="Ảnh"
        align="center"
      >
        <template slot-scope="{row}">
          <span>
            <el-image 
              style="width: 50px; height: 50px"
              :src="row.image" 
              :preview-src-list="[row.image]">
            </el-image>
          </span>
        </template>
      </el-table-column>
      <el-table-column
        label="Tên sản phẩm"
        align="center"
      >
        <template slot-scope="{row}">
          <span @click="openProductDetail(row.id)" class="link-type">{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Mô tả"
        align="center"
      >
        <template slot-scope="{row}">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Loại sản phẩm"
        align="center"
      >
        <template slot-scope="{row}">
          <span>{{ row.category.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Thương hiệu"
        align="center"
      >
        <template slot-scope="{row}">
          <span>{{ row.brand.name }}</span>
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
  </div>
</template>

<script>
import { getAllBySearchCriteria } from '../../../api/product'
import Pagination from '../../../components/pagination'

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
        sortDesc: true
      },
      listOfProduct: []
    }
  },
  computed: {
  },
  methods: {
    reLoadTable() {
      this.getList()
    },
    getList() {
      this.isLoading = true
      getAllBySearchCriteria(this.listQuery).then((res) => {
        if (res.data) {
          console.log(res)
          this.listOfProduct = res.data.items
          this.total = res.data.totalRows
        }
      }).finally(() => (this.isLoading = false))
    },
    replaceSearch() {
      if (this.$route.query.pageSize && Number.parseInt(this.$route.query.pageSize) !== this.listQuery.perPage) {
        this.listQuery.currentPage = 1
      }
      this.$router.replace({
        name: 'ADProductPage',
        query: {
          filter: this.listQuery.filter,
          currentPage: this.listQuery.currentPage.toString(),
          pageSize: this.listQuery.perPage.toString(),
          sortBy: this.listQuery.sortBy.toString(),
          sortDesc: this.listQuery.sortDesc.toString()
        }
      }).catch((err) => err)
    },
    sortTable(sortChange) {
      this.listQuery.sortBy = sortChange.prop
      this.listQuery.sortDesc = sortChange.order === 'descending'
      this.replaceSearch()
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

    this.reLoadTable()
  }
}
</script>
<style scoped lang="stylus">
.basic-form
  width 500px
.sys-mark-wrapper
  .input-two
    position absolute
    top 0
    right -110px
    width 100px
</style>
