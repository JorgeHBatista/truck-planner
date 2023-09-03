import { UllUUID } from '../../utils/ull-uuid';
import { Utils } from '../../utils/utils';
import { Operation } from './operation';
import { DateUtils } from '../../utils/dateUtils';

export class Scale {
    
    private _id: UllUUID = UllUUID.random();
    private _startingTime: Date;
    private _finishingTime: Date;
	private _operations: Operation[];

    constructor(id: UllUUID = UllUUID.random(), startingTime: Date, finishingTime: Date, operations: Operation[]) {
		this._id = id;
		this._startingTime = startingTime;
		this._finishingTime = finishingTime;
		this._operations = operations;
	}

    get id(): UllUUID {
        return this._id;
    }

    set id(id: UllUUID) {
        this._id = id;
    }

	get startingTime(): Date {
		return this._startingTime;
	}

	set startingTime(startingTime: Date) {
        this._startingTime = startingTime;
    }

	get finishingTime(): Date {
        return this._finishingTime;
    }

    set finishingTime(finishingTime: Date) {
        this._finishingTime = finishingTime;
    }

	get operations(): Operation[] {
        return this._operations;
    }

    set operations(operations: Operation[]) {
        this._operations = operations;
    }

    public static random(): Scale {
        return new Scale(
            UllUUID.random(),
			Utils.randomDate(),
            Utils.randomDate(),
			new Array(Utils.randomInt(1, 10)).fill(null).map(() => Operation.random())
        );
    }

    public toJson(): any {
        return {
            "idScale": this.id.toString(),
            "startingTime": DateUtils.toJson(this.startingTime),
            "finishingTime": DateUtils.toJson(this.finishingTime),
            "operations": this.operations.map((operation) => operation.toJson())
        };
    }

    public static fromJson(json: any): Scale {
        const id = new UllUUID(json.idScale);
        const startingTime = new Date(json.startingTime);
        const finishingTime = new Date(json.finishingTime);
        const operations = json.operations.map((operation: any) => Operation.fromJson(operation));
        return new Scale(id, startingTime, finishingTime, operations);
    }
}