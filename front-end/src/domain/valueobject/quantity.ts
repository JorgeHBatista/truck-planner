export class Quantity {

    private readonly _value: number;
    private static MAX_VALUE: number = 10000;

    constructor(value: number) {
        this.validate(value);
        this._value = value;
    }

    private validate(value: number): void {
        const QUANTITY_NEGATIVE_ERROR_MESSAGE: string = "Quantity must not be a negative number";
        if (value < 0) {
            throw new Error(QUANTITY_NEGATIVE_ERROR_MESSAGE);
        }
    }

    get value(): number {
        return this._value;
    }

    public static random(): Quantity {
        const rand = Math.floor(Math.random() * Quantity.MAX_VALUE);
        return new Quantity(rand);
    }
}
