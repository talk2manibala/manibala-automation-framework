package com.manibala.framework.jira;

import java.util.Base64;

public class JiraAuthHelper {
    public static String createAuthHeader(String email, String apiToken) {
        String credentials = email + ":" + apiToken;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}