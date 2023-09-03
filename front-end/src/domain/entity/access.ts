import { UllUUID } from '../../utils/ull-uuid';
import { Utils } from '../../utils/utils';
import { EntryPoint } from '../valueobject/entryPoint';
import { EntryPointVO } from '../valueobject/entryPoint';
import { ExitPoint } from '../valueobject/exitPoint';
import { ExitPointVO } from '../valueobject/exitPoint';
import { IdentificationType } from '../valueobject/identificationType';
import { IdentificationTypeVO } from '../valueobject/identificationType';
import { DateUtils } from '../../utils/dateUtils';

export class Access {
    
    private _id: UllUUID = UllUUID.random();
    private _entryDate: Date;
    private _exitDate: Date;
    private _entryPoint: EntryPoint;
    private _exitPoint: ExitPoint;
    private _identificationType: IdentificationType;
    private _truckID: UllUUID;
    private _portID: UllUUID;

    constructor(id: UllUUID = UllUUID.random(), EntryDate: Date, exitDate: Date, entryPoint: EntryPoint, exitPoint: ExitPoint, identificationType: IdentificationType, truck: UllUUID, port: UllUUID) {
        this._id = id;
        this._entryDate = EntryDate;
        this._exitDate = exitDate;
        this._entryPoint = entryPoint;
        this._exitPoint = exitPoint;
		this._identificationType = identificationType;
		this._truckID = truck;
		this._portID = port;
    }
    
    get id(): UllUUID {
        return this._id;
    }
    
    set id(id: UllUUID) {
        this._id = id;
    }
    
    set entryDate(entryDate: Date) {
        this._entryDate = entryDate;
    }

    get entryDate(): Date {
        return this._entryDate;
    }

    get exitDate(): Date {
        return this._exitDate;
    }

    set exitDate(exitDate: Date) {
        this._exitDate = exitDate;
    }

    get entryPoint(): EntryPoint {
        return this._entryPoint;
    }

    set entryPoint(entryPoint: EntryPoint) {
        this._entryPoint = entryPoint;
    }

	get exitPoint(): ExitPoint {
        return this._exitPoint;
    }

    set exitPoint(exitPoint: ExitPoint) {
        this._exitPoint = exitPoint;
    }

    get identificationType(): IdentificationType {
        return this._identificationType;
    }

    set identificationType(identificationType: IdentificationType) {
        this._identificationType = identificationType;
    }

	get truckID(): UllUUID {
        return this._truckID;
    }

    set truckID(truck: UllUUID) {
        this._truckID = truck;
    }

    get portID(): UllUUID {
        return this._portID;
    }

    set portID(port: UllUUID) {
        this._portID = port;
    }

    public static random(): Access {
        return new Access(
            UllUUID.random(),
        	Utils.randomDate(),
        	Utils.randomDate(),
        	EntryPointVO.random(),
        	ExitPointVO.random(),
			IdentificationTypeVO.random(),
			UllUUID.random(),
			UllUUID.random()
        );
    }

    public toJson(): any {
        return {
            "idAccess": this.id.toString(),
            "idTruck": this.truckID.toString(),
            "idPort": this.portID.toString(),
            "entryDate": DateUtils.toJson(this.entryDate),
            "exitDate": DateUtils.toJson(this.exitDate),
            "entryPoint": EntryPointVO.fromOrdinal(this.entryPoint),
            "exitPoint": ExitPointVO.fromOrdinal(this.exitPoint),
            "identificationType": IdentificationTypeVO.fromOrdinal(this.identificationType)
        };
    }

    public static fromJson(json: any): Access {
        const id = new UllUUID(json.idAccess);
        const truckID = (json.idTruck);
        const portID = (json.idPort);
        const entryDate = new Date(json.entryDate);
        const exitDate = new Date(json.exitDate);
        const entryPoint = EntryPoint[json.entryPoint as keyof typeof EntryPoint]
        const exitPoint = ExitPoint[json.exitPoint as keyof typeof ExitPoint]
        const identificationType = IdentificationType[json.identificationType as keyof typeof IdentificationType]
        return new Access(id, entryDate, exitDate, entryPoint, exitPoint, identificationType, truckID, portID);
    }
}