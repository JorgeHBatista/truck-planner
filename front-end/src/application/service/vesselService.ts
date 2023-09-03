import { VesselRepository } from "../repository/vesselRepository";
import { Vessel } from "../../domain/entity/vessel";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";

export class VesselService {
    private vesselRepository: VesselRepository;

    constructor(vesselRepository: VesselRepository) {
        this.vesselRepository = vesselRepository;
    }
    
    public save = (newVessel: Vessel): Promise<Either<DataError, Vessel>> => {
        return this.vesselRepository.save(newVessel);
    }
    
    public find = (id: UllUUID): Promise<Either<DataError, Vessel>> => {
        return this.vesselRepository.find(id);
    }
    
    public findAll = (pageRequest: any): Promise<Either<DataError, Vessel[]>> => {
        return this.vesselRepository.findAll(pageRequest);
    }

    public update = (id: UllUUID, updatedVessel: Vessel): Promise<
        Either<DataError, Vessel>> => {
            return this.vesselRepository.update(id, updatedVessel);
    }
    
    public delete = (id: UllUUID): Promise<Either<DataError, Vessel>> => {
        return this.vesselRepository.delete(id);
    }

    public deleteAll = (): Promise<Either<DataError, Vessel[]>> => {
        return this.vesselRepository.deleteAll();
    }
}