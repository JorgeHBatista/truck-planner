import { AccessRepository } from "../repository/accessRepository";
import { Access } from "../../domain/entity/access";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";

export class AccessService {
    private accessRepository: AccessRepository;

    constructor(accessRepository: AccessRepository) {
        this.accessRepository = accessRepository;
    }
    
    public save = (newAccess: Access): Promise<Either<DataError, Access>> => {
        return this.accessRepository.save(newAccess);
    }

    public findAll = (pageRequest: any): Promise<Either<DataError, Access[]>> => {
        return this.accessRepository.findAll(pageRequest);
    }

    public deleteAll = (): Promise<Either<DataError, Access[]>> => {
        return this.accessRepository.deleteAll();
    }
}