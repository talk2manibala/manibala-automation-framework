package com.manibala.framework.util;

import com.google.common.base.Joiner;
import net.serenitybdd.core.Serenity;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class SerenityUtil {

    public static void attachContents(String subject, Map<String, String> contents) {
        String result = Joiner.on(",").withKeyValueSeparator("=").join(contents);
        String[] split = result.split(",");
        String content = String.join("\n", split);
        attachContent(subject, content);
    }

    public static void attachContents(String subject, List<String> contents) {
        String content = String.join("\n", contents);
        attachContent(subject, content);
    }

    public static void attachContent(String subject, String content) {
        Serenity.recordReportData().withTitle(subject).andContents(content);
    }

    public static void attachFileToReport(String subject, String filepath) {
        try {
            Serenity.recordReportData().withTitle(subject)
                    .fromFile(Path.of(filepath));
        } catch (Exception e) {

        }
    }

}
