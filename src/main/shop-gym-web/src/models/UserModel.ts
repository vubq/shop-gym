import { UserStatusEnum } from "./enums/UserStatusEnum";

export class UserModel {
  private id?: string;
  private userName?: string;
  private email?: string;
  private password?: string;
  private phoneNumber?: string;
  private address?: string;
  private fullName?: string;
  private avatar?: string;
  private status?: UserStatusEnum;
  private roles?: string[];

  constructor(init?: Partial<UserModel>) {
    Object.assign(this, init)
  }
}
  