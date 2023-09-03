import { PortRepository } from '../../application/repository/portRepository';
import { Port } from "../../domain/entity/port";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";
import { http } from "../../utils/ull-http";

export class PortRestRepository implements PortRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/ports";
  	}

  	public async save(port: Port): Promise<Either<DataError, Port>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Port>>((resolve) => {
			http.post(options.url, port.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedPort = Port.fromJson(data);
							resolve(Either.right(returnedPort));
					  	});
					} else {
					  response.json().then((errorData: any) => {
						console.log('Resolved error:', errorData); // Add this line for debugging
						const error: DataError = {
							message: errorData.message,
							kind: errorData.kind,
						};
						resolve(Either.left(error));
					  });
					}
				})
				.catch((e) => {
					console.log('Caught error:', e); // Add this line for debugging
					const error: DataError = {
						kind: 'UnexpectedError',
						message: e.toString(),
					};
					resolve(Either.left(error));
				});
		});
	}

  	public async find(id: UllUUID): Promise<Either<DataError, Port>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Port>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedPort = Port.fromJson(data);
							resolve(Either.right(returnedPort));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Port[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		return new Promise<Either<DataError, Port[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let ports: Port[] = [];
							const jsonPorts = data.content;
							for (let i = 0; i < jsonPorts.length; i++) {
								const port = Port.fromJson(jsonPorts[i]);
								ports.push(port);
							}
							resolve(Either.right(ports));
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
	

	public async update(id: UllUUID, port: Port): Promise<Either<DataError, Port>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Port>>((resolve) => {
			http.put(options.url, port.toJson())
			.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedPort = Port.fromJson(data);
							resolve(Either.right(returnedPort));
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
	

	public async delete(id: UllUUID): Promise<Either<DataError, Port>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Port>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedPort = Port.fromJson(data);
							resolve(Either.right(returnedPort));
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
	
	public async deleteAll(): Promise<Either<DataError, Port[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Port[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Port[]) => {
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
