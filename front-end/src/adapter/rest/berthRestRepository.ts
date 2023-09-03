import { BerthRepository } from '../../application/repository/berthRepository';
import { Berth } from "../../domain/entity/berth";
import { Either } from "../../utils/ull-either";
import { DataError } from "../../utils/ull-data-error";
import { http } from "../../utils/ull-http";

export class BerthRestRepository implements BerthRepository {
  	private baseURL: string;

  	constructor() {
		this.baseURL = "http://localhost:8080/berths";
  	}

  	public async save(berth: Berth): Promise<Either<DataError, Berth>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Berth>>((resolve) => {
			http.post(options.url, berth.toJson())
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							const returnedBerth = Berth.fromJson(data);
							resolve(Either.right(returnedBerth));
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

  	public async findAll(pageRequest: any): Promise<Either<DataError, Berth[]>> {
		const options = {
			url: `${this.baseURL}?page=${pageRequest.page}&size=${pageRequest.size}`
		};
		console.log(options.url);
		return new Promise<Either<DataError, Berth[]>>((resolve) => {
			http.get(options.url)
			  	.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: any) => {
							let berths: Berth[] = [];
							const jsonBerths = data.content;
							for (let i = 0; i < jsonBerths.length; i++) {
								const berth = Berth.fromJson(jsonBerths[i]);
								berths.push(berth);
							}
							resolve(Either.right(berths));
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
	
	public async deleteAll(): Promise<Either<DataError, Berth[]>> {
		const options = {
			url: `${this.baseURL}`
		};
		return new Promise<Either<DataError, Berth[]>>((resolve) => {
			http.delete(options.url)
				.then((response) => {
					if (response.status === 200) {
					  	response.json().then((data: Berth[]) => {
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
