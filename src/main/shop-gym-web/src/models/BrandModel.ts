export class BrandModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;

  constructor(init?: Partial<BrandModel>) {
    Object.assign(this, init)
  }
}
