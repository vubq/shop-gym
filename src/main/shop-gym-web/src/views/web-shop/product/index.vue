<template>
  <div style="padding: 50px;">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-row style="background-color: #fff; border-radius: 5px;">
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

          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24" style="margin-top: 20px;">
        <el-row style="background-color: #fff; border-radius: 5px;">
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
      listImage: []
    }
  },
  methods: {
  },
  created() {
    if(this.productId) {
      getProductByIdWebShop(this.productId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            console.log(res)
            this.product = res.data.data
            this.listImage = res.data.data.listImage
          }
        })
    }
  }
}
</script>

<style scoped lang="css">
</style>
