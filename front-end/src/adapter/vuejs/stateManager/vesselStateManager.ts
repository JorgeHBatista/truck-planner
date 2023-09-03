import { defineStore } from 'pinia';
import { Vessel } from '../../../domain/entity/vessel';
import { VesselService } from '../../../application/service/vesselService';
import { VesselRestRepository } from '../../rest/vesselRestRepository';
import { DataError } from '../../../utils/ull-data-error';
import { UllUUID } from '../../../utils/ull-uuid';

export const useVesselStateManager = defineStore('vessels', {
    state: () => ({ 
        vessels: [] as Vessel[], 
        service: new VesselService(new VesselRestRepository())
    }),
    getters: {
        getVessels: (state) => {
            return state.vessels;
        },
    },
    actions:{
        async save(newVessel: Vessel) {
            try {
                const response = await this.service.save(newVessel);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getVessels.push(data as Vessel);
                    }
                )
            } catch (error) {
                throw error;
            }
        },
        async find(id: UllUUID) {
            const response = await this.service.find(id);
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    this.vessels = [data as Vessel];
                }
            )
        },
        async findAll(pageRequest: any) {
            try {
                const response = await this.service.findAll(pageRequest);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.vessels = data as Vessel[];
                    }
                )
            } catch (error) {
                throw error
            }
        },
        async update(id: UllUUID, updatedVessel: Vessel) {
            const response = await this.service.update(id, updatedVessel);
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    const index = this.findVessel(id);
                    if (index !== -1) {
                        this.vessels.splice(index, 1, data as Vessel);
                    }
                }
            )
        },
        async delete(id: UllUUID) {
            const response = await this.service.delete(id);
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    const index = this.findVessel(id);
                    if (index !== -1) {
                        this.vessels.splice(index, 1);
                    }
                    return data;
                }
            )
        },
        async deleteAll() {
            const response = await this.service.deleteAll();
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    this.vessels = [];
                    return data;
                }
            )
        },
        handleError(error: DataError): string {
            let errorData = "";
            if (error.kind === "UnexpectedError") {
                errorData = `Error: ${error.kind} - ${error.message}`;
            } else if (error.kind === "ApiError") {
                errorData = `Error: ${error.kind} - ${error.status}` +
                    `Error: ${error.error} - ${error.message}`;
            } else if (error.kind === "NotFound") {
                errorData = `Error: ${error.kind} - Vessel not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
        findVessel(id: UllUUID) {
            for (let i = 0; i < this.vessels.length; i++) {
                if (this.vessels[i].id.equals(id)) {
                    return i;
                }
            }
            return -1; 
        }
    },
})