package com.trucks.domain.valueobject;

import java.util.Objects;
import java.util.Random;

public enum OperationType {

    LOAD(0),
    UNLOAD(1);

    private static OperationType[] values = null;

    static {
        values = OperationType.values();
    }

    private static final String OPERATIONTYPE_NULL_ERROR_MESSAGE =
            "The operation type cannot be null or empty";
    private static final String OPERATIONTYPE_INVALID_ERROR_MESSAGE =
            "The operation type specified does not exist";

    private OperationType(Integer ordinal) {
        String error = OperationType.validate(ordinal);
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
    }

    public String toString() {
        return this.name();
    }

    public static String validate(Integer ordinal) {
        String error = "";
        if (ordinal == null)
            error = OPERATIONTYPE_NULL_ERROR_MESSAGE;
        if (ordinal < 0 || ordinal > 2)
            error = OPERATIONTYPE_INVALID_ERROR_MESSAGE;
        return error;
    }

    public static OperationType random() {
        Random rand = new Random();
        Integer randomNum = rand.nextInt(1);
        return values[randomNum];
    }

    public boolean equals(OperationType other) {
        return Objects.equals(this, other);
    }
}
