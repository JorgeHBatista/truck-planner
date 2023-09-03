package com.trucks.domain.valueobject;

import java.util.Objects;

import com.trucks.utils.TruckUtils;

public class Brand {

    private static final int MAX_LENGTH = 20;
    private static final String BRAND_NULL_ERROR_MESSAGE = "Brand cannot be null or empty";
    private static final String ERROR_MAX_LENGTH =
            "Brand must be at most " + MAX_LENGTH + " characters in length";
    private final String brand;

    public Brand(String brand) {
        Brand.validate(brand);
        this.brand = brand;
    }

    public static void validate(String brand) {
        if (brand == null || brand.isEmpty()) {
            throw new IllegalArgumentException(BRAND_NULL_ERROR_MESSAGE);
        }
        if (brand.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MAX_LENGTH);
        }
    }

    public String getBrand() {
        return brand;
    }

    public static Brand random() {
        String brand = TruckUtils.generateRandomString(TruckUtils.generateRandomNumber(1) + 1);
        return new Brand(brand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brand)) {
            return false;
        }
        Brand other = (Brand) o;
        return Objects.equals(brand, other.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }
}
