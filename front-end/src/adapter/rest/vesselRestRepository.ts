import { VesselRepository } from '../../application/repository/vesselRepository';
import { Vessel } from "../../domain/entity/vessel";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { UllUUID } from "../../utils/ull-uuid";
import { http } from "../../utils/ull-http";

export class VesselRestRepository implements VesselRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/vessels";
  	}

  	public async save(vessel: Vessel): Promise<Either<DataError, Vessel>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Vessel>>((resolve) => {
			http.post(options.url, vessel.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedVessel = Vessel.fromJson(data);
							resolve(Either.right(returnedVessel));
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

  	public async find(id: UllUUID): Promise<Either<DataError, Vessel>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Vessel>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedVessel = Vessel.fromJson(data);
							resolve(Either.right(returnedVessel));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Vessel[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		return new Promise<Either<DataError, Vessel[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let vessels: Vessel[] = [];
							const jsonVessels = data.content;
							for (let i = 0; i < jsonVessels.length; i++) {
								const vessel = Vessel.fromJson(jsonVessels[i]);
								vessels.push(vessel);
							}
							resolve(Either.right(vessels));
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
	

	public async update(id: UllUUID, vessel: Vessel): Promise<Either<DataError, Vessel>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Vessel>>((resolve) => {
			http.put(options.url, vessel.toJson())
			.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedVessel = Vessel.fromJson(data);
							resolve(Either.right(returnedVessel));
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
	

	public async delete(id: UllUUID): Promise<Either<DataError, Vessel>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Vessel>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedVessel = Vessel.fromJson(data);
							resolve(Either.right(returnedVessel));
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
	
	public async deleteAll(): Promise<Either<DataError, Vessel[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Vessel[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Vessel[]) => {
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
