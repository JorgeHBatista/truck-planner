import { Port } from "../../domain/entity/port";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export interface PortRepository {
    save: (port: Port) => Promise<Either<DataError, Port>>;
    
    find: (id: UllUUID) => Promise<Either<DataError, Port>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Port[]>>;
    
    update: (id: UllUUID, port: Port) => Promise<Either<DataError, Port>>;
    
    delete: (id: UllUUID) => Promise<Either<DataError, Port>>;
    
    deleteAll: () => Promise<Either<DataError, Port[]>>;
}