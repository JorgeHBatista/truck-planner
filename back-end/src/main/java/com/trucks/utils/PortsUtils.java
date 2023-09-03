package com.trucks.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import es.ull.utils.string.UllString;
import com.trucks.adapter.rest.json.JsonFields;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PortsUtils {

    public static boolean isIsoDate(String date) {
        try {
            Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidDescription(String string) {
        return Pattern
                .compile("^[a-zA-Z-\\d\\s\\.]*$")
                .matcher(string)
                .matches();
    }

    public static boolean isValidName(String string) {
        return Pattern
                .compile("^[a-zA-Z-\\d\\s\\.]*$")
                .matcher(string)
                .matches();
    }
}
