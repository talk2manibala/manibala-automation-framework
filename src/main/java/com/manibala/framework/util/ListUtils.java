package com.manibala.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {
    public static List<String> toList(String value1, String value2) {
        List<String> list = new ArrayList<>();
        list.add(value1);
        list.add(value2);
        return list;
    }

    public static String listToCommaSeparatedString(List<String> list) {
        String stringValue = String.join(",", list
                .stream()
                .map(name -> ("'" + name + "'"))
                .collect(Collectors.toList()));
        return stringValue;
    }
}
