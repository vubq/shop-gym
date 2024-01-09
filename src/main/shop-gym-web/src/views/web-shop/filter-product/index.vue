<template>
  <div style="padding: 50px;">
    <el-row :gutter="10">
      <el-col :span="5">
        <div class="filter">
          <el-collapse v-model="activeNames">
            <el-collapse-item title="Từ khóa" name="0">
              <el-input
                v-model="listQuery.filter"
                placeholder="Nhập từ khóa cần tìm"
                suffix-icon="el-icon-search">
              </el-input>
            </el-collapse-item>
            <el-collapse-item title="Khoảng giá" name="1">
              <div style="display: flex; justify-content: space-between; align-items: center;">
                <el-input v-model="requestBody.priceApprox[0]" style="margin-right: 10px;"></el-input>
                ~
                <el-input v-model="requestBody.priceApprox[1]" style="margin-left: 10px;"></el-input>
              </div>
              <el-slider
                style="margin: 0 10px 0 10px;"
                v-model="requestBody.priceApprox"
                range
                :step="1000"
                :max="500000">
              </el-slider>
            </el-collapse-item>
            <el-collapse-item title="Danh mục" name="2">
              <el-checkbox-group v-model="requestBody.categories">
                <el-checkbox v-for="c in listOfCategories" :label="c.id" :key="c.id">{{ c.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Thương hiệu" name="3">
              <el-checkbox-group v-model="requestBody.brands">
                <el-checkbox v-for="b in listOfBrands" :label="b.id" :key="b.id">{{ b.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Kích cỡ" name="4">
              <el-checkbox-group v-model="requestBody.sizes">
                <el-checkbox v-for="s in listOfSizes" :label="s.id" :key="s.id">{{ s.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item title="Màu sắc" name="5">
              <el-checkbox-group v-model="requestBody.colors">
                <el-checkbox v-for="c in listOfColors" :label="c.id" :key="c.id">{{ c.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
            <el-collapse-item class="collapse-customer" title="Chất liệu" name="6">
              <el-checkbox-group v-model="requestBody.materials">
                <el-checkbox v-for="m in listOfMatetials" :label="m.id" :key="m.id">{{ m.name }}</el-checkbox>
              </el-checkbox-group>
            </el-collapse-item>
          </el-collapse>
          <div>
            <el-button style="width: 100%;" icon="el-icon-search" @click="reloadProduct()">Lọc</el-button>
          </div>
        </div>
      </el-col>
      <el-col :span="19" v-loading="isLoadingProduct">
        <div v-if="titleSearch" :style="titleSearch !== '' ? 'background-color: #fff; border-radius: 5px; padding: 10px; display: flex; align-items: center; margin-bottom: 5px;' : 'background-color: #fff; border-radius: 5px; padding: 10px; display: flex; align-items: center;'">
          <span v-if="titleSearch" style="font-size: 20px; font-weight: bold;">"{{ titleSearch }}"</span>
          <!-- <span style="margin-left: 5px;">({{ totalProductFilter }} kết quả)</span> -->
        </div>
        <!-- <div :style="titleSearch !== '' ? 'background-color: #fff; padding:15px; display: flex; align-items: center; border-top-right-radius: 5px; border-top-left-radius: 5px; margin-top: 5px;' : 'background-color: #fff; padding:15px; display: flex; align-items: center; border-top-right-radius: 5px; border-top-left-radius: 5px;'"> -->
        <!-- <div style="background-color: #fff; padding:15px; display: flex; align-items: center; border-top-right-radius: 5px; border-top-left-radius: 5px; margin-top: 5px;">
          <span style="font-weight: bold;">Sắp xếp theo</span>
          <el-button>S</el-button>
          <el-button>Giá tăng dần</el-button>
          <el-button>Giá giảm dần</el-button>
          <el-button>Lượt đánh giá</el-button>
        </div> -->
        <div>
          <el-row :gutter="5">
            <el-col :span="6" v-for="p in listOfProducts" :key="p.id">
              <div style="background-color: #fff; padding: 20px; cursor: pointer; margin-bottom: 5px;" class="card-product" @click="showProduct(p.id)">
                <el-image
                  class="image-product-web-shop"
                  style="width: 100%"
                  :src="p.image">
                </el-image>
                <div style="margin-top: 10px;">{{ p.name }}</div>
                <div style="margin-top: 10px; font-weight: 400; font-size: 18px; color: rgb(20, 53, 195);">{{ formatCurrenyVND(p.price) }}</div>
                <div style="margin-top: 10px; display: flex; justify-content: space-between;">
                  <el-rate
                    v-model="p.rate"
                    :colors="colors"
                    disabled>
                  </el-rate>
                  <div>
                    Đã bán {{ p.quantitySold }}
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :span="24" style="margin-top: 15px;">
              <el-pagination
                v-show="totalRows > 0"
                background
                layout="prev, pager, next"
                :current-page.sync="listQuery.currentPage"
                :total="totalRows"
                @current-change="reloadProduct()">
              </el-pagination>
            </el-col>
          </el-row>
        </div>
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
import { getListOfProductsByCriteriaWebShop } from '../../../api/product'

export default {
  components: {
  },
  data() {
    return {
      isLoadingProduct: false,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      listQuery: {
        currentPage: 1,
        perPage: 12,
        filter: this.$route.params.searchProduct ? this.$route.params.searchProduct : '',
        sortBy: '',
        sortDesc: true
      },
      requestBody: {
        categories: [],
        brands: [],
        sizes: [],
        colors: [],
        materials: [],
        priceApprox: [0, 500000]
      },
      activeNames: ['0', '1', '2', '3', '4', '5', '6'],
      listOfCategories: [],
      listOfSizes: [],
      listOfMatetials: [],
      listOfColors: [],
      listOfBrands: [],
      listOfProducts: [],
      titleSearch: '',
      totalProductFilter: 0,
      totalRows: 0
    }
  },
  methods: {
    getListOfProducts() {
      this.isLoadingProduct = true
      getListOfProductsByCriteriaWebShop(this.listQuery, this.requestBody)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.listOfProducts = res.data.items
            this.totalProductFilter = res.data.totalRows
            this.totalRows = res.data.totalRows
            this.titleSearch = this.listQuery.filter.trim()
          }
        }).finally(() => (this.isLoadingProduct = false))
    },
    reloadProduct() {
      this.getListOfProducts()
    },
    formatCurrenyVND(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
    },
    showProduct(productId) {
      this.$router.push({
        name: 'product',
        params: {
          id: productId
        }
      })
    }
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
    this.reloadProduct()
    console.log(this.$route)
  }
}
</script>

<style scoped lang="css">
.card-product {
  border: 1px solid #fff;
}
.card-product:hover {
  border-color: rgb(20, 53, 195);
}
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
.image-product-web-shop .el-image__inner {
  -webkit-transform: scale(1);
	transform: scale(1);
	-webkit-transition: .3s ease-in-out;
	transition: .3s ease-in-out;
}
.image-product-web-shop:hover .el-image__inner {
  -webkit-transform: scale(1.3);
	transform: scale(1.3);
}
</style>
