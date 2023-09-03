import { OperationRepository } from '../../application/repository/operationRepository';
import { Operation } from "../../domain/entity/operation";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { http } from "../../utils/ull-http";
import { UllUUID } from '../../utils/ull-uuid';

export class OperationRestRepository implements OperationRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/operations";
  	}

  	public async save(operation: Operation): Promise<Either<DataError, Operation>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Operation>>((resolve) => {
			http.post(options.url, operation.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedOperation = Operation.fromJson(data);
							resolve(Either.right(returnedOperation));
					  	});
					} else {
					  response.json().then((errorData: any) => {
						const error: DataError = {
							message: errorData.message,
							kind: errorData.kind,
						};
						resolve(Either.left(error));
					  });
					}
				})
				.catch((e) => {
					const error: DataError = {
						kind: 'UnexpectedError',
						message: e.toString(),
					};
					resolve(Either.left(error));
				});
		});
	}

	public async find(id: UllUUID): Promise<Either<DataError, Operation>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Operation>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
						response.json().then((data: any) => {
							const operation = Operation.fromJson(data);
							resolve(Either.right(operation));
						});
					} else {
						response.json().then((errorData: any) => {
							const error: DataError = {
								message: errorData.message,
								kind: errorData.kind,
							};
							resolve(Either.left(error));
						});
					}
			  	})
			.catch((e) => {
				const error: DataError = {
					kind: 'UnexpectedError',
					message: e.toString(),
				};
				resolve(Either.left(error));
			});
		});
	}

  	public async findAll(pageRequest: any): Promise<Either<DataError, Operation[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		return new Promise<Either<DataError, Operation[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let operations: Operation[] = [];
							const jsonOperations = data.content;
							for (let i = 0; i < jsonOperations.length; i++) {
								const operation = Operation.fromJson(jsonOperations[i]);
								operations.push(operation);
							}
							resolve(Either.right(operations));
					  	});
					} else {
					  response.json().then((errorData: any) => {
						const error: DataError = {
							message: errorData.message,
							kind: errorData.kind,
						};
						resolve(Either.left(error));
					  });
					}
			  	})
			  	.catch((e) => {
					const error: DataError = {
						kind: 'UnexpectedError',
						message: e.toString(),
					};
					resolve(Either.left(error));
			  	});
		  });
	}
	
	public async deleteAll(): Promise<Either<DataError, Operation[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Operation[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Operation[]) => {
							resolve(Either.right(data));
					  	});
					} else {
					  response.json().then((errorData: any) => {
						const error: DataError = {
							message: errorData.message,
							kind: errorData.kind,
						};
						resolve(Either.left(error));
					  });
					}
			  	})
			  	.catch((e) => {
					const error: DataError = {
						kind: 'UnexpectedError',
						message: e.toString(),
					};
					resolve(Either.left(error));
			  	});
		});
	}
}
