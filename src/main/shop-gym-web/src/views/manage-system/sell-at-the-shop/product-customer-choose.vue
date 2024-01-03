<template>
  <el-table
    :key="tableKey"
    v-loading="isLoading"
    :data="listProductSelected"
    stripe
    v-if="listProductSelected.length > 0"
    style="border: 1px solid #DCDFE6; border-radius: 4px; border-bottom: 0;"
  >
    <el-table-column align="center" width="40">
      <template slot-scope="scope">
        <span>
          #{{ scope.$index + 1 }}
        </span>
      </template>
    </el-table-column>
    <el-table-column align="center" width="80">
      <template slot-scope="{row}">
        <span>
          <el-image 
            style="width: 50px; height: 50px"
            :src="row.productDetail.product.image" 
            :preview-src-list="[row.productDetail.product.image]">
          </el-image>
        </span>
      </template>
    </el-table-column>
    <el-table-column label="Tên sản phẩm" width="115">
      <template slot-scope="{row}">
        <span style="text-align: left;">
          {{ row.productDetail.product.name }}
        </span>
      </template>
    </el-table-column>
    <el-table-column label="Size" align="center">
      <template slot-scope="{row}">
        <span>{{ row.productDetail.size.name }}</span>
      </template>
    </el-table-column>
    <el-table-column label="Màu" align="center">
      <template slot-scope="{row}">
        <span>{{ row.productDetail.color.name }}</span>
      </template>
    </el-table-column>
    <el-table-column label="Chất liệu" align="center">
      <template slot-scope="{row}">
        <span>{{ row.productDetail.material.name }}</span>
      </template>
    </el-table-column>
    <el-table-column label="SL" align="center" width="60">
      <template slot-scope="{row}">
        <div style="display: flex; align-items: center;">
          <!-- <el-button style="color: #8492a6; padding: 4px 3.77px;" @click="row.quantity--">-</el-button> -->
          <el-input class="input-quantity" v-model="row.quantity" style="padding: 0; margin-left: 4px; margin-right: 4px;"></el-input>
          <!-- <el-button style="color: #8492a6; padding: 4px 2px;" @click="row.quantity++">+</el-button> -->
          <!-- <div style="margin-left: 5px;">
            <div style="line-height: 1.5; cursor: pointer; color: #8492a6; user-select: none !important;" @click="row.quantity++">▲</div>
            <div style="line-height: 1.5; cursor: pointer; color: #8492a6; user-select: none !important;" @click="row.quantity--">▼</div>
          </div> -->
        </div>
      </template>
    </el-table-column>
    <el-table-column label="Giá" align="center" width="150">
      <template slot-scope="{row}">
        <span>
          {{ FormatCurrenyVND(row.productDetail.price * row.quantity) }}
        </span>
      </template>
    </el-table-column>
    <el-table-column label="" align="center">
      <template slot-scope="scope">
        <el-button style="padding-left: 10px; padding-right: 10px;" @click="removeProduct(scope.$index)">
          <i class="el-icon-delete"></i>
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { ResponseCode, Status } from '../../../enums/enums'
import { formatCurrenyVND } from '../../../utils/utils'

export default {
  props: {
    listProductSelected: {
      type: Array
    }
  },
  components: {},
  data() {
    return {
      FormatCurrenyVND: formatCurrenyVND,
      tableKey: 0,
      isLoading: false
    }
  },
  methods: {
    removeProduct(index) {
      this.$emit('remove-product', index)
    }
  },
  created() {
  }
}
</script>

<style>
.input-quantity .el-input__inner {
  text-align: center !important;
  padding: 0;
}
</style>
