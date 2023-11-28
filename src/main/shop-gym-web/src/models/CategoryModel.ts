export class CategoryModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;

  constructor(init?: Partial<CategoryModel>) {
    Object.assign(this, init)
  }
}
