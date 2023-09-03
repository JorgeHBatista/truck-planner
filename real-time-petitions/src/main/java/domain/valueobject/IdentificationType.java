package domain.valueobject;

import java.util.Objects;
import java.util.Random;

public enum IdentificationType {

    QR(0),
    CARD(1),
    UNKNOWN(2);

    private static IdentificationType[] values = null;

    static {
        values = IdentificationType.values();
    }

    private static final String IDENTIFICATION_NULL_ERROR_MESSAGE =
            "The identification type cannot be null or empty";
    private static final String IDENTIFICATION_INVALID_ERROR_MESSAGE =
            "The identification type specified does not exist";

    private IdentificationType(Integer ordinal) {
        String error = IdentificationType.validate(ordinal);
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
    }

    public static String validate(Integer ordinal) {
        String error = "";
        if (ordinal == null)
            error = IDENTIFICATION_NULL_ERROR_MESSAGE;
        if (ordinal < 0 || ordinal > 2)
            error = IDENTIFICATION_INVALID_ERROR_MESSAGE;
        return error;
    }

    public String toString() {
        return this.name();
    }

    public static IdentificationType random() {
        Random rand = new Random();
        Integer randomNum = rand.nextInt(3);
        return values[randomNum];
    }

    public boolean equals(IdentificationType other) {
        return Objects.equals(this, other);
    }
}