import { defineStore } from 'pinia';
import { Truck } from '../../../domain/entity/truck';
import { TruckService } from '../../../application/service/truckService';
import { TruckRestRepository } from '../../rest/truckRestRepository';
import { DataError } from '../../../utils/ull-data-error';
import { UllUUID } from '../../../utils/ull-uuid';

export const useTruckStateManager = defineStore('trucks', {
    state: () => ({ 
        trucks: [] as Truck[], 
        service: new TruckService(new TruckRestRepository())
    }),
    getters: {
        getTrucks: (state) => {
            return state.trucks;
        },
    },
    actions:{
        async save(newTruck: Truck) {
            try {
                const response = await this.service.save(newTruck);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getTrucks.push(data as Truck);
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
                    this.trucks = [data as Truck];
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
                        this.trucks = data as Truck[];
                    }
                )
            } catch (error) {
                throw error
            }
        },
        async update(id: UllUUID, updatedTruck: Truck) {
            const response = await this.service.update(id, updatedTruck);
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    const index = this.findTruck(id);
                    if (index !== -1) {
                        this.trucks.splice(index, 1, data as Truck);
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
                    const index = this.findTruck(id);
                    if (index !== -1) {
                        this.trucks.splice(index, 1);
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
                    this.trucks = [];
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
                errorData = `Error: ${error.kind} - Truck not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
        findTruck(id: UllUUID) {
            for (let i = 0; i < this.trucks.length; i++) {
                if (this.trucks[i].id.equals(id)) {
                    return i;
                }
            }
            return -1; 
        }
    },
})