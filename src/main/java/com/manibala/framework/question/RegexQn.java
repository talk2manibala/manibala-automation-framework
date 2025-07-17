package com.manibala.framework.question;

import com.manibala.framework.util.RegexUtil;

public class RegexQn {

    public static boolean verify(String originalText, String pattern, String expValue) {
        String matchedText = RegexUtil.getGroup1(originalText, pattern);
        return (matchedText.contains(expValue));
    }

}
