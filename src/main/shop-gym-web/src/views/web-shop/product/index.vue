<template>
  <div style="padding: 50px;">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-row style="background-color: #fff; border-radius: 2px;">
          <el-col :span="10">
            <div style="padding: 20px 40px 20px 20px;">
              <el-carousel trigger="click" style="width: 100%;" height="550px">
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
                {{ product ? product.rate + '/5' : '0/5' }}
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
                    <div :class="[sizeSelected === s.id ? 'tag tag-active' : 'tag', s.isOutOfStock || !genIsOutOfStockSize(s.id) ? 'tag-disable' : '']" @click="changeSizeSelected(s.id, s.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ s.name }}
                    </div>
                  </div>
                </div>
              </div>
              <div style="margin-top: 20px;">
                Màu sắc:
                <div style="display: flex; margin-top: 10px;">
                  <div v-for="(c, i) in colors">
                    <div :class="[colorSelected === c.id ? 'tag tag-active' : 'tag', c.isOutOfStock || !genIsOutOfStockColor(c.id) ? 'tag-disable' : '']" class="''" @click="changeColorSelected(c.id, c.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ c.name }}
                    </div>
                  </div>
                </div>
              </div>
              <div style="margin-top: 20px;">
                Chất Liệu:
                <div style="display: flex; margin-top: 10px;">
                  <div v-for="(m, i) in materials">
                    <div :class="[materialSelected === m.id ? 'tag tag-active' : 'tag', m.isOutOfStock || !genIsOutOfStockMaterial(m.id) ? 'tag-disable' : '']" @click="changeMaterialSelected(m.id, m.isOutOfStock)" :style="i === 0 || i === sizes.length - 1 ? '' : 'margin: 0 10px;'">
                      {{ m.name }}
                    </div>
                  </div>
                </div>
              </div>
              <div style="margin-top: 20px;">
                Số lượng:
                <div style="margin-top: 10px;">
                  <el-input-number v-model="quantityPurchased" :min="1" :max="totalProductsAvailable" style="width: 120px;"></el-input-number>
                  <span style="margin-left: 20px;">{{ totalProductsAvailable }} sản phẩm có sẵn</span>
                </div>
              </div>
              <div style="margin-top: 20px; padding: 10px 0;">
                <span @click="addToCart" style="padding: 10px 25px; background-color: #ee4d2d; color: #fff; border-radius: 2px; font-size: 20px; cursor: pointer;">
                  <i class="el-icon-shopping-cart-full"></i>
                  Thêm vào giỏ hàng
                </span>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-row style="background-color: #fff; border-radius: 2px;">
          <el-col :span="24">
            <div style="padding: 20px;">
              <div style="font-size: 18px;">CHI TIẾT SẢN PHẨM</div>
              <div style="margin-top: 10px;">
                Tên: {{ product ? product.name : '' }}
              </div>
              <div style="margin-top: 10px;">
                Danh mục: {{ product ? product.category.name : '' }}
              </div>
              <div style="margin-top: 10px;">
                Thương hiệu: {{ product ? product.brand.name : '' }}
              </div>
              <div style="font-size: 18px; margin-top: 20px;">MÔ TẢ SẢN PHẨM</div>
              <div style="margin-top: 10px;">
                1 . Thông tin sản phẩm : 

                - Chất liệu nỉ Bông   Cotton 100%  mềm mại dày dặn  , ấm áp 

                - Đường may được gia công tỉ mỉ, chắc chắn.

                - Hình in sắc nét, chân thực

                - Hoodie luôn là món đồ cần có cho các bạn trẻ



                - Chất nỉ bông dày dặn - Ấm áp.

                - Form Hàn Quốc Basic - Cực kỳ dễ mặc

                - Form dáng basic – Dễ phối đồ. Có thể mặc nhà hoặc đi chơi đều được



                2. Size áo Áo Hoodie Nam Nữ UNICI gồm có : M - L - XL

                M : Ngang 54cm - Dài 68cm ( 45kg - 60kg )

                L : Ngang 56cm - Dài 70cm  ( 60kg - 70kg )

                XL: ngang 58 cm - Dài 72cm   ( 70kg - 80kg )

                2XL: ngang 60 cm - Dài 74cm   ( 80kg - 85kg )



                * Bảng size chỉ mang tính chất tham khảo và phù hợp với phần đông khách hàng, Khách hoàn toàn có thể chọn lên hoặc xuống size tuỳ thuộc vào sở thích và form dáng của mình 

                3. Shop CAM KẾT

                +) Về sản phẩm: shop cam kết cả về chất liệu và hình dáng giống ảnh

                +) Về giá cả: Shop sản xuất với số lượng nhiều và trực tiếp không qua trung gian nên sẽ rẻ nhất

                +)Về dịch vụ: tư vấn nhiệt tình, chu đáo, luôn lắng nghe khách hàng để phục vụ tốt

                +) Về thời gian chuẩn bị hàng: nhanh, đúng tiến độ, không để quý khách chờ đợi l

                + Cam kết 100% đổi Size nếu sản phẩm khách đặt không vừa (Hỗ trợ đổi size trong vòng 7 ngày)

                + Shop hỗ trợ đổi sang sản phẩm khác cùng giá hoặc cao hơn nếu khách có nhu cầu đổi mẫu khác./.

                + Nếu có bất kì khiếu nại cần Shop hỗ trợ về sản phẩm, khi mở sản phẩm bạn vui lòng quay lại video quá trình mở sản phẩm để được đảm bảo 100% đổi lại sản phẩm mới nếu Shop giao bị lỗi.

                + Bạn nhận được sản phẩm, vui lòng đánh giá giúp Shop để hưởng thêm nhiều ưu đãi hơn nhé.



                Tên nhà sản xuất: Xưởng may Anh Châu

                Địa chỉ: Hồ My Thượng, Thôn My Thượng, Xã Thanh Mai, Huyện Thanh Oai, Hà Nội
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-row style="background-color: #fff; border-radius: 2px;">
          <el-col :span="24" style="padding: 20px;">
            <div style="font-size: 18px;">ĐÁNH GIÁ SẢN PHẨM</div>
            <div>
              <div style="background-color: #fffbf8; border: 1px solid #f9ede5; margin-top: 10px; padding: 20px;">
                <el-row>
                  <el-col :span="4">
                    <div style="text-align: center;">
                      <span style="font-size: 25px;">{{ product ? product.rate + '/5' : '0/5' }}</span>
                      <el-rate class="rate-cmt" style="height: 37px;"
                        :value="product ? product.rate : 0"
                        :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                        text-color="#ff9900"
                        disabled>
                      </el-rate>
                    </div>
                  </el-col>
                  <el-col :span="20">
                    <div>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        Tất cả
                      </span>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        5 sao
                      </span>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        4 sao
                      </span>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        3 sao
                      </span>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        2 sao
                      </span>
                      <span class="tag" style="margin: 0 5px; background-color: #fff;">
                        1 sao
                      </span>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-col>
          <el-col :span="24" style="padding: 0 20px 20px 20px;">
            <div v-for="(f, i) in listOfFeedbacks" :key="f.i">
              <div>
                <el-divider></el-divider>
                <div style="display: flex;">
                  <div>
                    <el-avatar :src="f.createdBy.avatar"></el-avatar>
                  </div>
                  <div style="margin-left: 20px;">
                    <div>{{ f.createdBy.fullName }}</div>
                    <div style="margin-top: 3px;">
                      <el-rate
                        :value="f.rate"
                        :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                        text-color="#ff9900"
                        disabled>
                      </el-rate>
                    </div>
                    <div style="margin-top: 3px; display: flex;">
                      <div>
                        {{ Moment(f.createdAt).format('DD-MM-YYYY HH:mm:ss') }}
                      </div>
                      <div style="margin-left: 5px; border-left: 1px solid gray; padding-left: 5px;">
                        {{ f.productDetail.size.name + ', ' + f.productDetail.color.name + ', ' + f.productDetail.material.name }}
                      </div>
                    </div>
                    <div style="margin-top: 15px;">
                      {{ f.content }}
                    </div>
                  </div>
                </div>
                <el-divider v-if="i === listOfFeedbacks.length - 1"></el-divider>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getProductByIdWebShop } from '../../../api/product'
