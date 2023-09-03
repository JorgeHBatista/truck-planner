import { ScaleRepository } from "../repository/scaleRepository";
import { Scale } from "../../domain/entity/scale";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from '../../utils/ull-uuid';

export class ScaleService {
    private scaleRepository: ScaleRepository;

    constructor(scaleRepository: ScaleRepository) {
        this.scaleRepository = scaleRepository;
    }
    
    public save = (newScale: Scale): Promise<Either<DataError, Scale>> => {
        return this.scaleRepository.save(newScale);
    }

    public find = (id: UllUUID): Promise<Either<DataError, Scale>> => {
        return this.scaleRepository.find(id);
    }

    public findAll = (pageRequest: any): Promise<Either<DataError, Scale[]>> => {
        return this.scaleRepository.findAll(pageRequest);
    }

    public deleteAll = (): Promise<Either<DataError, Scale[]>> => {
        return this.scaleRepository.deleteAll();
    }
}