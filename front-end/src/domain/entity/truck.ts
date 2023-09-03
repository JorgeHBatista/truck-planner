import { UllUUID } from '../../utils/ull-uuid';
import { Utils } from '../../utils/utils';
import { Plate } from '../valueobject/plate';
import { Brand } from '../valueobject/brand';
import { Driver } from '../valueobject/driver';

export class Truck {
    
    private _id: UllUUID = UllUUID.random();
    private _plate: Plate;
    private _brand: Brand;
    private _driver: Driver;
    private _isUnload: boolean;

    constructor(id: UllUUID = UllUUID.random(), plate: Plate, brand: Brand, driver: Driver, isUnload: boolean) {
        this._id = id;
        this._plate = plate;
        this._brand = brand;
        this._driver = driver;
        this._isUnload = isUnload;
    }
    
    get id(): UllUUID {
        return this._id;
    }
    
    set id(id: UllUUID) {
        this._id = id;
    }
    
    set plate(plate: Plate) {
        this._plate = plate;
    }

    get plate(): Plate {
        return this._plate;
    }

    get brand(): Brand {
        return this._brand;
    }

    set brand(brand: Brand) {
        this._brand = brand;
    }

    get driver(): Driver {
        return this._driver;
    }

    set driver(driver: Driver) {
        this._driver = driver;
    }

    get isUnload(): boolean {
        return this._isUnload;
    }    

    set isUnload(isUnload: boolean) {
        this._isUnload = isUnload;
    }

    public static random(): Truck {
        return new Truck(
            UllUUID.random(),
            Plate.random(),
            Brand.random(),
            Driver.random(),
            Utils.randomInt(0, 1) === 0 ? false : true
        );
    }

    public toJson(): any {
        return {
            "idTruck": this.id.toString(),
            "plate": this.plate.plate,
            "brand": this.brand.brand,
            "driver": this.driver.driver,
            "unload": this.isUnload
        };
    }

    public static fromJson(json: any): Truck {
        return new Truck(
            new UllUUID(json.idTruck),
            new Plate(json.plate),
            new Brand(json.brand),
            new Driver(json.driver),
            json.unload
        );
    }
}