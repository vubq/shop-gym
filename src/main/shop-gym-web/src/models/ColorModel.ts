export class ColorModel {
  public id?: string;
  public name?: string;
  public description?: string;
  public status?: string;

  constructor(init?: Partial<ColorModel>) {
    Object.assign(this, init)
  }
}
