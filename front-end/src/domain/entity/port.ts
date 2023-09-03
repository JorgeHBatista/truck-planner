import { UllUUID } from '../../utils/ull-uuid';
import { PortName } from '../valueobject/portName';
import { Coordinate } from '../valueobject/coordinate';

export class Port {
    
    private _id: UllUUID = UllUUID.random();
    private _name: PortName;
    private _coordinate: Coordinate;

    constructor(id: UllUUID = UllUUID.random(), name: PortName, coordinate: Coordinate) {
        this._id = id;
        this._name = name;
        this._coordinate = coordinate;
    }
    
    get id(): UllUUID {
        return this._id;
    }
    
    set id(id: UllUUID) {
        this._id = id;
    }
    
    set name(name: PortName) {
        this._name = name;
    }

    get name(): PortName {
        return this._name;
    }

    get coordinate(): Coordinate {
        return this._coordinate;
    }

    set coordinate(coordinate: Coordinate) {
        this._coordinate = coordinate;
    }

    public static random(): Port {
        return new Port(
            UllUUID.random(),
            PortName.random(),
            Coordinate.random(),
        );
    }

    public toJson(): any {
        return {
            "idPort": this.id.toString(),
            "name": this.name.portName,
            "coordinates": this.coordinate.toString(),
        };
    }

    public static fromJson(json: any): Port {
        const id = new UllUUID(json.idPort);
        const name = new PortName(json.name);
        const coordinate = (json.coordinates);
        const latitude = coordinate.split(",")[0];
        const longitude = coordinate.split(",")[1];
        const coordinates = new Coordinate(latitude, longitude);
        let port = new Port(id, name, coordinates);
        return port;
    }
}