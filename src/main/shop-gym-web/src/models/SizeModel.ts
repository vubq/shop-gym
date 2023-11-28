export class SizeModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;

  constructor(init?: Partial<SizeModel>) {
    Object.assign(this, init)
  }
}
