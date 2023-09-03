import { defineStore } from 'pinia';
import { Scale } from '../../../domain/entity/scale';
import { ScaleService } from '../../../application/service/scaleService';
import { ScaleRestRepository } from '../../rest/scaleRestRepository';
import { DataError } from '../../../utils/ull-data-error';
import { UllUUID } from '../../../utils/ull-uuid';

export const useScaleStateManager = defineStore('scale', {
    state: () => ({ 
        scales: [] as Scale[], 
        service: new ScaleService(new ScaleRestRepository())
    }),
    getters: {
        getScale: (state) => {
            return state.scales;
        },
    },
    actions:{
        async save(newScale: Scale) {
            try {
                const response = await this.service.save(newScale);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getScale.push(data as Scale);
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
                    this.scales = [data as Scale];
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
                        this.scales = data as Scale[];
                    }
                )
            } catch (error) {
                throw error
            }
        },
        async deleteAll() {
            const response = await this.service.deleteAll();
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    this.scales = [];
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
                errorData = `Error: ${error.kind} - Scale not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
    },
})