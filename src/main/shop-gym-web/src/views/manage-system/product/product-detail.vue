<template>
  
</template>

<script>
import { ResponseCode, Status } from '../../../enums/enums'
import { getAllColor } from '../../../api/color'
import { getAllSize } from '../../../api/size'
import { getAllMaterial } from '../../../api/material'
import { getAllCategory } from '../../../api/category'
import { getAllBrand } from '../../../api/brand'
import axios from 'axios'

export default {
  components: {
    Pagination
  },
  data() {
    return {
      productId: '',
      product: null,
      listProductDetail: [],
      listColor: [],
      listSize: [],
      listMaterial: [],
      listCategory: [],
      listBrand: [],
      listSizeIsAvailable: [],
      listColorIsAvailable: [],
      listMaterialIsAvailable: [],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      listImage: [],
      listImageDelete: []
    }
  },
  methods: {
    async createOrUpdateProduct() {
      const images = []
      for (const image of this.$refs.imageProductUpload.uploadFiles) {
        if(image.raw) {
          const data = new FormData()
          data.append('file', image.raw)
          data.append('upload_preset', 'vubq-upload')
          data.append('cloud_name', 'vubq')
          await axios.post('https://api.cloudinary.com/v1_1/vubq/image/upload', data).then((res) => {
            console.log(res)
            const imageNew = {
              publicId: res.data.public_id,
              url: res.data.url
            }
            images.push(imageNew)
          })
        } else {
          images.push(image)
        }
      }
      this.product.images = images

      // await createProduct(this.product).then((res) => {
      //   console.log(res)
      // })
    },
    handleRemove(file) {
      console.log(file)
      if(!file.raw) {
        this.listImageDelete.push({ 
          id: file.id,
          publicId: file.publicId,
          url: file.url,
          type: file.type,
          secondaryId: file.secondaryId
        })
        this.product.imageDeletes = this.listImageDelete
      }
      this.$refs.imageProductUpload.handleRemove(file)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    selectAttribute() {
      const list = []

      if(this.product.sizes.length > 0 && this.product.colors.length > 0 && this.product.materials.length > 0) {
        const totalNumberProduct = this.product.sizes.length * this.product.colors.length * this.product.materials.length

        let iSize = 0
        let iColor = 0
        let iMaterial = 0

        for(let i = 0; i < totalNumberProduct; i++) {
          let maxSize = 1 * this.product.colors.length * this.product.materials.length
          // let maxColor = 1 * this.product.sizes.length * this.product.materials.length;

          // Size
          let totalSize = list.filter((e) => e.sizeId === this.product.sizes[iSize]).length
          let size = totalSize < totalNumberProduct / this.product.sizes.length ? this.product.sizes[iSize] : this.product.sizes[++iSize]

          // console.log(i + ": iColor: " + iColor + ", totalSize: " + totalSize);
          // Color
          if(iColor === this.product.colors.length - 1 && totalSize === maxSize) {
            iColor = 0
          }
          let totalColor = list.filter((e) => e.sizeId === size && e.colorId === this.product.colors[iColor]).length
          let color = totalColor === maxSize / this.product.colors.length ? this.product.colors[++iColor] : this.product.colors[iColor]

          // Material
          if(iMaterial === this.product.materials.length - 1) {
            iMaterial = 0
          }
          let totalMaterial = list.filter((e) => e.sizeId === size && e.colorId === color && e.materialId === this.product.materials[iMaterial]).length
          let material = totalMaterial === 1 ? this.product.materials[++iMaterial] : this.product.materials[iMaterial]

          const productDetail = {
            sizeId: size,
            colorId: color,
            materialId: material,
            quantity: 0,
            price: this.product.price ? this.product.price : 0,
            index: i
          }

          list.push(productDetail)
        }
      }
      if(this.productId) {
        this.product.productDetails = list.map((e) => {
          const productDetail = this.listProductDetail.find((x) => (
            e.sizeId === x.sizeId && e.colorId === x.colorId && e.materialId === x.materialId
          ))
          if(productDetail) {
            return productDetail
          } else {
            return e
          }
        })
      } else {
        this.product.productDetails = list
      }
    },
    genAttribute(field, id) {
      switch (field) {
        case 'SIZE':
          return this.listSize.find((e) => e.id === id).name
        case 'COLOR':
          return this.listColor.find((e) => e.id === id).name
        case 'MATERIAL':
          return this.listMaterial.find((e) => e.id === id).name
        default:
          ''
      }
    },
    genDisabled(field, id) {
      if(!this.productId) return false
      switch (field) {
        case 'SIZE':
          return this.listSizeIsAvailable.find((e) => e === id) ? true : false
        case 'COLOR':
          return this.listColorIsAvailable.find((e) => e === id) ? true : false
        case 'MATERIAL':
          return this.listMaterialIsAvailable.find((e) => e === id) ? true : false
        default:
          false
      }
    }
  },
  created() {
    // if(this.productId) {
    //   getProductDetailByProductId(this.productId).then((res) => {
    //     this.product = res.data
    //     if(res.data.sizes.length !== 0) {
    //       this.listSizeIsAvailable = res.data.sizes
    //     }
    //     if(res.data.colors.length !== 0) {
    //       this.listColorIsAvailable = res.data.colors
    //     }
    //     if(res.data.materials.length !== 0) {
    //       this.listMaterialIsAvailable = res.data.materials
    //     }
    //     if(res.data.productDetails.length !== 0) {
    //       this.listProductDetail = res.data.productDetails
    //     }
    //     this.selectAttribute()
    //   })
    // }

    getAllColor().then((res) => {
      if(res.data) {
        this.listColor = res.data
      }
    })

    getAllMaterial().then((res) => {
      if(res.data) {
        this.listMaterial = res.data
      }
    })

    getAllSize().then((res) => {
      if(res.data) {
        this.listSize = res.data
      }
    })

    getAllCategory().then((res) => {
      if(res.data) {
        this.listCategory = res.data
      }
    })

    getAllBrand().then((res) => {
      if(res.data) {
        this.listBrand = res.data
      }
    })
  }
}
</script>

<style>
</style>