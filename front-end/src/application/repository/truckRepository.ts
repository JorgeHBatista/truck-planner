import { Truck } from "../../domain/entity/truck";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export interface TruckRepository {
    save: (truck: Truck) => Promise<Either<DataError, Truck>>;
    
    find: (id: UllUUID) => Promise<Either<DataError, Truck>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Truck[]>>;
    
    update: (id: UllUUID, truck: Truck) => Promise<Either<DataError, Truck>>;
    
    delete: (id: UllUUID) => Promise<Either<DataError, Truck>>;
    
    deleteAll: () => Promise<Either<DataError, Truck[]>>;
}