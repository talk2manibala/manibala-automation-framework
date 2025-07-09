package com.manibala.framework.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<String> toList(String value1, String value2) {
        List<String> list = new ArrayList<>();
        list.add(value1);
        list.add(value2);
        return list;
    }
}
