package domain.valueobject;

import java.util.Objects;
import java.util.Random;

public enum EntryPoint {

    GATE1, 
    GATE2, 
    GATE3, 
    GATE4, 
    GATE5, 
    GATE6, 
    OCEAN;

    private static EntryPoint[] values = null;

    static {
        values = EntryPoint.values();
    }

    private static final String ENTRYPOINT_NULL_ERROR_MESSAGE =
            "The entry point cannot be null or empty";
    private static final String ENTRYPOINT_INVALID_ERROR_MESSAGE =
            "The entry point specified does not exist";

    public String toString() {
        return this.name();
    }

    public static String validate(Integer ordinal) {
        String error = "";
        if (ordinal == null)
            error = ENTRYPOINT_NULL_ERROR_MESSAGE;
        if (ordinal < 0 || ordinal > 6)
            error = ENTRYPOINT_INVALID_ERROR_MESSAGE;
        return error;
    }

    public static EntryPoint random() {
        Random rand = new Random();
        Integer randomNum = rand.nextInt(7);
        return values[randomNum];
    }

    public boolean equals(EntryPoint other) {
        return Objects.equals(this, other);
    }
}