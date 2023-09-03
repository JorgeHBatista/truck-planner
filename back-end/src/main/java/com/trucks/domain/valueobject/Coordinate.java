package com.trucks.domain.valueobject;

import java.util.Objects;

public class Coordinate {

    private final double latitude;
    private final double longitude;
    private static final double MAX_LATITUDE = 90.0; // Between 0 and 90 means Nort (N)
    private static final double MIN_LATITUDE = -90.0; // Between 0 and -90 means South (S)
    private static final double MAX_LONGITUDE = 180.0; // Between 0 and 180 means East (E)
    private static final double MIN_LONGITUDE = -180.0; // Between 0 and -180 means West (W)
    private static final String LATITUDE_ERROR_MESSAGE =
            "Latitude must be between -90 and 90 degrees.";
    private static final String LONGITUDE_ERROR_MESSAGE =
            "Longitude must be between -180 and 180 degrees.";

    public Coordinate(double latitude, double longitude) {
        Coordinate.validate(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static void validate(double latitude, double longitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException(LATITUDE_ERROR_MESSAGE);
        }
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException(LONGITUDE_ERROR_MESSAGE);
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toString() {
        return String.valueOf(latitude) + ", " + String.valueOf(longitude);
    }

    public static Coordinate random() {
        double latitude = Math.random() * (MAX_LATITUDE - MIN_LATITUDE) + MIN_LATITUDE;
        double longitude = Math.random() * (MAX_LONGITUDE - MIN_LONGITUDE) + MIN_LONGITUDE;
        return new Coordinate(latitude, longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate other = (Coordinate) o;
        return Double.compare(other.getLatitude(), this.getLatitude()) == 0
                && Double.compare(other.getLongitude(), this.getLongitude()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
