package com.trucks.domain.valueobject;

import java.util.Objects;

import com.trucks.utils.TruckUtils;

public class Driver {

    public static final int MAX_LENGTH = 50;
    public static final String REGULAR_EXPRESSION = ".+\s.+";
    private static final String DRIVER_NULL_ERROR_MESSAGE = "Driver cannot be null or empty";
    private static final String DRIVER_SPACE_ERROR_MESSAGE =
            "Driver must have name and surname stored";
    public static final String ERROR_MAX_LENGTH =
            "Driver must be at most " + MAX_LENGTH + " characters in length";
    private final String driver;

    public Driver(String driver) {
        Driver.validate(driver);
        this.driver = driver;
    }

    public static void validate(String driver) {
        if (driver == null || driver.isEmpty()) {
            throw new IllegalArgumentException(DRIVER_NULL_ERROR_MESSAGE);
        }
        final int length = driver.length();
        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MAX_LENGTH);
        }
        if (!driver.matches(REGULAR_EXPRESSION)) {
            throw new IllegalArgumentException(DRIVER_SPACE_ERROR_MESSAGE);
        }
    }

    public String getDriver() {
        return driver;
    }

    public static Driver random() {
        String driver = TruckUtils.generateRandomString(TruckUtils.generateRandomNumber(1) + 1);
        driver += " " + TruckUtils.generateRandomString(TruckUtils.generateRandomNumber(1) + 1);
        return new Driver(driver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        Driver other = (Driver) o;
        return Objects.equals(this.getDriver(), other.getDriver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver);
    }
}
