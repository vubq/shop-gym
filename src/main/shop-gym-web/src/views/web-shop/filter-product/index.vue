<template>
  <div style="padding: 50px 100px 50px 100px;">
    <el-row :gutter="20">
      <el-col :span="4">
        <div class="filter">
          <!-- <div style="font-weight: bold;">
            Bộ lọc sản phẩm
          </div> -->
          <el-collapse v-model="activeNames">
            <el-collapse-item title="Từ khóa" name="0">
              <el-input
                placeholder="Nhập từ khóa cần tìm"
                suffix-icon="el-icon-search"
                >
              </el-input>
            </el-collapse-item>
            <el-collapse-item title="Khoảng giá" name="1">
              <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-model="priceApprox[0]" style="margin-right: 10px;"></el-input>
                ~
                <el-input v-model="priceApprox[1]" style="margin-left: 10px;"></el-input>
              </div>
              <el-slider
                style="margin: 0 10px 0 10px;"
                v-model="priceApprox"
                range
                :step="1000"
                :max="100000">
              </el-slider>
            </el-collapse-item>
            <el-collapse-item title="Danh mục" name="2">
              <el-checkbox-group v-model="categories">
                <el-checkbox v-for="c in listOfCategories" :label="c.id" :key="c.id">{{ c.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Thương hiệu" name="3">
              <el-checkbox-group v-model="brands">
                <el-checkbox v-for="b in listOfBrands" :label="b.id" :key="b.id">{{ b.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Kích cỡ" name="4">
              <el-checkbox-group v-model="sizes">
                <el-checkbox v-for="s in listOfSizes" :label="s.id" :key="s.id">{{ s.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Màu sắc" name="5">
              <el-checkbox-group v-model="colors">
                <el-checkbox v-for="c in listOfColors" :label="c.id" :key="c.id">{{ c.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item class="collapse-customer" title="Chất liệu" name="6">
              <el-checkbox-group v-model="materials">
                <el-checkbox v-for="m in listOfMatetials" :label="m.id" :key="m.id">{{ m.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
          </el-collapse>
          <!-- <div>
            <el-button icon="el-icon-search" circle></el-button>
          </div> -->
        </div>
      </el-col>
      <el-col :span="20">
        
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'
import { getAllCategory } from '../../../api/category'
import { getAllBrand } from '../../../api/brand'
import { getAllSize } from '../../../api/size'
import { getAllColor } from '../../../api/color'
import { getAllMaterial } from '../../../api/material'

export default {
  components: {
  },
  data() {
    return {
      activeNames: ['0', '1', '2', '3', '4', '5', '6'],
      categories: [],
      brands: [],
      sizes: [],
      colors: [],
      materials: [],
      listOfCategories: [],
      listOfSizes: [],
      listOfMatetials: [],
      listOfColors: [],
      listOfBrands: [],
      priceApprox: [0, 100000]
    }
  },
  methods: {
  },
  created() {
    getAllCategory().then(res => {
      this.listOfCategories = res.data
    })
    getAllBrand().then(res => {
      this.listOfBrands = res.data
    })
    getAllSize().then(res => {
      this.listOfSizes = res.data
    })
    getAllColor().then(res => {
      this.listOfColors = res.data
    })
    getAllMaterial().then(res => {
      this.listOfMatetials = res.data
    })
  }
}
</script>

<style scoped lang="css">
.el-collapse {
  border-top: 0;
}
.filter {
  background-color: #fff;
  padding: 0 10px 10px 10px;
  border-radius: 5px;
}
</style>
<style>
.collapse-customer .el-collapse-item__wrap {
  border-top: 0;
  border-bottom: 0;
}
.collapse-customer .el-collapse-item__header {
  border-bottom: 0;
}
.el-collapse-item__header {
  font-weight: bold;
}
.el-collapse-item__content {
  padding-bottom: 15px;
}
</style>
