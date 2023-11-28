import { BrandModel } from "./BrandModel";
import { CategoryModel } from "./CategoryModel";

export class ProductModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;
  public image?: string;
  public price?: number;
  public brand?: BrandModel;
  public category?: CategoryModel;

  public brandId?: string;
  public categoryId?: string;

  constructor(init?: Partial<ProductModel>) {
    Object.assign(this, init)
  }
}
