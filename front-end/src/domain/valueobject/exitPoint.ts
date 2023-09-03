import { Utils } from "../../utils/utils";

enum ExitPoint {
  	GATE1, 
    GATE2, 
    GATE3, 
    GATE4, 
    GATE5, 
    GATE6, 
    OCEAN
}

class ExitPointVO {
    private static readonly EXITPOINT_NULL_ERROR_MESSAGE: string = "The exit point cannot be null or empty";
    private static readonly EXITPOINT_INVALID_ERROR_MESSAGE: string = "The exit point specified does not exist";

    static validate(ordinal: number): string {
        let error: string = "";
        if (ordinal === null) {
            error = this.EXITPOINT_NULL_ERROR_MESSAGE;
        }
        if (ordinal < 0 || ordinal > 6) {
            error = this.EXITPOINT_INVALID_ERROR_MESSAGE;
        }
        return error;
    }

    static random(): ExitPoint {
        return Utils.randomInt(0, +Object.keys(ExitPoint) - 1);
    }

    static fromOrdinal(ordinal: number): String {
        return ExitPoint[ordinal];
    }
}

export { ExitPoint, ExitPointVO };