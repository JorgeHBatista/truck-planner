package domain.valueobject;

import java.util.Objects;
import java.util.Random;

public class Quantity {

    private int value;

    private static int MAX_VALUE = 10000;

    private final String QUANTITY_NEGATIVE_ERROR_MESSAGE =
            "Quantity must not me a negative number";

    public Quantity(int value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0)
            throw new IllegalArgumentException(QUANTITY_NEGATIVE_ERROR_MESSAGE);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public static Quantity random() {
        Random rand = new Random();
        return new Quantity(rand.nextInt(MAX_VALUE));
    }

    public boolean equals(Quantity other) {
        return this.value == other.getValue();
    }

}