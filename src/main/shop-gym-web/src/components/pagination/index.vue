<template>
  <div
    :class="{'hidden': hidden}"
    class="pagination-container"
  >
    <el-pagination
      :background="background"
      :current-page.sync="currentPage"
      :page-size.sync="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="pageSizes"
      :total="total"
      v-bind="$attrs"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    total: {
      type: Number,
      default: 0
    },
    page: {
      type: Number,
      default: 1
    },
    limit: {
      type: Number,
      default: 20
    },
    pageSizes: {
      type: Array[Number],
      default: () => [10, 20, 30, 50]
    },
    layout: {
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    },
    background: {
      type: Boolean,
      default: true
    },
    autoScroll: {
      type: Boolean,
      default: true
    },
    hidden: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    currentPage: {
      get() {
        return this.page
      },
      set(value) {
        this.$emit('update:page', value)
      }
    },
    pageSize: {
      get() {
        return this.limit
      },
      set(value) {
        this.$emit('update:limit', value)
      }
    }
  },
  methods: {
    handleSizeChange(value) {
      this.$emit('pagination', { page: this.currentPage, limit: value })
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    },
    handleCurrentChange(value) {
      this.$emit('pagination', { page: value, limit: this.pageSize })
      if (this.autoScroll) {
        scrollTo(0, 800)
      }
    }
  }
}
</script>

<style lang="css" scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
  border: 1px solid #EBEEF5;
  border-top: 0;
}

.pagination-container.hidden {
  display: none;
}
</style>