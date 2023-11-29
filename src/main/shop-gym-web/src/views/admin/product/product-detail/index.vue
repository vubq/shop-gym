<template>
  <div>
    <span style="font-weight: bold;">Thêm mới sản phẩm</span>

    <el-row style="margin-top: 20px; display: flex; gap: 20px;" :gutter="40">
      <el-col :span="8" style="background-color: #fff; border-radius: 4px; padding: 20px; text-align: center;">
        <el-upload
          action="#"
          list-type="picture-card"
          :auto-upload="false"
          ref="imageProductUpload"
          :file-list="listImage"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url" alt=""
            >
            <span class="el-upload-list__item-actions">
              <span
                class="el-upload-list__item-preview"
                @click="handlePictureCardPreview(file)"
              >
                <i class="el-icon-zoom-in"></i>
              </span>
              <span
                v-if="!disabled"
                class="el-upload-list__item-delete"
                @click="handleRemove(file)"
              >
                <i class="el-icon-delete"></i>
              </span>
            </span>
          </div>
        </el-upload>
      </el-col>

      <el-col :span="16" style="background-color: #fff; border-radius: 4px; padding: 20px;">
        <span>Thông tin sản phẩm</span>
        <div style="margin-top: 10px;">
          <el-form label-position="right" label-width="">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="Tên sản phẩm">
                  <el-input v-model="product.name" placeholder="Vui lòng nhập tên sản phẩm"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="Loại sản phẩm">
                  <el-select v-model="product.categoryId" placeholder="Vui lòng chọn loại sản phẩm" style="width: 100%;">
                    <el-option
                      v-for="category in listCategory"
                      :key="category.id"
                      :label="category.name"
                      :value="category.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="Thương hiệu">
                  <el-select v-model="product.brandId" placeholder="Vui lòng chọn thương hiệu" style="width: 100%;">
                    <el-option
                      v-for="brand in listBrand"
                      :key="brand.id"
                      :label="brand.name"
                      :value="brand.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Giá mặc định">
                  <el-input v-model="product.price" placeholder="Vui lòng nhập giá mặc định của sản phẩm">
                    <template slot="append">VND</template>
                  </el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Mô tả">
                  <el-input v-model="product.description" type="textarea" placeholder="Vui lòng nhập mô tả"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Size">
                  <el-select
                    v-model="selectedSize"
                    multiple
                    filterable
                    default-first-option
                    placeholder="Vui lòng chọn size"
                    @change="selectAttribute()"
                    style="width: 100%;">
                    <el-option
                      v-for="size in listSize"
                      :key="size.id"
                      :label="size.name"
                      :value="size.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Màu sắc">
                  <el-select
                    v-model="selectedColor"
                    multiple
                    filterable
                    default-first-option
                    placeholder="Vui lòng chọn màu sắc"
                    @change="selectAttribute()"
                    style="width: 100%;">
                    <el-option
                      v-for="color in listColor"
                      :key="color.id"
                      :label="color.name"
                      :value="color.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Chất liệu">
                  <el-select
                    v-model="selectedMaterial"
                    multiple
                    filterable
                    default-first-option
                    placeholder="Vui lòng chọn chất liệu"
                    @change="selectAttribute()"
                    style="width: 100%;">
                    <el-option
                      v-for="material in listMaterial"
                      :key="material.id"
                      :label="material.name"
                      :value="material.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </el-col>
    </el-row>

    <div style="background-color: #fff; border-radius: 4px; padding: 20px; margin-top: 20px;">
      <el-table
        key="0"
        :data="product.productDetails"
        ref="tableP"
      >
        <el-table-column type="expand">
          <template slot-scope="{row}">
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
              ref="imageProductDetailUpload"
              :file-list="row.images"
              :on-change="$event => addImageProductDetail($event, row)"
            >
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url" alt=""
                >
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column
          label="Code"
          align="center"
        >
          <template slot-scope="{row}">
            <el-input v-model="row.code"></el-input>
          </template>
        </el-table-column>

        <el-table-column
          label="Size"
          align="center"
        >
          <template slot-scope="{row}">
            <span>{{ genAttribute('SIZE', row.sizeId) }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="Màu sắc"
          align="center"
        >
          <template slot-scope="{row}">
            <span>{{ genAttribute('COLOR', row.colorId) }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="Chất liệu"
          align="center"
        >
          <template slot-scope="{row}">
            <span>{{ genAttribute('MATERIAL', row.materialId) }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="Số lượng"
          align="center"
        >
          <template slot-scope="{row}">
            <el-input v-model="row.quantity"></el-input>
          </template>
        </el-table-column>

        <el-table-column
          label="Giá tiền"
          align="center"
        >
          <template slot-scope="{row}">
            <el-input v-model="row.price" :value="product.price">
              <template slot="append">VND</template>
            </el-input>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- <div style="display: flex; gap: 20px;">
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
    </div> -->
    <el-button type="primary" @click="createProduct" style="margin-top: 20px;">
      Thêm sản phẩm
    </el-button>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import { getAllColor } from '@/services/color/colorService'
import { getAllSize } from '@/services/size/sizeService'
import { getAllMaterial } from '@/services/material/materialService'
import { getAllCategory } from '@/services/category/categoryService'
import { getAllBrand } from '@/services/brand/brandService'
import ProductDetailAttribute from './ProductDetailAttribute.vue';
import { SizeModel } from '@/models/SizeModel';
import { ColorModel } from '@/models/ColorModel';
import { MaterialModel } from '@/models/MaterialModel';
import { CategoryModel } from '@/models/CategoryModel';
import { BrandModel } from '@/models/BrandModel';
import { ProductModel } from '@/models/ProductModel';
import { ProductDetailModel } from '@/models/ProductDetailModel';
import { ImageModel } from '@/models/ImageModel';
import { createProduct } from '@/services/product/ProductService';
import axios from 'axios';

@Component({
  name: 'ProductDetail',
  components: {
    ProductDetailAttribute
  }
})
export default class extends Vue {
  private product: ProductModel = new ProductModel();

  private listColor: ColorModel[] = [];
  private listSize: SizeModel[] = [];
  private listMaterial: MaterialModel[] = [];

  private listCategory: CategoryModel[] = [];
  private listBrand: BrandModel[] = [];

  private selectedColor: any[] = [];
  private selectedSize: any[] = [];
  private selectedMaterial: any[] = [];

  // private listProductDetailAttribute: any[] = [];

  private dialogImageUrl: any = '';
  private dialogVisible: any = false;
  private disabled: any = false;

  private listImage: any = []
  private listImageDelete: any = [];

  private addImageProductDetail(file: any, productDetail: any) {
    this.product.productDetails = this.product.productDetails?.map((e: any) => {
      if(e.colorId === productDetail.color && e.sizeId === productDetail.sizeId && e.materialId === productDetail.materialId) {
        return {...e, images: e.images.push(file)};
      } else {
        return e;
      }
    });
  }

  // private mounted() {
  //   console.log(this.$refs.upload);
  //   (this.$refs.upload as any).uploadFiles.push({ name: '1', url: 'https://i.pinimg.com/originals/98/ca/1b/98ca1b7585e9642585ac2ba5719f6087.jpg', delete: true})
  // }

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

    getAllCategory().then((res: any) => {
      if(res.data) {
        this.listCategory = res.data;
      }
    })

    getAllBrand().then((res: any) => {
      if(res.data) {
        this.listBrand = res.data;
      }
    })
  }

  private createProduct() {
    // this.product.imageFiles = (this.$refs.imageProductUpload as any).uploadFiles.map((e: any) => { return e.raw });
    // this.product.test = (this.$refs.imageProductUpload as any).uploadFiles[0].raw;

    // (this.$refs.imageProductUpload as any).uploadFiles.map((e: any) => {
    //   const data = new FormData();
    //   data.append("file", (this.$refs.imageProductUpload as any).uploadFiles[0].raw);
    //   data.append("upload_preset", "vubq-upload");
    //   data.append("cloud_name", "vubq");
    //   axios.post("https://api.cloudinary.com/v1_1/vubq/image/upload", data).then((res: any) => {
    //     const image = new ImageModel();
    //     image.publicId = res.public_id;
    //     image.url = res.url;
    //     this.product.images?.push(image);
    //   })
    // });
    console.log(this.$refs)
    console.log(this.product)
    

    // createProduct(this.product).then((res: any) => {
    //   console.log(res);
    // })
    // console.log(this.product);
  }

  handleRemove(file: any) {

    if(file.raw === undefined) {
      file.delete = true;
      this.listImageDelete.push(file);
    }
    (this.$refs.imageProductUpload as any).handleRemove(file);
    console.log((this.$refs.imageProductUpload as any).uploadFiles)
  }

  handlePictureCardPreview(file: any) {

    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  private selectAttribute() {

    const list: ProductDetailModel[] = [];

    if(this.selectedSize.length > 0 && this.selectedColor.length > 0 && this.selectedMaterial.length > 0) {
      const totalNumberProduct = this.selectedSize.length * this.selectedColor.length * this.selectedMaterial.length;

      let iSize = 0;
      let iColor = 0;
      let iMaterial = 0;

      for(let i = 0; i < totalNumberProduct; i++) {

        let maxSize = 1 * this.selectedColor.length * this.selectedMaterial.length; 
        // let maxColor = 1 * this.selectedSize.length * this.selectedMaterial.length;

        // Size
        let totalSize = list.filter((e: any) => e.sizeId === this.selectedSize[iSize]).length;
        let size = totalSize < totalNumberProduct / this.selectedSize.length ? this.selectedSize[iSize] : this.selectedSize[++iSize];

        // console.log(i + ": iColor: " + iColor + ", totalSize: " + totalSize);
        // Color
        if(iColor === this.selectedColor.length - 1 && totalSize === maxSize) {
          iColor = 0;
        }
        let totalColor = list.filter((e: any) => e.sizeId === size && e.colorId === this.selectedColor[iColor] ).length;
        let color = totalColor === maxSize / this.selectedColor.length ? this.selectedColor[++iColor] : this.selectedColor[iColor];

        // Material
        if(iMaterial === this.selectedMaterial.length - 1) {
          iMaterial = 0;
        }
        let totalMaterial = list.filter((e: any) => e.sizeId === size && e.colorId === color && e.materialId === this.selectedMaterial[iMaterial]).length;
        let material = totalMaterial === 1 ? this.selectedMaterial[++iMaterial] : this.selectedMaterial[iMaterial];

        const productDetail = new ProductDetailModel();
        productDetail.sizeId = size;
        productDetail.colorId = color;
        productDetail.materialId = material;
        productDetail.quantity = 0;
        productDetail.price = this.product.price ? this.product.price : 0;
        productDetail.index = i;

        list.push(productDetail);
      }
    }
    this.product.productDetails = list;
  }

  private genAttribute(field: string, id: string) {

    switch (field) {
      case 'SIZE':
        return this.listSize.find((e: any) => e.id === id)?.name;
      case 'COLOR':
        return this.listColor.find((e: any) => e.id === id)?.name;
      case 'MATERIAL':
        return this.listMaterial.find((e: any) => e.id === id)?.name;
    }
  }
}
</script>

<style lang='scss' scoped>
</style>