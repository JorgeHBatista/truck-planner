import { UllUUID } from '../../utils/ull-uuid';

export class Berth {
    
    private _id: UllUUID = UllUUID.random();
    private _portID: UllUUID;
    private _vesselID: UllUUID;

    constructor(id: UllUUID = UllUUID.random(), port: UllUUID, vessel: UllUUID) {
		this._id = id;
        this._portID = port;
		this._vesselID = vessel;
    }

    get id(): UllUUID {
        return this._id;
    }

    set id(id: UllUUID) {
        this._id = id;
    }

    get PortID(): UllUUID {
        return this._portID;
    }

    set PortID(port: UllUUID) {
        this._portID = port;
    }

    get VesselID(): UllUUID {
        return this._vesselID;
    }

    set VesselID(vessel: UllUUID) {
        this._vesselID = vessel;
    }

    public static random(): Berth {
        return new Berth(
            UllUUID.random(),
			UllUUID.random(),
            UllUUID.random()
        );
    }

    public toJson(): any {
        return {
            "idBerth": this.id.toString(),
            "idPort": this.PortID.toString(),
            "idVessel": this.VesselID.toString()
        };
    }

    public static fromJson(json: any): Berth {
        const id = new UllUUID(json.idBerth);
        const port = new UllUUID(json.idPort);
        const vessel = new UllUUID(json.idVessel);
        return new Berth(id, port, vessel);
    }
}