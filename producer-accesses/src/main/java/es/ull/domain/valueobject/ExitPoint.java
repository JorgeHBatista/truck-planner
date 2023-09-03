package es.ull.domain.valueobject;

import java.util.Objects;
import java.util.Random;

public enum ExitPoint {

    GATE1, 
    GATE2, 
    GATE3, 
    GATE4, 
    GATE5, 
    GATE6, 
    OCEAN;

    private static ExitPoint[] values = null;

    static {
        values = ExitPoint.values();
    }

    private static final String EXITPOINT_NULL_ERROR_MESSAGE =
            "The exit point cannot be null or empty";
    private static final String EXITPOINT_INVALID_ERROR_MESSAGE =
            "The exit point specified does not exist";

    public static String validate(Integer ordinal) {
        String error = "";
        if (ordinal == null)
            error = EXITPOINT_NULL_ERROR_MESSAGE;
        if (ordinal < 0 || ordinal > 6)
            error = EXITPOINT_INVALID_ERROR_MESSAGE;
        return error;
    }

    public String toString() {
        return this.name();
    }

    public static ExitPoint random() {
        Random rand = new Random();
        Integer randomNum = rand.nextInt(7);
        return values[randomNum];
    }

    public boolean equals(ExitPoint other) {
        return Objects.equals(this, other);
    }
}
