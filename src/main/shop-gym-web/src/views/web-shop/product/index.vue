<template>
  <div style="padding: 50px;">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-row style="background-color: #fff; border-radius: 2px;">
          <el-col :span="10">
            <div style="padding: 20px 40px 20px 20px;">
              <el-carousel trigger="click" style="width: 100%;" height="450px">
                <el-carousel-item v-for="image in listImage" :key="image">
                  <el-image style="width: 100%;" :src="image"></el-image>
                </el-carousel-item>
              </el-carousel>
            </div>
          </el-col>
          <el-col :span="14">
            <div style="color: #000; padding: 20px;">
              <div style="font-size: 20px;">
                {{ product ? product.name : '' }}
              </div>
              <div style="margin-top: 20px; display: flex; align-items: center;">
                <el-rate
                  :value="product ? product.rate : 0"
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  text-color="#ff9900"
                  disabled>
                </el-rate>
                <div style="border-left: 1px solid gray; margin-left: 20px; padding-left: 20px;">
                  {{ product ? product.quantitySold : 0 }} Đã bán
                </div>
              </div>
              <div style="margin-top: 20px; font-size: 25px; font-weight: 500; color: #ee4d2d;">
                {{ formatCurrenyVND(product ? product.price : 0) }}
              </div>
              <div style="margin-top: 20px;">
                Kích cỡ:
                <div style="display: flex; margin-top: 10px;">
                  <div v-for="(s, i) in sizes">
                    <div :class="[sizeSelected === s.id ? 'tag tag-active' : 'tag', s.isOutOfStock ? 'tag-disable' : '']" @click="changeSizeSelected(s.id, s.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ s.name }}
                    </div>
                  </div>
                </div>
              </div>
              <div style="margin-top: 20px;">
                Màu sắc:
                <div style="display: flex; margin-top: 10px;">
                  <div v-for="(c, i) in colors">
                    <div :class="[colorSelected === c.id ? 'tag tag-active' : 'tag', c.isOutOfStock ? 'tag-disable' : '']" class="''" @click="changeColorSelected(c.id, c.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ c.name }}
                    </div>
                  </div>
                </div>
              </div>
              <div style="margin-top: 20px;">
                Chất Liệu:
                <div style="display: flex; margin-top: 10px;">
                  <div v-for="(m, i) in materials">
                    <div :class="[materialSelected === m.id ? 'tag tag-active' : 'tag', m.isOutOfStock ? 'tag-disable' : '']" @click="changeMaterialSelected(m.id, m.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ m.name }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-row style="background-color: #fff; border-radius: 2px;">
          <el-col :span="24">
            <div style="padding: 20px;"></div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getProductByIdWebShop } from '../../../api/product'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'

export default {
  components: {
  },
  data() {
    return {
      productId: this.$route.params.id ? this.$route.params.id : '',
      product: null,
      listImage: [],
      sizes: [],
      colors: [],
      materials: [],
      colorSelected: '',
      sizeSelected: '',
      materialSelected: ''
    }
  },
  watch: {
    sizeSelected(newValue, oldValue) {
      console.log(newValue + ' - ' + oldValue)
    }
  },
  methods: {
    formatCurrenyVND(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
    },
    changeSizeSelected(id, isOutOfStock) {
      if(!isOutOfStock) {
        this.sizeSelected = id
      }
    },
    changeColorSelected(id, isOutOfStock) {
      if(!isOutOfStock) {
        this.colorSelected = id
      }
    },
    changeMaterialSelected(id, isOutOfStock) {
      if(!isOutOfStock) {
        this.materialSelected = id
      }
    }
  },
  created() {
    if(this.productId) {
      getProductByIdWebShop(this.productId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            console.log(res)
            this.product = res.data.data
            this.listImage = res.data.data.listImage
            this.sizes = res.data.data.sizes
            this.colors = res.data.data.colors
            this.materials = res.data.data.materials
          }
        })
    }
  }
}
</script>

<style scoped lang="css">
.tag {
  cursor: pointer;
  padding: 5px 15px;
  border: 1px solid rgba(0,0,0,.09);
  border-radius: 2px;
}
.tag:hover {
  border-color: #ee4d2d;
  color: #ee4d2d;
}
.tag-active {
  border-color: #ee4d2d;
  color: #ee4d2d;
}
.tag-disable {
  cursor: not-allowed;
  background-color: rgba(0,0,0,.09);
}
.tag-disable:hover {
  border-color: rgba(0,0,0,.09);
  color: #000;
}
</style>
