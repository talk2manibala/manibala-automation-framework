package com.manibala.framework.util;

import java.util.Random;

public class RandomUtils {

    public static String generate5Digits() {
        Random random = new Random();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        return formatted;
    }

}
