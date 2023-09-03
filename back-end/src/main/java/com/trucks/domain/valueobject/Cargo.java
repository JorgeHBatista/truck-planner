package com.trucks.domain.valueobject;

import java.util.Objects;
import java.util.Random;

public enum Cargo {

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
    MADERAS_Y_CORCHOS;

    private static final String CARGO_NULL_ERROR_MESSAGE = "The cargo cannot be null or empty";
    private static final String CARGO_INVALID_ERROR_MESSAGE = "The cargo specified does not exist";

    public String toString() {
        return this.name();
    }

    public static String validate(Integer ordinal) {
        String error = "";
        if (ordinal == null)
            error = CARGO_NULL_ERROR_MESSAGE;
        if (ordinal < 0 || ordinal > values().length)
            error = CARGO_INVALID_ERROR_MESSAGE;
        return error;
    }

    public static Cargo random() {
        return values()[new Random().nextInt(values().length)];
    }

    public boolean equals(Cargo other) {
        return Objects.equals(this, other);
    }
}
