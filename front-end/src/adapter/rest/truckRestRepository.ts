import { TruckRepository } from '../../application/repository/truckRepository';
import { Truck } from "../../domain/entity/truck";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";
import { http } from "../../utils/ull-http";

export class TruckRestRepository implements TruckRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/trucks";
  	}

  	public async save(truck: Truck): Promise<Either<DataError, Truck>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Truck>>((resolve) => {
			http.post(options.url, truck.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedTruck = Truck.fromJson(data);
							resolve(Either.right(returnedTruck));
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

  	public async find(id: UllUUID): Promise<Either<DataError, Truck>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Truck>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedTruck = Truck.fromJson(data);
							console.log(returnedTruck);
							resolve(Either.right(returnedTruck));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Truck[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		console.log(options.url);
		return new Promise<Either<DataError, Truck[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let trucks: Truck[] = [];
							const jsonTrucks = data.content;
							for (let i = 0; i < jsonTrucks.length; i++) {
								const truck = Truck.fromJson(jsonTrucks[i]);
								trucks.push(truck);
							}
							resolve(Either.right(trucks));
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
	

	public async update(id: UllUUID, truck: Truck): Promise<Either<DataError, Truck>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Truck>>((resolve) => {
			http.put(options.url, truck.toJson())
			.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedTruck = Truck.fromJson(data);
							resolve(Either.right(returnedTruck));
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
	

	public async delete(id: UllUUID): Promise<Either<DataError, Truck>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Truck>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedTruck = Truck.fromJson(data);
							resolve(Either.right(returnedTruck));
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
	
	public async deleteAll(): Promise<Either<DataError, Truck[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Truck[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Truck[]) => {
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
