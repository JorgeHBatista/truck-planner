import { defineStore } from 'pinia';
import { Port } from '../../../domain/entity/port';
import { PortService } from '../../../application/service/portService';
import { PortRestRepository } from '../../rest/portRestRepository';
import { DataError } from '../../../utils/ull-data-error';
import { UllUUID } from '../../../utils/ull-uuid';

export const usePortStateManager = defineStore('ports', {
    state: () => ({ 
        ports: [] as Port[], 
        service: new PortService(new PortRestRepository())
    }),
    getters: {
        getPorts: (state) => {
            return state.ports;
        },
    },
    actions:{
        async save(newPort: Port) {
            try {
                const response = await this.service.save(newPort);
                response.fold(
                    (error) => {
                        throw new Error(this.handleError(error));
                    },
                    (data) => {
                        this.getPorts.push(data as Port);
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
                    this.ports = [data as Port];
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
                        this.ports = data as Port[];
                    }
                )
            } catch (error) {
                throw error
            }
        },
        async update(id: UllUUID, updatedPort: Port) {
            const response = await this.service.update(id, updatedPort);
            response.fold(
                (error) => {
                    throw new Error(this.handleError(error));
                },
                (data) => {
                    const index = this.findPort(id);
                    if (index !== -1) {
                        this.ports.splice(index, 1, data as Port);
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
                    const index = this.findPort(id);
                    if (index !== -1) {
                        this.ports.splice(index, 1);
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
                    this.ports = [];
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
                errorData = `Error: ${error.kind} - Port not found`;
            } else if (error.kind === "Unauthorized") {
                errorData = `Error: ${error.kind} - User unauthorized`;
            }
            return errorData;
        },
        findPort(id: UllUUID) {
            for (let i = 0; i < this.ports.length; i++) {
                if (this.ports[i].id.equals(id)) {
                    return i;
                }
            }
            return -1; 
        }
    },
})