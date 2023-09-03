import { BerthRepository } from "../repository/berthRepository";
import { Berth } from "../../domain/entity/berth";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";

export class BerthService {
    private berthRepository: BerthRepository;

    constructor(berthRepository: BerthRepository) {
        this.berthRepository = berthRepository;
    }
    
    public save = (newBerth: Berth): Promise<Either<DataError, Berth>> => {
        return this.berthRepository.save(newBerth);
    }

    public findAll = (pageRequest: any): Promise<Either<DataError, Berth[]>> => {
        return this.berthRepository.findAll(pageRequest);
    }

    public deleteAll = (): Promise<Either<DataError, Berth[]>> => {
        return this.berthRepository.deleteAll();
    }
}