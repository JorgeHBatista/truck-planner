import { defineStore } from 'pinia';
import { Berth } from '../../../domain/entity/berth';
import { BerthService } from '../../../application/service/berthService';
import { BerthRestRepository } from '../../rest/berthRestRepository';
import { DataError } from '../../../utils/ull-data-error';

export const useBerthStateManager = defineStore('berth', {
    state: () => ({ 
        berth: [] as Berth[], 
        service: new BerthService(new BerthRestRepository())
    }),
    getters: {
        getBerth: (state) => {
            return state.berth;
        },
    },
    actions:{
        async save(newBerth: Berth) {
            console.log(newBerth);
            try {
                const response = await this.service.save(newBerth);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getBerth.push(data as Berth);
                    }
                )
            } catch (error) {
                throw error;
            }
        },
        async findAll(pageRequest: any) {
            try {
                const response = await this.service.findAll(pageRequest);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.berth = data as Berth[];
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
                    this.berth = [];
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
                errorData = `Error: ${error.kind} - Berth not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
    },
})