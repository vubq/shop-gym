import { UserModel } from "./UserModel";
import { StatusEnum } from "./enums/StatusEnum";
import { VoucherTypeEnum } from "./enums/VoucherTypeEnum";

export class VoucherModel {
  public id?: string;
  public code?: string;
  public type?: VoucherTypeEnum;
  public value?: number;
  public startDate?: Date;
  public endDate?: Date;
  public createdBy?: UserModel;
  public status?: StatusEnum;

  constructor(init?: Partial<VoucherModel>) {
    Object.assign(this, init)
  }
}
