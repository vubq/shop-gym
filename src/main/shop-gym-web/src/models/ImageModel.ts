export class ImageModel {
  public id?: string;
  public publicId?: string;
  public url?: string;
  public type?: string;
  public secondaryId?: string;

  constructor(init?: Partial<ImageModel>) {
    Object.assign(this, init)
  }
}