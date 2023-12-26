import { OrderDetailModel } from "./OrderDetailModel";
import { VoucherModel } from "./VoucherModel";
import { OrderStatusEnum } from "./enums/OrderStatusEnum";
import { OrderTypeEnum } from "./enums/OrderTypeEnum";

export class OrderModel {
  public id?: string;
  public type?: OrderTypeEnum;
  public fullNameCustomer?: string;
  public phoneNumber?: string;
  public address?: string;
  public createdBy?: string;
  public createdDate?: Date;
  public completedDate?: Date;
  public totalAmount?: number;
  public voucherId?: string;
  public status?: OrderStatusEnum;

  public orderDetails: OrderDetailModel[] = [];

  constructor(init?: Partial<OrderModel>) {
    Object.assign(this, init)
  }
}
