<template>
  <div>
    <div style="display: flex; gap: 20px;">
      <el-select
        v-model="selectedSize"
        multiple
        filterable
        default-first-option
        collapse-tags
        placeholder="Vui lòng chọn size"
        @change="selectAttribute()">
        <el-option
          v-for="size in listSize"
          :key="size.id"
          :label="size.name"
          :value="size.id">
        </el-option>
      </el-select>

      <el-select
        v-model="selectedColor"
        multiple
        filterable
        default-first-option
        collapse-tags
        placeholder="Vui lòng chọn màu"
        @change="selectAttribute()">
        <el-option
          v-for="color in listColor"
          :key="color.id"
          :label="color.name"
          :value="color.id">
        </el-option>
      </el-select>

      <el-select
        v-model="selectedMaterial"
        multiple
        filterable
        default-first-option
        collapse-tags
        placeholder="Vui lòng chọn chất liệu"
        @change="selectAttribute()">
        <el-option
          v-for="material in listMaterial"
          :key="material.id"
          :label="material.name"
          :value="material.id">
        </el-option>
      </el-select>
    </div>

    {{ selectedSize }}, {{ selectedColor }}, {{ selectedMaterial }}

    <div v-for="(productDetailAttribute, i) in listProductDetailAttribute" :key="i">
      <ProductDetailAttribute 
        :listSize="listSize"
        :listColor="listColor"
        :listMaterial="listMaterial"
        :product="productDetailAttribute">
      </ProductDetailAttribute>
    </div>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import { getAllColor } from '@/services/color/colorService'
import { getAllSize } from '@/services/size/sizeService'
import { getAllMaterial } from '@/services/material/materialService'
import ProductDetailAttribute from './ProductDetailAttribute.vue';

@Component({
  name: 'ProductDetail',
  components: {
    ProductDetailAttribute
  }
})
export default class extends Vue {
  private listColor = [];
  private listSize = [];
  private listMaterial = [];

  private selectedColor = [];
  private selectedSize = [];
  private selectedMaterial = [];

  private listProductDetailAttribute = [
    {
      code: '',
      size: '',
      color: '',
      material: '',
      quantity: '',
      price: ''
    }
  ];

  private created() {

    getAllColor().then((res: any) => {
      if(res.data) {
        this.listColor = res.data;
      }
    })

    getAllMaterial().then((res: any) => {
      if(res.data) {
        this.listMaterial = res.data;
      }
    })

    getAllSize().then((res: any) => {
      if(res.data) {
        this.listSize = res.data;
      }
    })
  }

  private selectAttribute() {
    const list = [];

    if(this.selectedSize.length > 0 && this.selectedColor.length > 0 && this.selectedMaterial.length > 0) {
      const totalNumberProduct = this.selectedSize.length * this.selectedColor.length * this.selectedMaterial.length;
      console.log('Tổng: ' + totalNumberProduct)
      const attribute1 = totalNumberProduct / this.listSize.length
      console.log('Tổng x: ' + attribute1)
      let iSize = 0;
      let count = 1;
      for(let i = 1; i <= totalNumberProduct; i++) {
        console.log('----')
        console.log(count)
        if(count - 1 === attribute1) {
          iSize++;
          count = 1;
        }
        count++;
        console.log(iSize)
        console.log('----')
        // if(this.listProductDetailAttribute.length === 0) {
        //   this.listProductDetailAttribute.push({
        //     code: '',
        //     size: this.listSize[iSize],
        //     color: '',
        //     material: '',
        //     quantity: '',
        //     price: ''
        //   })
        // }
        list.push({
          code: '',
          size: this.listSize[iSize].id,
          color: '',
          material: '',
          quantity: '',
          price: ''
        })
      }
    }
    this.listProductDetailAttribute = list;
    // console.log(this.listProductDetailAttribute)
  }
}
</script>

<style lang='scss' scoped>
</style>
  