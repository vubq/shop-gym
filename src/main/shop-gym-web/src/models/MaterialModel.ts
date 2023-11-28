export class MaterialModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;

  constructor(init?: Partial<MaterialModel>) {
    Object.assign(this, init)
  }
}
