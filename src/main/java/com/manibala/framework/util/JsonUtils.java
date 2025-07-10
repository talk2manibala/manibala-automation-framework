package com.manibala.framework.util;

import io.restassured.response.Response;
import net.serenitybdd.screenplay.Actor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static boolean isJsonString(String str) {
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            return false;
        }

    }

    public String getJsonStringFromResponse(Response response) {
        return response.getBody().asString();
    }

    public JSONObject getJsonObjectFromResponse(Response response) {
        return new JSONObject(response.getBody().asString());
    }

    public JSONObject getJsonObjectFromJsonStr(String jsonStr) {
        return new JSONObject(jsonStr);
    }

    public JSONObject maskFieldInArray(JSONObject jsonObject1, String fieldToIgnore) {
        JSONObject jsonObject=null;
        try {
            String[] fields = fieldToIgnore.split("\\.");
            JSONArray jsonArray = jsonObject1.getJSONArray(fields[0].replace("[]", ""));
            for (int i=0; i<jsonArray.length(); i++) {
                jsonObject = jsonObject1;
                jsonObject = jsonArray.getJSONObject(i);
                for (int j=1; j<fields.length-1; j++) {
                    jsonObject = jsonObject.getJSONObject(fields[j]);
                }
            }
            jsonObject.put(fields[fields.length-1], "XXXXX");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public JSONObject maskNestedField(JSONObject jsonObject, String fieldToIgnore) {
        try {
            String[] fields = fieldToIgnore.split("\\.");
            for (int i=0; i<fields.length-1; i++) {
                jsonObject = jsonObject.getJSONObject(fields[i]);
            }
            jsonObject.put(fields[fields.length-1], "XXXXX");
        } catch (JSONException e) {
            throw new JSONException(e);
        }
        return jsonObject;
    }

    public JSONObject maskDirectField(JSONObject jsonObject, String field) {
        try {
            if (jsonObject.get(field)!=null) {
                jsonObject.put(field, "XXXXX");
            }
        } catch (JSONException e) {
            throw new JSONException(e);
        }
        return jsonObject;
    }

    public String ignoreFields(Response response, List<String> fields) {
        return ignoreFields(getJsonObjectFromResponse(response), fields);
    }

    public String ignoreFields(String jsonStr, List<String> fields) {
        return ignoreFields(getJsonObjectFromJsonStr(jsonStr), fields);
    }

    public String ignoreFields(JSONObject jsonObject1, List<String> fieldsToIgnore) {
        try {
            for (String fieldToIgnore : fieldsToIgnore) {
                if (!fieldToIgnore.contains(".")) {
                    jsonObject1 = maskDirectField(jsonObject1, fieldToIgnore);
                } else {
                    if (fieldToIgnore.contains("[]")) {
                        jsonObject1 = maskFieldInArray(jsonObject1, fieldToIgnore);
                    } else {
                        jsonObject1 = maskNestedField(jsonObject1, fieldToIgnore);
                    }
                }
            }
        } catch (JSONException e) {
            throw new JSONException(e);
        }
        return jsonObject1.toString();
    }

    public boolean compareJsonWithMismatchExpected(Actor actor, String message, String jsonStr1, String jsonStr2, List<String> fieldsToIgnore) {
        return compareJson(actor, message, ignoreFields(jsonStr1, fieldsToIgnore), ignoreFields(jsonStr2, fieldsToIgnore), true, false);
    }

    public boolean compareJson(Actor actor, String message, String json1, String json2, boolean strictToFail, boolean expectMatch) {
        JSONCompareResult result = null;
        boolean isTrue=false;
        try {
            result = JSONCompare.compareJSON(json1, json2, strictToFail ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
            List<FieldComparisonFailure> fieldFailures = result.getFieldFailures();
            SerenityUtil.attachContent(message.toUpperCase()+" 1", json1);
            SerenityUtil.attachContent(message.toUpperCase()+" 2", json2);
            List<String> comparisonResults = new ArrayList<>();
            for (FieldComparisonFailure fieldFailure : fieldFailures) {
                comparisonResults.add("Field : "+fieldFailure.getField()+"; Expected - "+fieldFailure.getExpected()+"; Actual - "+fieldFailure.getActual());
            }
            String expected = expectMatch ? " ==> EXPECTED MATCH" : " ==> EXPECTED MISMATCH";
            String actualResult = (expectMatch && result.passed()) ? "PASS ==> " :
                    (!expectMatch && !result.passed()) ? "PASS ==> " : "FAIL ==> ";
            if (expectMatch) {
                SerenityUtil.attachContents(actualResult+" " +message.toUpperCase()+expected, comparisonResults);
                isTrue = result.passed();
            } else {
                SerenityUtil.attachContents(actualResult+" "+message.toUpperCase() + expected, comparisonResults);
                isTrue = !result.passed();
            }
        } catch (JSONException e) {
            LogUtils.fail(actor, "Issue when comparing JSON");
        }
        return isTrue;
    }

}
