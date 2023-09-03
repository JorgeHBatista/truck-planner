package com.trucks.domain.valueobject;

import java.util.Objects;

import com.trucks.utils.TruckUtils;

public class Plate {

    private static final int MAX_PLATE_NUMBER = 9999;
    private static final int MIN_PLATE_NUMBER = 0;
    private static final int PLATE_NUMBER_LENGTH = 4;
    private static final int PLATE_LETTERS_LENGTH = 3;
    private static final String PLATE_NULL_ERROR_MESSAGE = "Plate cannot be null or empty";
    private static final String PLATE_LENGTH_ERROR_MESSAGE = String
            .format("Plate must have %s characters", PLATE_NUMBER_LENGTH + PLATE_LETTERS_LENGTH);
    private static final String PLATE_NUMBER_ERROR_MESSAGE = String
            .format("Plate number must be between %s and %s", MIN_PLATE_NUMBER, MAX_PLATE_NUMBER);
    private static final String PLATE_LETTERS_ERROR_MESSAGE =
            String.format("Plate letters must be %s uppercase letters", PLATE_LETTERS_LENGTH);
    private final String plate;

    public Plate(String plate) {
        Plate.validate(plate);
        this.plate = plate;
    }

    public static void validate(String plate) {
        if (plate == null || plate.isEmpty()) {
            throw new IllegalArgumentException(PLATE_NULL_ERROR_MESSAGE);
        }
        if (plate.length() < PLATE_NUMBER_LENGTH + PLATE_LETTERS_LENGTH) {
            throw new IllegalArgumentException(PLATE_LENGTH_ERROR_MESSAGE);
        }
        int number = Integer.parseInt(plate.substring(0, 3));
        if (number < MIN_PLATE_NUMBER || number > MAX_PLATE_NUMBER) {
            throw new IllegalArgumentException(PLATE_NUMBER_ERROR_MESSAGE);
        }
        String letters = plate.substring(PLATE_NUMBER_LENGTH);
        if (!letters.matches(String.format("[A-Z]{%s}", PLATE_LETTERS_LENGTH))) {
            throw new IllegalArgumentException(PLATE_LETTERS_ERROR_MESSAGE);
        }
    }

    public String getPlate() {
        return plate;
    }

    public static Plate random() {
        String plate = String.format("%s%s",
                String.format("%04d", TruckUtils.generateRandomNumber(PLATE_NUMBER_LENGTH)),
                TruckUtils.generateRandomString(PLATE_LETTERS_LENGTH).toUpperCase());
        return new Plate(plate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Plate))
            return false;
        Plate other = (Plate) o;
        return Objects.equals(plate, other.getPlate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }
}
