import { ImageModel } from "./ImageModel";

export class ProductDetailModel {
  public id?: string;
  public code?: string;
  public price?: number;
  public quantity?: number;
  public productId?: string;
  public sizeId?: string;
  public colorId?: string;
  public materialId?: string;

  public images?: ImageModel[];

  public index?: number;

  constructor(init?: Partial<ProductDetailModel>) {
    Object.assign(this, init)
  }
}
