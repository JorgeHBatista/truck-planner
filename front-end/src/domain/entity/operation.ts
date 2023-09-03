import { UllUUID } from '../../utils/ull-uuid';
import { Cargo } from '../valueobject/cargo';
import { CargoVO } from '../valueobject/cargo';
import { OperationType } from '../valueobject/operationType';
import { OperationTypeVO } from '../valueobject/operationType';
import { Quantity } from '../valueobject/quantity';

export class Operation {

    private _id: UllUUID = UllUUID.random();
    private _cargo: Cargo;
    private _operationType: OperationType;
    private _quantity: Quantity;

    constructor(id: UllUUID = UllUUID.random(), cargo: Cargo, operationType: OperationType, quantity: Quantity) {
        this._id = id;
        this._cargo = cargo;
        this._operationType = operationType;
        this._quantity = quantity;
    }

    get id(): UllUUID {
        return this._id;
    }

    set id(id: UllUUID) {
        this._id = id;
    }
    
    get cargo(): Cargo {
        return this._cargo;
    }

    set cargo(cargo: Cargo) {
        this._cargo = cargo;
    }

    get operationType(): OperationType {
        return this._operationType;
    }

    set operationType(operationType: OperationType) {
        this._operationType = operationType;
    }

    get quantity(): Quantity {
        return this._quantity;
    }

    set quantity(quantity: Quantity) {
        this._quantity = quantity;
    }

    public static random(): Operation {
        return new Operation(
            UllUUID.random(),
            CargoVO.random(),
            OperationTypeVO.random(),
            Quantity.random(),
        );
    }

    public toJson(): any {
        return {
            "idOperation": this.id.toString(),
            "cargo": CargoVO.fromOrdinal(this.cargo),
            "operationType": OperationTypeVO.fromOrdinal(this.operationType),
            "quantity": this.quantity.value
        };
    }

    public static fromJson(json: any): Operation {
        const id = new UllUUID(json.idOperation);
        const cargo = Cargo[json.cargo as keyof typeof Cargo];
        const operationType = OperationType[json.operationType as keyof typeof OperationType];
        const quantity = new Quantity(json.quantity);
        return new Operation(id, cargo, operationType, quantity);
    }
}