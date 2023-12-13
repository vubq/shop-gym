import { ProductDetailModel } from "./ProductDetailModel";
import { ProductModel } from "./ProductModel";
import { StatusEnum } from "./enums/StatusEnum";

export class OrderDetailModel {
	public id?: string;
	public orderId?: string;
	public productDetailId?: string;
	public quantity?: number;
	public totalAmount?: number;
	public isVoucher?: boolean;
	public status?: StatusEnum;
	
	public productDetail?: ProductDetailModel;

  constructor(init?: Partial<OrderDetailModel>) {
    Object.assign(this, init)
  }
}