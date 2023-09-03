import { UllUUID } from '../../utils/ull-uuid';
import { Utils } from '../../utils/utils';
import { Scale } from './scale';

export class Vessel {
    
    private _id: UllUUID = UllUUID.random();
    private _scales: Scale[];

    constructor(id: UllUUID = UllUUID.random(), scales: Scale[]) {
		this._id = id;
		this._scales = scales;
    }

    get id(): UllUUID {
        return this._id;
    }
    
    set id(id: UllUUID) {
        this._id = id;
    }

	get scales(): Scale[] {
		return this._scales;
	}

	set scales(scales: Scale[]) {
        this._scales = scales;
    }

    public static random(): Vessel {
        return new Vessel(
			UllUUID.random(),
			new Array(Utils.randomInt(1, 10)).fill(null).map(() => Scale.random())
        );
    }

    public toJson(): any {
        return {
            "idVessel": this.id.toString(),
            "scales": this.scales.map((scale) => scale.toJson())
        };
    }

    public static fromJson(json: any): Vessel {
        const id = new UllUUID(json.idVessel);
        const scales = json.scales.map((scale: any) => Scale.fromJson(scale));
        return new Vessel(id, scales);
    }
}