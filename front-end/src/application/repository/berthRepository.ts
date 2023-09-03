import { Berth } from "../../domain/entity/berth";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";

export interface BerthRepository {
    save: (berth: Berth) => Promise<Either<DataError, Berth>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Berth[]>>;
    
    deleteAll: () => Promise<Either<DataError, Berth[]>>;
}