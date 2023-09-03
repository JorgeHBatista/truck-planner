import { TruckRepository } from "../repository/truckRepository";
import { Truck } from "../../domain/entity/truck";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export class TruckService {
    private truckRepository: TruckRepository;

    constructor(truckRepository: TruckRepository) {
        this.truckRepository = truckRepository;
    }
    
    public save = (newTruck: Truck): Promise<Either<DataError, Truck>> => {
        return this.truckRepository.save(newTruck);
    }
    
    public find = (id: UllUUID): Promise<Either<DataError, Truck>> => {
        return this.truckRepository.find(id);
    }
    
    public findAll = (pageRequest: any): Promise<Either<DataError, Truck[]>> => {
        return this.truckRepository.findAll(pageRequest);
    }

    public update = (id: UllUUID, updatedTruck: Truck): Promise<
        Either<DataError, Truck>> => {
            return this.truckRepository.update(id, updatedTruck);
    }
    
    public delete = (id: UllUUID): Promise<Either<DataError, Truck>> => {
        return this.truckRepository.delete(id);
    }

    public deleteAll = (): Promise<Either<DataError, Truck[]>> => {
        return this.truckRepository.deleteAll();
    }
}