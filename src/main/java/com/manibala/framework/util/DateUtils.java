package com.manibala.framework.util;

import java.sql.Timestamp;

public class DateUtils {
    public static String getCurrentDateTimeNoSpaceWithRandomNumber() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime() + "_" + RandomUtils.generate5Digits());
    }
}
