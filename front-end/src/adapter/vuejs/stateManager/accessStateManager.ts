import { defineStore } from 'pinia';
import { Access } from '../../../domain/entity/access';
import { AccessService } from '../../../application/service/accessService';
import { AccessRestRepository } from '../../rest/accessRestRepository';
import { DataError } from '../../../utils/ull-data-error';

export const useAccessStateManager = defineStore('access', {
    state: () => ({ 
        access: [] as Access[], 
        service: new AccessService(new AccessRestRepository())
    }),
    getters: {
        getAccess: (state) => {
            return state.access;
        },
    },
    actions:{
        async save(newAccess: Access) {
            try {
                const response = await this.service.save(newAccess);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getAccess.push(data as Access);
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
                        this.access = data as Access[];
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
                    this.access = [];
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
                errorData = `Error: ${error.kind} - Access not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
    },
})