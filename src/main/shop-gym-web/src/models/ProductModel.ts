import { ImageModel } from "./ImageModel";
import { ProductDetailModel } from "./ProductDetailModel";

export class ProductModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;
  public image?: string;
  public price?: number;
  public brandId?: string;
  public categoryId?: string;

  public images?: ImageModel[];
  public productDetails?: ProductDetailModel[];

  public sizes: any[] = [];
  public colors: any[] = [];
  public materials: any[] = [];

  constructor(init?: Partial<ProductModel>) {
    Object.assign(this, init)
  }
}
