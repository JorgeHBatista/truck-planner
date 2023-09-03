export class Coordinate {

	private readonly _latitude: number;
	private readonly _longitude: number;
	private static readonly MAX_LATITUDE: number = 90.0;
	private static readonly MIN_LATITUDE: number = -90.0;
	private static readonly MAX_LONGITUDE: number = 180.0;
	private static readonly MIN_LONGITUDE: number = -180.0;
	private static readonly LATITUDE_ERROR_MESSAGE: string = "Latitude must be between -90 and 90 degrees";
	private static readonly LONGITUDE_ERROR_MESSAGE: string = "Longitude must be between -180 and 180 degrees";
  
	constructor(latitude: number, longitude: number) {
	  	Coordinate.validate(latitude, longitude);
	  	this._latitude = latitude;
	  	this._longitude = longitude;
	}
  
	private static validate(latitude: number, longitude: number): void {
	  	if (latitude < Coordinate.MIN_LATITUDE || latitude > Coordinate.MAX_LATITUDE) {
			throw new Error(Coordinate.LATITUDE_ERROR_MESSAGE);
	  	}
	  	if (longitude < Coordinate.MIN_LONGITUDE || longitude > Coordinate.MAX_LONGITUDE) {
			throw new Error(Coordinate.LONGITUDE_ERROR_MESSAGE);
	  	}
	}
  
	get latitude(): number {
	  	return this._latitude;
	}
  
	get longitude(): number {
	  	return this._longitude;
	}
  
	toString(): string {
	  	return `${this._latitude}, ${this._longitude}`;
	}
  
	static random(): Coordinate {
	  	const latitude = Math.random() * (Coordinate.MAX_LATITUDE - Coordinate.MIN_LATITUDE) + Coordinate.MIN_LATITUDE;
	  	const longitude = Math.random() * (Coordinate.MAX_LONGITUDE - Coordinate.MIN_LONGITUDE) + Coordinate.MIN_LONGITUDE;
	  	return new Coordinate(latitude, longitude);
	}
}  