package es.ull.domain.valueobject;

import java.util.Objects;

import es.ull.utils.TruckUtils;

public class PortName {

    private final static int MAX_LENGTH = 40;
    private static final String PORT_NAME_NULL_ERROR_MESSAGE = "Port name cannot be null or empty";
    private static final String ERROR_MAX_LENGTH =
            "Port name must be at most " + MAX_LENGTH + " characters in length";
    private final String portName;

    public PortName(String portName) {
        PortName.validate(portName);
        this.portName = portName;
    }

    private static void validate(String portName) {
        if (portName == null || portName.isEmpty()) {
            throw new IllegalArgumentException(PORT_NAME_NULL_ERROR_MESSAGE);
        }
        if (portName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MAX_LENGTH);
        }
    }

    public String getPortName() {
        return portName;
    }

    public static PortName random() {
        String portName =
            TruckUtils.generateRandomString((TruckUtils.generateRandomNumber(2) % MAX_LENGTH) + 1);
        return new PortName(portName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortName)) {
            return false;
        }
        PortName other = (PortName) o;
        return Objects.equals(portName, other.getPortName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(portName);
    }
}
