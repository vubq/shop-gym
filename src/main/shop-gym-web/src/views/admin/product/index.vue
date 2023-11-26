<template>
  <div class="container">
    <div class="float-right">
      <el-form @submit.native.prevent>
        <el-form-item
          label-width="10rem"
        >
          <template slot="label">
            <span class="font-bold">Search: </span>
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
      :data="list"
      border
      :default-sort="sortDefault"
      @sort-change="sortTable"
    >
      <!-- <el-table-column
        label="Id"
        align="center"
      >
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column> -->
      <el-table-column
        label="Ảnh"
        align="center"
      >
        <template slot-scope="{row}">
          <span>
            <el-image 
              style="width: 100px; height: 100px"
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
          <span>{{ row.name }}</span>
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

    <Pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.currentPage"
      :limit.sync="listQuery.perPage"
      @pagination="replaceSearch"
    />
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import { Utils } from '@/utils/utils'
import { getAllBySearchCriteria } from '@/services/product/service'
import Pagination from '@/components/pagination/index.vue';

@Component({
  name: 'ProductPage',
  components: {
    Pagination
  }
})
export default class extends Vue {
  private tableKey = 0;
  private total = 0;
  private isLoading = true;
  private sortOrder = '';
  private sortBy = '';
  private sortDefault = {
    prop: '',
    order: ''
  };
  private listQuery = {
    currentPage: 1,
    perPage: 10,
    importance: undefined,
    filter: '',
    type: undefined,
    sortBy: '',
    sortDesc: true
  };
  private list = [];

  private created() {
    this.listQuery.currentPage = this.$route.query.currentPage ? Number.parseInt(Utils.getQueryParam(this.$route.query.currentPage)) : this.listQuery.currentPage;
    this.listQuery.perPage = this.$route.query.pageSize ? Number.parseInt(Utils.getQueryParam(this.$route.query.pageSize)) : this.listQuery.perPage;
    this.listQuery.filter = this.$route.query.filter ? Utils.getQueryParam(this.$route.query.filter) : this.listQuery.filter;

    this.listQuery.sortBy = this.$route.query.sortBy ? Utils.getQueryParam(this.$route.query.sortBy) : this.listQuery.sortBy;
    if (this.$route.query.sortDesc) {
      this.listQuery.sortDesc = this.$route.query.sortDesc === 'true';
    }
    this.sortOrder = this.$route.query.sortDesc && this.$route.query.sortDesc === 'false' ? 'ascending' : 'descending';
    this.sortBy = this.$route.query.sortBy ? Utils.getQueryParam(this.$route.query.sortBy) : this.listQuery.sortBy;
    this.sortDefault = {
      prop: this.sortBy,
      order: this.sortOrder
    };

    // Reload Table
    this.reLoadTable();
  }

  private reLoadTable() {
    this.getList();
  }

  private getList() {
    this.isLoading = true
    getAllBySearchCriteria(this.listQuery).then((res: any) => {
      if (res.data) {
        console.log(res)
        this.list = res.data.items;
        this.total = res.data.totalRows;
      }
    }).finally(() => (this.isLoading = false))
  }

  private replaceSearch() {
    if (this.$route.query.pageSize && Number.parseInt(Utils.getQueryParam(this.$route.query.pageSize)) !== this.listQuery.perPage) {
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
  }

  private sortTable(sortChange: any) {
    this.listQuery.sortBy = sortChange.prop
    this.listQuery.sortDesc = sortChange.order === 'descending'
    this.replaceSearch()
  }
}
</script>

<style lang='scss' scoped>
.container {
  transition: width 0.28s;

  .float-right {
    float: right;
  }

  .font-bold {
    font-weight: bold;
  }
}
</style>