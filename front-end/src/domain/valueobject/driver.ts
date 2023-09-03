import { Utils } from "../../utils/utils";

export class Driver {

    public static readonly MAX_LENGTH: number = 50;
    public static readonly REGULAR_EXPRESSION: string = ".+\\s.+";
    private static readonly DRIVER_NULL_ERROR_MESSAGE: string = "Driver cannot be null or empty";
    private static readonly DRIVER_SPACE_ERROR_MESSAGE: string = "Driver must have name and surname stored";
    public static readonly ERROR_MAX_LENGTH: string = `Driver must be at most ${Driver.MAX_LENGTH} characters in length`;
    private readonly _driver: string;

    constructor(driver: string) {
        Driver.validate(driver);
        this._driver = driver;
    }

    public static validate(driver: string): void {
        if (driver == null || driver === "") {
            throw new Error(Driver.DRIVER_NULL_ERROR_MESSAGE);
        }
        const length: number = driver.length;
        if (length > Driver.MAX_LENGTH) {
            throw new Error(Driver.ERROR_MAX_LENGTH);
        }
        if (!driver.match(Driver.REGULAR_EXPRESSION)) {
            throw new Error(Driver.DRIVER_SPACE_ERROR_MESSAGE);
        }
    }

    get driver(): string {
        return this._driver;
    }

    static random(): Driver {
        let driver: string = Utils.randomString(Utils.randomInt(0, 9) + 1);
        driver += " " + Utils.randomString(Utils.randomInt(0, 9) + 1);
        return new Driver(driver);
    }
}
