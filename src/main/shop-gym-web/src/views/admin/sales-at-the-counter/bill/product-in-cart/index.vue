<template>
  <div style="border: 1px solid #EBEEF5; border-radius: 4px; padding: 10px; align-items: center;">
        

      <el-row :gutter="60" style="margin: 0;">
        <el-col :span="4">
          <el-image 
            style="width: 100px; height: 100px"
            :src="product.image">
          </el-image>
        </el-col>
        <el-col :span="20">
          <el-row :gutter="60" style="margin: 0;">
            <el-col :span="8">
              <el-select value="" filterable placeholder="Size" style="width: 100% !important;">
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-select value="" filterable placeholder="Màu" style="width: 100% !important;">
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-select value="" filterable placeholder="Chất liệu" style="width: 100% !important;">
              </el-select>
            </el-col>
          </el-row>
          <el-row :gutter="60" style="margin: 20px 0 0 0;">
            <el-col :span="8">
              <el-input value="" filterable placeholder="Số lượng" style="width: 100% !important;">
              </el-input>
            </el-col>
            <el-col :span="16">
              Thành tiền: 1000.1000 VND
            </el-col>
          </el-row>
        </el-col>
      </el-row>

  </div>

</template>

<script lang='ts'>
import { Component, Vue, Prop } from 'vue-property-decorator'
import { getProductById } from "@/services/product/ProductService";
import { ProductModel } from '@/models/ProductModel';

@Component({
  name: 'ProductInCart',
  components: {}
})
export default class extends Vue {
  @Prop() private orderDetail: any;
  private product: ProductModel = new ProductModel();

  private created() {
    getProductById(this.orderDetail.productId).then((res: any) => {
      this.product = res.data.data;
    })
  }
}
</script>

<style lang='scss' scoped>
</style>