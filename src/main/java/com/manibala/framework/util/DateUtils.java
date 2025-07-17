package com.manibala.framework.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateUtils {

    private static final List<String> knownFormats = Arrays.asList(
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "yyyy-MM-dd",
            "dd-MM-yyyy",
            "yyyy/MM/dd",
            "d MMM yyyy",
            "dd MMMM yyyy"
    );

    public static String getCurrentDateTimeNoSpaceWithRandomNumber() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime() + "_" + RandomUtils.generate5Digits());
    }

    public static String getDateInThisFormat(String date, String outputFormat) {
        for (String format : knownFormats) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
                LocalDate formattedDate = LocalDate.parse(date, inputFormatter);
                return formattedDate.format(outputFormatter);
            } catch (DateTimeParseException e) {
                LogUtils.fail(ActorUtils.getActor(), "Unable to parse date: Unknown format");
            }
        }
        return date;
    }

}
