import { PortRepository } from "../repository/portRepository";
import { Port } from "../../domain/entity/port";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export class PortService {
    private portRepository: PortRepository;

    constructor(portRepository: PortRepository) {
        this.portRepository = portRepository;
    }
    
    public save = (newPort: Port): Promise<Either<DataError, Port>> => {
        return this.portRepository.save(newPort);
    }
    
    public find = (id: UllUUID): Promise<Either<DataError, Port>> => {
        return this.portRepository.find(id);
    }
    
    public findAll = (pageRequest: any): Promise<Either<DataError, Port[]>> => {
        return this.portRepository.findAll(pageRequest);
    }

    public update = (id: UllUUID, updatedPort: Port): Promise<
        Either<DataError, Port>> => {
            return this.portRepository.update(id, updatedPort);
    }
    
    public delete = (id: UllUUID): Promise<Either<DataError, Port>> => {
        return this.portRepository.delete(id);
    }

    public deleteAll = (): Promise<Either<DataError, Port[]>> => {
        return this.portRepository.deleteAll();
    }
}