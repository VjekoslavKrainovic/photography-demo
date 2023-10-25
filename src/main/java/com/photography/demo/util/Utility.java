package com.photography.demo.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utility {

  private static final String LOCAL_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

  private Utility() {
  }

  public static LocalDateTime currentLocalDateTime() {
    return LocalDateTime.now(ZoneId.of("Europe/Paris"));
  }

  public static String formatLocalDateTimeToString(LocalDateTime localDateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN)
        .withLocale(Locale.FRENCH);
    return localDateTime.format(formatter);
  }

}
