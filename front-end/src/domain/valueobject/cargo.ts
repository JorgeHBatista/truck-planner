import { Utils } from "../../utils/utils";

enum Cargo {
    METALES,
    AZUFRES,
    CHATARRAS_DE_HIERRO,
    OTROS_PRODUCTOS_ALIMENTICIOS,
    TARA_DE_CONTENEDORES,
    PRODUCTOS_SIDERÚRGICOS,
    RESTO_DE_MERCANCÍAS,
    MADERAS,
    CEREALES,
    VINOS_BEBIDAS_ALCOHOLES_Y_DERIVADOS,
    AUTOMÓVILES_Y_SUS_PIEZAS,
    PRODUCTOS_ALIMENTICIOS,
    HERRAMIENTAS_REPUESTOS,
    MINERALES_METÁLICOS,
    CEMENTO_Y_CLINKER,
    MATERIALES_DE_CONSTRUCCIÓN_ELABORADOS,
    FRUTAS_HORTALIZAS_Y_LEGUMBRES,
    TABACO_CACAO_CAFÉ_Y_ESPECIAS,
    PAPEL_Y_PASTA,
    PRODUCTOS_QUÍMICOS,
    MADERAS_Y_CORCHOS
}

class CargoVO {

	private static readonly CARGO_NULL_ERROR_MESSAGE: string = "The cargo cannot be null or empty";
	private static readonly CARGO_INVALID_ERROR_MESSAGE: string = "The cargo specified does not exist";

	public static validateCargo(ordinal: number): string {
        let error: string = "";
        if (ordinal === null) {
            error = this.CARGO_NULL_ERROR_MESSAGE;
        }
        if (ordinal < 0 || ordinal >= +Object.keys(Cargo)) {
            error = this.CARGO_INVALID_ERROR_MESSAGE;
        }
        return error;
    }

	public static random(): Cargo {
		return Utils.randomInt(0, +Object.keys(Cargo) - 1);
	}

    static fromOrdinal(ordinal: number): String {
        return Cargo[ordinal];
    }
}

export { Cargo, CargoVO };
