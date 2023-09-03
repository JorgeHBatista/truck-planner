import { defineStore } from 'pinia';
import { Operation } from '../../../domain/entity/operation';
import { OperationService } from '../../../application/service/operationService';
import { OperationRestRepository } from '../../rest/operationRestRepository';
import { DataError } from '../../../utils/ull-data-error';
import { UllUUID } from '../../../utils/ull-uuid';

export const useOperationStateManager = defineStore('operation', {
    state: () => ({ 
        operations: [] as Operation[], 
        service: new OperationService(new OperationRestRepository())
    }),
    getters: {
        getOperation: (state) => {
            return state.operations;
        },
    },
    actions:{
        async save(newOperation: Operation) {
            try {
                const response = await this.service.save(newOperation);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getOperation.push(data as Operation);
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
                    this.operations = [data as Operation];
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
                        this.operations = data as Operation[];
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
                    this.operations = [];
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
                errorData = `Error: ${error.kind} - Operation not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
    },
})