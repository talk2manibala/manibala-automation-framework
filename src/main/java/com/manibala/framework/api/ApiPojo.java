package com.manibala.framework.api;

import io.restassured.response.Response;
import lombok.Data;
import net.serenitybdd.screenplay.Actor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ApiPojo implements Serializable {
    private Actor actor;
    private String apiName;;
    private String apiEndpoint;;
    private String apiRequestJson;;
    private Map<String, String> apiHeaders;
    private boolean isApiFieldValidationRequired;;
    private boolean isStatusCodeValidationRequired;;
    private String isReportingRequired;
    private int apiExpStatusCode;
    private int actualStatusCode;
    private String apiExpErrorMessage;
    private String apiExpErrorCode;
    private String authKey;;
    private String authValue;;
    private String bearerToken;;
    private Map<String, String> pathParam;
    private Map<String, String> queryParam;
    private Map<String, List<String>> expectedDataSet;
    private Response response;
}
