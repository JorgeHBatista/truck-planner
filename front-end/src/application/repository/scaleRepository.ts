import { Scale } from "../../domain/entity/scale";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from '../../utils/ull-uuid';

export interface ScaleRepository {
    save: (scale: Scale) => Promise<Either<DataError, Scale>>;

    find: (id: UllUUID) => Promise<Either<DataError, Scale>>;
    
    findAll: (pageRequest: any) => Promise<Either<DataError, Scale[]>>;
    
    deleteAll: () => Promise<Either<DataError, Scale[]>>;
}