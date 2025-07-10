package com.manibala.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static Matcher getMatcher(String inputTxt, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(inputTxt);
        return matcher;
    }
    public static String get(String inputTxt, String pattern) {
        String output = "";
        Matcher m = getMatcher(inputTxt, pattern);
        output = m.find() ? m.group(0) : output;
        return output;
    }

    public static String getGroup1(String inputTxt, String pattern) {
        String output = "";
        Matcher m = getMatcher(inputTxt, pattern);
        output = m.find() ? m.group(1) : output;
        return output;
    }

    public static List<String> getAllMatches(String inputTxt, String pattern){
        Matcher m = getMatcher(inputTxt, pattern);
        List<String> list = new ArrayList<>();
        m.results().map(MatchResult::group).forEach(list::add);
        return list;
    }
}
