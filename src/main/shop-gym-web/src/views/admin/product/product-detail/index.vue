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
import { SizeModel } from '@/models/SizeModel';
import { ColorModel } from '@/models/ColorModel';
import { MaterialModel } from '@/models/MaterialModel';

@Component({
  name: 'ProductDetail',
  components: {
    ProductDetailAttribute
  }
})
export default class extends Vue {
  private listColor: ColorModel[] = [];
  private listSize: SizeModel[] = [];
  private listMaterial: MaterialModel[] = [];

  private selectedColor: any[] = [];
  private selectedSize: any[] = [];
  private selectedMaterial: any[] = [];

  private listProductDetailAttribute: any[] = [];

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
    const list: any[] = [];

    if(this.selectedSize.length > 0 && this.selectedColor.length > 0 && this.selectedMaterial.length > 0) {
      const totalNumberProduct = this.selectedSize.length * this.selectedColor.length * this.selectedMaterial.length;

      let iSize = 0;
      let iColor = 0;
      let iMaterial = 0;

      for(let i = 0; i < totalNumberProduct; i++) {

        let maxSize = 1 * this.selectedColor.length * this.selectedMaterial.length; 
        let maxColor = 1 * this.selectedSize.length * this.selectedMaterial.length;

        // Size
        let totalSize = list.filter((e: any) => e.size === this.selectedSize[iSize]).length;
        let size = totalSize < totalNumberProduct / this.selectedSize.length ? this.selectedSize[iSize] : this.selectedSize[++iSize];

        console.log(i + ": iColor: " + iColor + ", totalSize: " + totalSize);
        // Color
        if(iColor === this.selectedColor.length - 1 && totalSize === maxSize) {
          iColor = 0;
        }
        let totalColor = list.filter((e: any) => e.size === size && e.color === this.selectedColor[iColor] ).length;
        let color = totalColor === maxSize / this.selectedColor.length ? this.selectedColor[++iColor] : this.selectedColor[iColor];

        // Material
        if(iMaterial === this.selectedMaterial.length - 1) {
          iMaterial = 0;
        }
        let totalMaterial = list.filter((e: any) => e.size === size && e.color === color && e.material === this.selectedMaterial[iMaterial]).length;
        let material = totalMaterial === 1 ? this.selectedMaterial[++iMaterial] : this.selectedMaterial[iMaterial];

        list.push({
          code: '',
          size: size,
          color: color,
          material: material,
          quantity: 0,
          price: 0
        })
      }
    }
    this.listProductDetailAttribute = list;
  }
}
</script>

<style lang='scss' scoped>
</style>
  