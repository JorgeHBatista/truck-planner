import { Utils } from "../../utils/utils";

enum IdentificationType {
  	QR, 
    CARD, 
    UNKNOWN
}

class IdentificationTypeVO {
    
	private static readonly IDENTIFICATION_NULL_ERROR_MESSAGE =
            "The identification type cannot be null or empty";
    private static readonly IDENTIFICATION_INVALID_ERROR_MESSAGE =
            "The identification type specified does not exist";
	
	static validate(ordinal: number): string {
		let error = "";
		if (ordinal == null)
			error = this.IDENTIFICATION_NULL_ERROR_MESSAGE;
		if (ordinal < 0 || ordinal > 2)
			error = this.IDENTIFICATION_INVALID_ERROR_MESSAGE;
		return error;
    }

    static random(): IdentificationType {
        return Utils.randomInt(0, +Object.keys(IdentificationType) - 1);
    }

	static fromOrdinal(ordinal: number): String {
        return IdentificationType[ordinal];
    }
}

export { IdentificationType, IdentificationTypeVO };