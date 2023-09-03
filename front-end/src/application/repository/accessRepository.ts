import { Access } from "../../domain/entity/access";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";

export interface AccessRepository {
    save: (access: Access) => Promise<Either<DataError, Access>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Access[]>>;
    
    deleteAll: () => Promise<Either<DataError, Access[]>>;
}