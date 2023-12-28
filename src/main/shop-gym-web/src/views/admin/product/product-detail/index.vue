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
          :file-list="product.images"
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
                    v-model="product.sizes"
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
                      :value="size.id"
                      :disabled="genDisabled('SIZE', size.id)">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Màu sắc">
                  <el-select
                    v-model="product.colors"
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
                      :value="color.id"
                      :disabled="genDisabled('COLOR', color.id)">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="24">
                <el-form-item label="Chất liệu">
                  <el-select
                    v-model="product.materials"
                    multiple
                    filterable
                    default-first-option
                    placeholder="Vui lòng chọn chất liệu"
                    @change="selectAttribute()"
                    :remove-tag="removeTag"
                    style="width: 100%;">
                    <el-option
                      v-for="material in listMaterial"
                      :key="material.id"
                      :label="material.name"
                      :value="material.id"
                      :disabled="genDisabled('MATERIAL', material.id)">
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
        <!-- <el-table-column type="expand">
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
        </el-table-column> -->
        <!-- <el-table-column
          label="Code"
          align="center"
        >
          <template slot-scope="{row}">
            <el-input v-model="row.code"></el-input>
          </template>
        </el-table-column> -->

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
        v-model="product.sizes"
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
        v-model="product.colors"
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
        v-model="product.materials"
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

    {{ product.sizes }}, {{ product.colors }}, {{ product.materials }}

    <div v-for="(productDetailAttribute, i) in listProductDetailAttribute" :key="i">
      <ProductDetailAttribute 
        :listSize="listSize"
        :listColor="listColor"
        :listMaterial="listMaterial"
        :product="productDetailAttribute">
      </ProductDetailAttribute>
    </div> -->
    <el-button type="primary" @click="createOrUpdateProduct" style="margin-top: 20px;">
      {{ productId ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm' }}
    </el-button>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import { getAllColor } from '@/services/color/ColorService'
import { getAllSize } from '@/services/size/SizeService'
import { getAllMaterial } from '@/services/material/MaterialService'
import { getAllCategory } from '@/services/category/CategoryService'
import { getAllBrand } from '@/services/brand/BrandService'
import { SizeModel } from '@/models/SizeModel';
import { ColorModel } from '@/models/ColorModel';
import { MaterialModel } from '@/models/MaterialModel';
import { CategoryModel } from '@/models/CategoryModel';
import { BrandModel } from '@/models/BrandModel';
import { ProductModel } from '@/models/ProductModel';
import { ProductDetailModel } from '@/models/ProductDetailModel';
import { ImageModel } from '@/models/ImageModel';
import { createProduct, getProductDetailByProductId } from '@/services/product/ProductService';
import axios from 'axios';

@Component({
  name: 'ProductDetail',
  components: {}
})
export default class extends Vue {
  private productId = this.$route.params.productId || '';

  private product: ProductModel = new ProductModel();

  private listProductDetail: ProductDetailModel[] = [];

  private listColor: ColorModel[] = [];
  private listSize: SizeModel[] = [];
  private listMaterial: MaterialModel[] = [];

  private listCategory: CategoryModel[] = [];
  private listBrand: BrandModel[] = [];

  private listSizeIsAvailable: any[] = [];
  private listColorIsAvailable: any[] = [];
  private listMaterialIsAvailable: any[] = [];

  private dialogImageUrl: any = '';
  private dialogVisible: any = false;
  private disabled: any = false;

  private listImage: any = []
  private listImageDelete: any = [];

  private removeTag(value: any) {
    console.log(value)
    console.log('a')
    // switch(field) {
    //   case 'SIZE': 
    //     if(!this.listSizeIsAvailable.find((e: any) => e === id)) {
    //       this.product.sizes = this.product.sizes.filter((e: any) => e === id);
    //     }
    //     return;
    //   case 'COLOR': 
    //     if(!this.listColorIsAvailable.find((e: any) => e === id)) {
    //       this.product.colors = this.product.colors.filter((e: any) => e === id);
    //     }
    //     return;
    //   case 'MATERIAL': 
    //     if(!this.listMaterialIsAvailable.find((e: any) => e === id)) {
    //       this.product.materials = this.product.materials.filter((e: any) => e === id);
    //     }
    //     return;
    // }
  }

  private created() {
    if(this.productId) {
      getProductDetailByProductId(this.productId).then((res: any) => {
        this.product = res.data;
        if(res.data.sizes.length !== 0) {
          this.listSizeIsAvailable = res.data.sizes;
        }
        if(res.data.colors.length !== 0) {
          this.listColorIsAvailable = res.data.colors;
        }
        if(res.data.materials.length !== 0) {
          this.listMaterialIsAvailable = res.data.materials;
        }
        if(res.data.productDetails.length != 0) {
          this.listProductDetail = res.data.productDetails;
        }
        this.selectAttribute();
      })
    }

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

  private async createOrUpdateProduct() {

    const images: any[] = [];
    for (const image of (this.$refs.imageProductUpload as any).uploadFiles) {
      if(image.raw) {
        const data = new FormData();
        data.append("file", image.raw);
        data.append("upload_preset", "vubq-upload");
        data.append("cloud_name", "vubq");
        await axios.post("https://api.cloudinary.com/v1_1/vubq/image/upload", data).then((res: any) => {
          console.log(res)
          const imageNew = new ImageModel();
          imageNew.publicId = res.data.public_id;
          imageNew.url = res.data.url;
          images.push(imageNew);
        })
      } else {
        images.push(image);
      }
    }
    this.product.images = images;

    // if(this.productId) {
    //   console.log('aaaa')
    // } else {
      await createProduct(this.product).then((res: any) => {
        console.log(res);
      })
    // }
  }

  handleRemove(file: any) {
    console.log(file)
    if(!file.raw) {
      this.listImageDelete.push({ 
        id: file.id,
        publicId: file.publicId,
        url: file.url,
        type: file.type,
        secondaryId: file.secondaryId
      });
      this.product.imageDeletes = this.listImageDelete;
    }
    (this.$refs.imageProductUpload as any).handleRemove(file);
  }

  handlePictureCardPreview(file: any) {

    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  private selectAttribute() {

    const list: ProductDetailModel[] = [];

    if(this.product.sizes.length > 0 && this.product.colors.length > 0 && this.product.materials.length > 0) {
      const totalNumberProduct = this.product.sizes.length * this.product.colors.length * this.product.materials.length;

      let iSize = 0;
      let iColor = 0;
      let iMaterial = 0;

      for(let i = 0; i < totalNumberProduct; i++) {

        let maxSize = 1 * this.product.colors.length * this.product.materials.length; 
        // let maxColor = 1 * this.product.sizes.length * this.product.materials.length;

        // Size
        let totalSize = list.filter((e: any) => e.sizeId === this.product.sizes[iSize]).length;
        let size = totalSize < totalNumberProduct / this.product.sizes.length ? this.product.sizes[iSize] : this.product.sizes[++iSize];

        // console.log(i + ": iColor: " + iColor + ", totalSize: " + totalSize);
        // Color
        if(iColor === this.product.colors.length - 1 && totalSize === maxSize) {
          iColor = 0;
        }
        let totalColor = list.filter((e: any) => e.sizeId === size && e.colorId === this.product.colors[iColor] ).length;
        let color = totalColor === maxSize / this.product.colors.length ? this.product.colors[++iColor] : this.product.colors[iColor];

        // Material
        if(iMaterial === this.product.materials.length - 1) {
          iMaterial = 0;
        }
        let totalMaterial = list.filter((e: any) => e.sizeId === size && e.colorId === color && e.materialId === this.product.materials[iMaterial]).length;
        let material = totalMaterial === 1 ? this.product.materials[++iMaterial] : this.product.materials[iMaterial];

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
    if(this.productId) {
      this.product.productDetails = list.map((e: any) => {
        const productDetail = this.listProductDetail.find((x: any) => (
          e.sizeId === x.sizeId && e.colorId === x.colorId && e.materialId === x.materialId
        ))
        if(productDetail) {
          return productDetail;
        } else {
          return e;
        }
      })
    } else {
      this.product.productDetails = list;
    }
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

  private genDisabled(field: string, id: string) {
    if(!this.productId) return false;
    switch (field) {
      case 'SIZE':
        return this.listSizeIsAvailable.find((e: any) => e === id) ? true : false;
      case 'COLOR':
        return this.listColorIsAvailable.find((e: any) => e === id) ? true : false;
      case 'MATERIAL':
        return this.listMaterialIsAvailable.find((e: any) => e === id) ? true : false;
    }
  }
}
</script>

<style lang='scss' scoped>
</style>