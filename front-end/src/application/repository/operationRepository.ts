import { Operation } from "../../domain/entity/operation";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from '../../utils/ull-uuid';

export interface OperationRepository {
    save: (operation: Operation) => Promise<Either<DataError, Operation>>;

    find: (id: UllUUID) => Promise<Either<DataError, Operation>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Operation[]>>;
    
    deleteAll: () => Promise<Either<DataError, Operation[]>>;
}