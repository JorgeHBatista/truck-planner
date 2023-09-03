import { Vessel } from "../../domain/entity/vessel";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export interface VesselRepository {
    save: (vessel: Vessel) => Promise<Either<DataError, Vessel>>;
    
    find: (id: UllUUID) => Promise<Either<DataError, Vessel>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Vessel[]>>;
    
    update: (id: UllUUID, vessel: Vessel) => Promise<Either<DataError, Vessel>>;
    
    delete: (id: UllUUID) => Promise<Either<DataError, Vessel>>;
    
    deleteAll: () => Promise<Either<DataError, Vessel[]>>;
}