import { OperationRepository } from "../repository/operationRepository";
import { Operation } from "../../domain/entity/operation";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from '../../utils/ull-uuid';

export class OperationService {
    private operationRepository: OperationRepository;

    constructor(operationRepository: OperationRepository) {
        this.operationRepository = operationRepository;
    }
    
    public save = (newOperation: Operation): Promise<Either<DataError, Operation>> => {
        return this.operationRepository.save(newOperation);
    }

    public find = (id: UllUUID): Promise<Either<DataError, Operation>> => {
        return this.operationRepository.find(id);
    }

    public findAll = (pageRequest: any): Promise<Either<DataError, Operation[]>> => {
        return this.operationRepository.findAll(pageRequest);
    }

    public deleteAll = (): Promise<Either<DataError, Operation[]>> => {
        return this.operationRepository.deleteAll();
    }
}