import { filterProductAttributes, getProductDetailByAttributes } from '../../../api/product-detail'
import Pagination from '../../../components/pagination'
import { ResponseCode, Status } from '../../../enums/enums'
import { getListOfFeedbacksByCriteria } from '../../../api/feedback'
import moment from 'moment'
import { mapActions } from 'vuex'

export default {
  components: {
  },
  data() {
    return {
      Moment: moment,
      productId: this.$route.params.id ? this.$route.params.id : '',
      product: null,
      listImage: [],
      sizes: [],
      colors: [],
      materials: [],
      listOfAvailableSizes: [],
      listOfAvailableColors: [],
      listOfAvailableMaterials: [],
      listOfDisableSizes: [],
      listOfDisableColors: [],
      listOfDisableMaterials: [],
      colorSelected: '',
      sizeSelected: '',
      materialSelected: '',
      totalProductsAvailable: 0,
      quantityPurchased: 1,
      listOfFeedbacks: []
    }
  },
  methods: {
    ...mapActions([
      'addProductToCart'
    ]),
    formatCurrenyVND(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
    },
    changeSizeSelected(id, isOutOfStock) {
      if(!isOutOfStock && this.genIsOutOfStockSize(id)) {
        if(id === this.sizeSelected) {
          this.sizeSelected = ''
        } else {
          this.sizeSelected = id
        }
        this.filterAttributes()
      }
    },
    changeColorSelected(id, isOutOfStock) {
      if(!isOutOfStock && this.genIsOutOfStockColor(id)) {
        if(id === this.colorSelected) {
          this.colorSelected = ''
        } else {
          this.colorSelected = id
        }
        this.filterAttributes()
      }
    },
    changeMaterialSelected(id, isOutOfStock) {
      if(!isOutOfStock && this.genIsOutOfStockMaterial(id)) {
        if(id === this.materialSelected) {
          this.materialSelected = ''
        } else {
          this.materialSelected = id
        }
        this.filterAttributes()
      }
    },
    filterAttributes() {
      filterProductAttributes({
        productId: this.productId,
        sizeId: this.sizeSelected,
        colorId: this.colorSelected,
        materialId: this.materialSelected
      }).then(res => {
        if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
          if(!this.sizeSelected) {
            this.listOfDisableSizes = this.sizes.map(e => e.id)
            res.data.data.listOfAvailableSizes.forEach(e => {
              this.listOfDisableSizes = this.listOfDisableSizes.filter(x => x !== e)
            })
          }
          if(!this.colorSelected) {
            this.listOfDisableColors = this.colors.map(e => e.id)
            res.data.data.listOfAvailableColors.forEach(e => {
              this.listOfDisableColors = this.listOfDisableColors.filter(x => x !== e)
            })
          }
          if(!this.materialSelected) {
            this.listOfDisableMaterials = this.materials.map(e => e.id)
            res.data.data.listOfAvailableMaterials.forEach(e => {
              this.listOfDisableMaterials = this.listOfDisableMaterials.filter(x => x !== e)
            })
          }
          this.totalProductsAvailable = res.data.data.totalProductsAvailable
        }
      })
    },
    genIsOutOfStockSize(id) {
      return !this.listOfDisableSizes.find(e => e === id)
    },
    genIsOutOfStockColor(id) {
      return !this.listOfDisableColors.find(e => e === id)
    },
    genIsOutOfStockMaterial(id) {
      return !this.listOfDisableMaterials.find(e => e === id)
    },
    addToCart() {
      if(this.sizeSelected && this.colorSelected && this.materialSelected) {
        getProductDetailByAttributes({
          productId: this.productId,
          sizeId: this.sizeSelected,
          colorId: this.colorSelected,
          materialId: this.materialSelected
        }).then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.addProductToCart({
              productDetailId: res.data.data.id,
              quantity: this.quantityPurchased
            })
          }
        })
      }
    }
  },
  created() {
    if(this.productId) {
      getProductByIdWebShop(this.productId)
        .then(res => {
          if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
            this.product = res.data.data
            this.listImage = res.data.data.listImage
            this.sizes = res.data.data.sizes
            this.colors = res.data.data.colors
            this.materials = res.data.data.materials
            this.totalProductsAvailable = res.data.data.totalProductsAvailable
          }
        })
    }
    getListOfFeedbacksByCriteria({
      currentPage: 1,
      perPage: 10,
      filter: '',
      sortBy: '',
      sortDesc: true,
      productId: this.productId,
      rate: null
    }).then(res => {
      if(res.data && res.data.code === ResponseCode.CODE_SUCCESS) {
        this.listOfFeedbacks = res.data.items
      }
    })
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

<style>
.rate-cmt .el-rate__icon {
  font-size: 35px;
}
</style>
