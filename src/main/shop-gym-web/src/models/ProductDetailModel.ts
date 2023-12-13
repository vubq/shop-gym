import { ColorModel } from "./ColorModel";
import { ImageModel } from "./ImageModel";
import { MaterialModel } from "./MaterialModel";
import { ProductModel } from "./ProductModel";
import { SizeModel } from "./SizeModel";

export class ProductDetailModel {
  public id?: string;
  public code?: string;
  public price?: number;
  public quantity?: number;
  public product?: ProductModel;
  public size?: SizeModel;
  public color?: ColorModel;
  public material?: MaterialModel;

  public images?: ImageModel[];

  public index?: number;

  public productId?: string;
  public sizeId?: string;
  public colorId?: string;
  public materialId?: string;

  constructor(init?: Partial<ProductDetailModel>) {
    Object.assign(this, init)
  }
}
