package com.trucks.utils;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TruckUtils {

  static public String generateRandomString(int length) {
    if (length <= 0) throw new IllegalArgumentException();
    String string = "";
    int n = 'Z' - 'A' + 1;
    for (int i = 0; i < length; i++) {
      char c = (char) ('A' + Math.random() * n);
      string += c;
    }
    return string;
  }

  static public int generateRandomNumber(int length) {
    if (length <= 0) throw new IllegalArgumentException();
    int number = 0;
    Random rand = new Random();
    for (int i = 0; i < length; i++) {
      number *= 10;
      int randomNum = rand.nextInt((9 - 0) + 1);
      number += randomNum;
    }
    return number;
  }

  static public String localDateTimeToString(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return localDateTime.format(formatter);
  }
}