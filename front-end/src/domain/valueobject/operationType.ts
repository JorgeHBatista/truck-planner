import { Utils } from "../../utils/utils";

enum OperationType {
    LOAD,
    UNLOAD
}

class OperationTypeVO {
    private static OPERATIONTYPE_NULL_ERROR_MESSAGE: string = "The operation type cannot be null or empty";
    private static OPERATIONTYPE_INVALID_ERROR_MESSAGE: string = "The operation type specified does not exist";

    static validate(ordinal: number): string {
        let error: string = "";
        if (ordinal === null) {
            error = this.OPERATIONTYPE_NULL_ERROR_MESSAGE;
        }
        if (ordinal < 0 || ordinal >= Object.keys(OperationType).length / 2) {
            error = this.OPERATIONTYPE_INVALID_ERROR_MESSAGE;
        }
        return error;
    }

    static random(): OperationType {
        return Utils.randomInt(0, +Object.keys(OperationType) - 1);
    }

    static fromOrdinal(ordinal: number): String {
        return OperationType[ordinal];
    }
}

export { OperationType, OperationTypeVO };

