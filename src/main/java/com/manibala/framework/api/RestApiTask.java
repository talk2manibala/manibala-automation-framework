package com.manibala.framework.api;

import com.manibala.framework.asserts.AssertQn;
import com.manibala.framework.constants.MatchersKey;
import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.ListUtils;
import com.manibala.framework.util.LogUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.apache.commons.math3.util.Precision;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import static net.serenitybdd.screenplay.GivenWhenThen.when;

public class RestApiTask implements ApiTask {

    @Override
    public ApiTask validateStatusCode(ApiPojo apiPojo) {
        AssertQn.verify(apiPojo.getActor(), "Verify status code "+apiPojo.getApiExpStatusCode()+" == "+apiPojo.getActualStatusCode(), apiPojo.getApiExpStatusCode()==apiPojo.getActualStatusCode());
        return new RestApiTask();
    }

    @Override
    public ApiTask validateResponse(ApiPojo apiPojo) {
        apiPojo.getActor().attemptsTo(
                Task.where("API Response Validation", new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T t) {
                        boolean isTrue = checkResponse(apiPojo);
                        AssertQn.verify(apiPojo.getActor(), "Is all response matched", isTrue);
                    }
                })
        );
        return new RestApiTask();
    }

    public boolean checkResponse(ApiPojo apiPojo) {
        final int[] count = {0};
        try {
            apiPojo.getActor().attemptsTo(
                    Task.where("API Response Validation", new Performable() {
                        @Override
                        public <T extends Actor> void performAs(T actor) {
                            for (Map.Entry<String, List<String>> entry : apiPojo.getExpectedDataSet().entrySet()) {
                                long dotCount = entry.getKey().chars().filter(ch -> ch == '.').count();
                                long sqBracketCount = entry.getKey().chars().filter(ch -> ch == '[').count();
                                List<String> expectedValues = apiPojo.getExpectedDataSet().get(entry.getKey());
                                if (dotCount==0) {
                                    count[0] = checkFieldValue(apiPojo, entry.getKey(), expectedValues) ? count[0] : (count[0] + 1);
                                }
                                if (dotCount>0) {
                                    if (sqBracketCount>0) {
                                        count[0] = checkFieldValue(apiPojo, entry.getKey(), expectedValues) ? count[0] : (count[0] + 1);
                                    } else {
                                        count[0] = checkFieldsValue(apiPojo, entry.getKey(), expectedValues) ? count[0] : (count[0] + 1);
                                    }
                                }
                            }
                        }
                    })
            );
        } catch (Exception e) {

        }
        return (count[0] == 0);
    }

    @Override
    public boolean checkFieldValue(ApiPojo apiPojo, String fieldName, List<String> expectedValues) {
        boolean isMatched=false;
        String actualValue = "";
        Response response = apiPojo.getResponse();
        if (!fieldName.contains(API.API_EXP_STATUS_CODE)) {
            Object actualValueInObject = response.jsonPath().using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).get(fieldName.replaceAll(":\\d+", ""));
            actualValue = actualValueInObject==null ? MatchersKey.FIELD_NOT_PRESENT : String.valueOf(actualValueInObject);
        }
        if (fieldName.equalsIgnoreCase(API.API_EXP_STATUS_CODE)) {
            actualValue = String.valueOf(response.statusCode());
            isMatched = Integer.valueOf(actualValue).equals(Integer.valueOf(expectedValues.get(0)));
        } else {
            if (!expectedValues.contains(MatchersKey.ANY_OF)) {
                isMatched = compareValues(actualValue.replaceAll("[\\[\\]]", ""), expectedValues, fieldName);
            } else {
                for (int i=0; i < (expectedValues.size()-1); i++) {
                    isMatched = compareValues(actualValue.replaceAll("[\\[\\]]", ""), ListUtils.toList(expectedValues.get(0), expectedValues.get(i+1)), fieldName);
                    if (expectedValues.get(0).contains(MatchersKey.ANY_OF) && isMatched) {
                        isMatched = true;
                        break;
                    }
                }
            }
        }
        return isMatched;
    }

    @Override
    public boolean checkFieldsValue(ApiPojo apiPojo, String fieldName, List<String> expectedValues) {
        int mismatchCount = 0;

        try {
            if (expectedValues.get(0).matches(MatchersKey.CONTAINS + ".*" + "|" + MatchersKey.PRECISION + ".*" + "|" + MatchersKey.ANY_OF + ".*" + "|" + MatchersKey.ANY_OF_CONTAINS + ".*")
                && expectedValues.size()==2
                && fieldName.contains("["))
                return checkFieldValue(apiPojo, fieldName, expectedValues);
            List actualValues = null;
            try {
                actualValues = apiPojo.getResponse().jsonPath().using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).getList(fieldName.replaceAll(":\\d+", ""));
            } catch (Exception e) {
                LogUtils.with(apiPojo.getActor(), "Ignore this exception : "+e.getMessage());
                return checkFieldValue(apiPojo, fieldName, expectedValues);
            }
            boolean isMatched=false;
            for (int i=0; i<actualValues.size(); i++) {
                if (expectedValues.size()==1 || actualValues.get(0).equals(MatchersKey.NOT_NULL)) {
                    isMatched = checkFieldValue(apiPojo, fieldName, expectedValues);
                } else {
                    isMatched = checkFieldValue(apiPojo, fieldName, ListUtils.toList(expectedValues.get(0), expectedValues.get(1)));
                    if (expectedValues.get(0).contains(MatchersKey.ANY_OF) && isMatched) {
                        mismatchCount = 0;
                        break;
                    }
                    if (expectedValues.get(0).contains(MatchersKey.CONTAINS) && (i+2)==expectedValues.size()) {
                        mismatchCount = isMatched ? mismatchCount : mismatchCount+1;
                        break;
                    }
                }
                mismatchCount = isMatched ? mismatchCount : mismatchCount+1;
            }
        } catch (Exception e) {
            LogUtils.with(apiPojo.getActor(), "Issue in validating the field "+fieldName+"; Exp Value - "+expectedValues, e.getMessage());
        }
        return mismatchCount==0;
    }

    @Override
    public boolean compareValues(String actualValue, List<String> expectedValues, String subject) {
        boolean isMatched=false;
        String compareFlag = MatchersKey.EQUALS;
        String expectedValue = expectedValues.get(0);
        if (expectedValues.size()==1 && isMatcherKeyExists(expectedValues)) {
            compareFlag = expectedValues.get(0);
        } else if (expectedValues.size()==2 && isMatcherKeyExists(expectedValues)) {
            compareFlag = expectedValues.get(0);
            expectedValue = expectedValues.get(1);
        }
        if ((actualValue.matches("\\d+\\.\\d+") || expectedValue.matches("\\d+\\.\\d+")) && actualValue.length()!=expectedValue.length()) {
            actualValue = actualValue.replaceAll("\\.0+$", "");
            expectedValue = expectedValue.replaceAll("\\.0+$", "");
        }
        if (compareFlag.startsWith(MatchersKey.CONTAINS) || compareFlag.startsWith(MatchersKey.ANY_OF)) {
            isMatched = actualValue.contains(expectedValue);
        } else if (compareFlag.startsWith(MatchersKey.STARTS_WITH)) {
            isMatched = actualValue.startsWith(expectedValue);
        } else if (compareFlag.startsWith(MatchersKey.ENDS_WITH)) {
            isMatched = actualValue.endsWith(expectedValue);
        } else if (compareFlag.startsWith(MatchersKey.NOT)) {
            isMatched = !actualValue.contains(expectedValue);
        } else if (compareFlag.startsWith(MatchersKey.PRECISION)) {
            Double precision = 0.0;
            if (actualValue.length() < expectedValue.length()) {
                actualValue = actualValue.contains(".") ? actualValue : String.valueOf(Double.valueOf(actualValue) * 1.0);
                precision = (1 / (Math.pow(10.0, Double.valueOf(actualValue.split("\\.")[1].length())))) * 5;
            } else {
                expectedValue = expectedValue.contains(".") ? expectedValue : String.valueOf(Double.valueOf(expectedValue) * 1.0);
                precision = (1 / (Math.pow(10.0, Double.valueOf(expectedValue.split("\\.")[1].length())))) * 5;
            }
            isMatched = Precision.equals(Double.valueOf(actualValue), Double.valueOf(expectedValue), precision);
        } else if (compareFlag.startsWith(MatchersKey.NULL)) {
            isMatched = actualValue == null;
            actualValue = actualValue == null ? MatchersKey.NULL : actualValue;
        } else if (compareFlag.startsWith(MatchersKey.FIELD_NOT_PRESENT)) {
            isMatched = true;
            actualValue = actualValue == null ? MatchersKey.FIELD_NOT_PRESENT : actualValue;
        } else if (compareFlag.startsWith(MatchersKey.NOT_NULL)) {
            isMatched = actualValue!=null;
            actualValue = actualValue == null ? "" : actualValue;
        } else if (compareFlag.startsWith(MatchersKey.BLANK)) {
            isMatched = expectedValue.isBlank() || expectedValue.isEmpty();
            actualValue = actualValue == null ? "" : actualValue;
        } else {
            isMatched = String.valueOf(actualValue).equalsIgnoreCase(expectedValue);
        }
        return isMatched;
    }

    private static boolean isMatcherKeyExists(List<String> values) {
        boolean isTrue=false;
        Field[] fields = MatchersKey.class.getDeclaredFields();
        for (Field field : fields) {
           try {
               if (field.get(MatchersKey.class).equals(values.get(0))) {
                   isTrue = true;
               }
           } catch(IllegalAccessException e) {
               throw new RuntimeException(e);
           }
        }
        return isTrue;
    }

    @Override
    public ApiPojo post(ApiPojo apiPojo) {
        apiPojo.getActor().attemptsTo(
                Task.where("POST - API Call : " + apiPojo.getApiName(), new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T t) {
                        apiPojo.getActor().can(CallAnApi.at(apiPojo.getApiEndpoint()));
                        RequestSpecification requestSpecification =
                                SerenityRest.given().contentType(ContentType.JSON)
                                .relaxedHTTPSValidation()
                                .accept(ContentType.JSON)
                                .log().all()
                                .body(apiPojo.getApiRequestJson());

                        if (apiPojo.getApiHeaders()!=null) {
                            requestSpecification = requestSpecification.headers(apiPojo.getApiHeaders());
                        }
                        if (apiPojo.getAuthKey()!=null) {
                            requestSpecification = requestSpecification.auth().basic(apiPojo.getAuthKey(), apiPojo.getAuthValue());
                        }
                        if (apiPojo.getBearerToken()!=null) {
                            requestSpecification = requestSpecification.auth().oauth2(new Crypt().perform().decode(apiPojo.getBearerToken()));
                        }
                        if (apiPojo.getPathParam()!=null) {
                            requestSpecification = requestSpecification.pathParams(apiPojo.getPathParam());
                        }
                        if (apiPojo.getQueryParam()!=null) {
                            requestSpecification = requestSpecification.queryParams(apiPojo.getQueryParam());
                        }
                        RequestSpecification finalRequestSpecification = requestSpecification;
                        when(apiPojo.getActor()).attemptsTo(
                                Post.to("")
                                        .with(spec -> finalRequestSpecification));
                        apiPojo.setResponse(SerenityRest.lastResponse());
                        //apiPojo.setActualStatusCode(SerenityRest.lastResponse().statusCode());
                    }
                })
        );
        if (apiPojo.isStatusCodeValidationRequired())
            validateStatusCode(apiPojo);
        if (apiPojo.isApiFieldValidationRequired())
            validateResponse(apiPojo);
        return apiPojo;
    }

    @Override
    public ApiPojo get(ApiPojo apiPojo) {
        apiPojo.getActor().attemptsTo(
                Task.where("GET - API Call : " + apiPojo.getApiName(), new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T t) {
                        apiPojo.getActor().can(CallAnApi.at(apiPojo.getApiEndpoint()));
                        RequestSpecification requestSpecification =
                                SerenityRest.given().contentType(ContentType.JSON)
                                        .relaxedHTTPSValidation()
                                        .accept(ContentType.JSON)
                                        .headers(apiPojo.getApiHeaders()
                                        );

                        if (apiPojo.getAuthKey()!=null) {
                            requestSpecification = requestSpecification.auth().basic(apiPojo.getAuthKey(), apiPojo.getAuthValue());
                        }
                        if (apiPojo.getPathParam()!=null) {
                            requestSpecification = requestSpecification.pathParams(apiPojo.getPathParam());
                        }
                        if (apiPojo.getQueryParam()!=null) {
                            requestSpecification = requestSpecification.queryParams(apiPojo.getQueryParam());
                        }
                        RequestSpecification finalRequestSpecification = requestSpecification;
                        when(apiPojo.getActor()).attemptsTo(
                                Get.resource("")
                                        .with(spec -> finalRequestSpecification).withNoReporting());
                        apiPojo.setResponse(SerenityRest.lastResponse());
                        apiPojo.setActualStatusCode(SerenityRest.lastResponse().statusCode());
                    }
                })
        );
        if (apiPojo.isStatusCodeValidationRequired())
            validateStatusCode(apiPojo);
        if (apiPojo.isApiFieldValidationRequired())
            validateResponse(apiPojo);
        return apiPojo;
    }

    @Override
    public ApiPojo delete(ApiPojo apiPojo) {
        return null;
    }

    @Override
    public ApiPojo put(ApiPojo apiPojo) {
        return null;
    }
}
