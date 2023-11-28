<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="3">
        <el-input placeholder="Nhập mã SP"></el-input>
      </el-col>

      <el-col :span="3">
        <el-select v-model="product.size" placeholder="Size">
          <el-option
            v-for="size in listSizeFilter"
            :key="size.id"
            :label="size.name"
            :value="size.id">
          </el-option>
        </el-select>
      </el-col>

      <el-col :span="3">
        <el-select v-model="product.color" placeholder="Màu">
          <el-option
            v-for="color in listColorFilter"
            :key="color.id"
            :label="color.name"
            :value="color.id">
          </el-option>
        </el-select>
      </el-col>

      <el-col :span="3">
        <el-select v-model="product.material" placeholder="Chất liệu">
          <el-option
            v-for="material in listMaterialFilter"
            :key="material.id"
            :label="material.name"
            :value="material.id">
          </el-option>
        </el-select>
      </el-col>

      <el-col :span="3">
        <el-input-number :min="1" style="width: 100%;"></el-input-number>
      </el-col>

      <el-col :span="4">
        <el-input placeholder="Giá sản phẩm"></el-input>
      </el-col>
    </el-row>
  </div>
</template>

<script lang='ts'>
import { Component, Prop, Vue } from 'vue-property-decorator'

@Component({
  name: 'ProductDetailAttribute',
  components: {}
})
export default class extends Vue {
  @Prop() private listSize: any;
  @Prop() private listColor: any;
  @Prop() private listMaterial: any;
  @Prop() private product: any;

  private listSizeFilter = [];
  private listColorFilter = [];
  private listMaterialFilter = [];

  private created() {

    this.listColorFilter = this.listColor;
    this.listSizeFilter = this.listSize;
    this.listMaterialFilter = this.listMaterial;
  }
}
</script>

<style lang='scss' scoped>
</style>
  