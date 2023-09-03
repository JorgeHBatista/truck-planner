import { AccessRepository } from '../../application/repository/accessRepository';
import { Access } from "../../domain/entity/access";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { http } from "../../utils/ull-http";

export class AccessRestRepository implements AccessRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/access";
  	}

  	public async save(access: Access): Promise<Either<DataError, Access>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Access>>((resolve) => {
			http.post(options.url, access.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedAccess = Access.fromJson(data);
							resolve(Either.right(returnedAccess));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Access[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		return new Promise<Either<DataError, Access[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let accesss: Access[] = [];
							const jsonAccesss = data.content;
							for (let i = 0; i < jsonAccesss.length; i++) {
								const access = Access.fromJson(jsonAccesss[i]);
								accesss.push(access);
							}
							resolve(Either.right(accesss));
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
	
	public async deleteAll(): Promise<Either<DataError, Access[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Access[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Access[]) => {
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
