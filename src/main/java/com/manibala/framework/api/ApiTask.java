package com.manibala.framework.api;

import java.util.List;

public interface ApiTask {
    ApiTask validateStatusCode(ApiPojo apiPojo);
    ApiTask validateResponse(ApiPojo apiPojo);
    boolean checkResponse(ApiPojo apiPojo);
    boolean checkFieldValue(ApiPojo apiPojo, String fieldName, List<String> expectedValues);
    boolean checkFieldsValue(ApiPojo apiPojo, String fieldName, List<String> expectedValues);
    boolean compareValues(String actualValue, List<String> expectedValues, String subject);
    ApiPojo post(ApiPojo apiPojo);
    ApiPojo get(ApiPojo apiPojo);
    ApiPojo delete(ApiPojo apiPojo);
    ApiPojo put(ApiPojo apiPojo);
}
