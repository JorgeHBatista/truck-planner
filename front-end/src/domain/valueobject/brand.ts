import { Utils } from "../../utils/utils";

export class Brand {
  	
  	private readonly _brand: string;
	
  	constructor(brand: string) {
  	    Brand.validate(brand);
  	    this._brand = brand;
  	}
  
  	public static validate(brand: string): void {
		const MAX_LENGTH: number = 20;
  		const BRAND_NULL_ERROR_MESSAGE: string = "Brand cannot be null or empty";
  		const ERROR_MAX_LENGTH: string = `Brand must be at most ${MAX_LENGTH} characters in length`;
  	    if (brand == null || brand.trim() === "") {
  	        throw new Error(BRAND_NULL_ERROR_MESSAGE);
  	    }
  	    if (brand.length > MAX_LENGTH) {
  	        throw new Error(ERROR_MAX_LENGTH);
  	    }
  	}
  
  	get brand(): string {
  	    return this._brand;
  	}
  
  	public static random(): Brand {
  	    const brand: string = Utils.randomString(Utils.randomInt(0, 9) + 1);
  	    return new Brand(brand);
  	}
}
