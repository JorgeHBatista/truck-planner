export class Utils {

	public static randomString(length: number): string {
		let result = '';
    	const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    	const charactersLength = characters.length;

    	for ( let i = 0; i < length; i++ ) {
    	  result += characters.charAt(Math.floor(Math.random() * charactersLength));
    	}

    	return result;
  	}

	public static randomInt(min: number, max: number): number {
		return Math.floor(Math.random() * (max - min + 1)) + min;
	}

	public static randomDate(): Date {
		return new Date(new Date().valueOf() - Math.random()*(1e+12));
	}
}