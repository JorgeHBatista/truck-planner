import { ScaleRepository } from '../../application/repository/scaleRepository';
import { Scale } from "../../domain/entity/scale";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { http } from "../../utils/ull-http";
import { UllUUID } from '../../utils/ull-uuid';

export class ScaleRestRepository implements ScaleRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/scales";
  	}

  	public async save(scale: Scale): Promise<Either<DataError, Scale>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Scale>>((resolve) => {
			http.post(options.url, scale.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedScale = Scale.fromJson(data);
							resolve(Either.right(returnedScale));
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

	public async find(id: UllUUID): Promise<Either<DataError, Scale>> {
		const options = {
			url: `${this.baseURL}/${id}`
		};
		return new Promise<Either<DataError, Scale>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
						response.json().then((data: any) => {
							const scale = Scale.fromJson(data);
							resolve(Either.right(scale));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Scale[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		return new Promise<Either<DataError, Scale[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let scales: Scale[] = [];
							const jsonScales = data.content;
							for (let i = 0; i < jsonScales.length; i++) {
								const scale = Scale.fromJson(jsonScales[i]);
								scales.push(scale);
							}
							resolve(Either.right(scales));
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
	
	public async deleteAll(): Promise<Either<DataError, Scale[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Scale[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Scale[]) => {
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
