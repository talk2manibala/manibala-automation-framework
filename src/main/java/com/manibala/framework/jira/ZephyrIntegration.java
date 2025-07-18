package com.manibala.framework.jira;

import com.manibala.application.groq.api.config.ConfigProperties;
import okhttp3.*;

import java.io.IOException;

public class ZephyrIntegration {

    private static final String JIRA_URL = ConfigProperties.getJiraUrl();
    private static final String AUTH_HEADER = JiraAuthHelper.createAuthHeader(ConfigProperties.getJiraMail(), ConfigProperties.getJiraAuth());

    private static final String PROJECT_KEY = ConfigProperties.getJiraKey(); // your JIRA project key


    public static void postTestResult(String testcaseKey, boolean isPassed) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = "{"
                + "\"status\": {\"name\": \"" + (isPassed ? "Pass" : "Fail") + "\"},"
                + "\"testCaseKey\": \"" + testcaseKey + "\","
                + "\"projectKey\": \"" + PROJECT_KEY + "\","
                + "\"executionDate\": \""+java.time.LocalDate.now()+"\", "
                + "\"executionTime\": 5, "
                + "\"comment\": \"Automated test execution result.\" "
                + "}";

        Request request = new Request.Builder()
                .url(JIRA_URL + "/rest/atm/1.0/testresult") // for Zephyr Scale, use /rest/atm/1.0
                .addHeader("Authorization", AUTH_HEADER)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            System.out.println("Response body: " + response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}