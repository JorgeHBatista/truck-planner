import { Utils } from "../../utils/utils";

enum EntryPoint {
  	GATE1, 
    GATE2, 
    GATE3, 
    GATE4, 
    GATE5, 
    GATE6, 
    OCEAN
}

class EntryPointVO {
    private static readonly ENTRYPOINT_NULL_ERROR_MESSAGE: string = "The entry point cannot be null or empty";
    private static readonly ENTRYPOINT_INVALID_ERROR_MESSAGE: string = "The entry point specified does not exist";

    static validate(ordinal: number): string {
        let error: string = "";
        if (ordinal === null) {
            error = this.ENTRYPOINT_NULL_ERROR_MESSAGE;
        }
        if (ordinal < 0 || ordinal > 6) {
            error = this.ENTRYPOINT_INVALID_ERROR_MESSAGE;
        }
        return error;
    }

    static random(): EntryPoint {
        return Utils.randomInt(0, +Object.keys(EntryPoint) - 1);
    }

    static fromOrdinal(ordinal: number): String {
        return EntryPoint[ordinal];
    }
}

export { EntryPoint, EntryPointVO };
