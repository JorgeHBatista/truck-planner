//import utils class from utils.ts to use the methods from that class
import { Utils } from '../../utils/utils';

export class Plate {
      
    private readonly _plate: string;

    constructor(plate: string) {
        this.validate(plate);
        this._plate = plate;
    }

    private validate(plate: string): void {
        const PLATE_NULL_ERROR_MESSAGE = "Plate cannot be null or empty";
        const PLATE_LENGTH_ERROR_MESSAGE = "Plate length is invalid";
        const MIN_PLATE_NUMBER = 0;
        const MAX_PLATE_NUMBER = 9999;
        const PLATE_NUMBER_LENGTH = 4;
        const PLATE_LETTERS_LENGTH = 3;
        const PLATE_NUMBER_ERROR_MESSAGE = "Plate number is out of range";
        const PLATE_LETTERS_ERROR_MESSAGE = "Plate letters are invalid";
      
        if (plate == null || plate === "") {
            throw new Error(PLATE_NULL_ERROR_MESSAGE);
        }
      
        if (plate.length < PLATE_NUMBER_LENGTH + PLATE_LETTERS_LENGTH) {
            throw new Error(PLATE_LENGTH_ERROR_MESSAGE);
        }
      
        const number = parseInt(plate.substring(0, PLATE_NUMBER_LENGTH), 10);
        if (number < MIN_PLATE_NUMBER || number > MAX_PLATE_NUMBER) {
            throw new Error(PLATE_NUMBER_ERROR_MESSAGE);
        }
      
        const letters = plate.substring(PLATE_NUMBER_LENGTH);
        if (!RegExp(`[A-Z]{${PLATE_LETTERS_LENGTH}}`).test(letters)) {
            throw new Error(PLATE_LETTERS_ERROR_MESSAGE);
        }
    }
    
    get plate(): string {
        return this._plate;
    }
    
	// the format the random plate must follow should be 0000AAA
    public static random(): Plate {
        return new Plate(
			Utils.randomInt(0, 9999).toString().padStart(4, '0') +
			Utils.randomString(3).toUpperCase()
        );
    }
}