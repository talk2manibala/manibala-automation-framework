package com.manibala.framework.database;

import com.manibala.framework.util.JsonUtils;
import com.manibala.framework.util.LogUtils;
import com.manibala.framework.util.SerenityUtil;
import net.serenitybdd.screenplay.Actor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetComparator {

    public static boolean getResult(Actor actor, String message, ResultSet resultSet1, ResultSet resultSet2) {
        return getResult(actor, message, resultSet1, resultSet2, List.of(""));
    }

    public static boolean getResult(Actor actor, String message, ResultSet resultSet1, ResultSet resultSet2, List<String> IGNORE_FIELDS) {
        boolean isTrue=false;
        try {
            List<Map<String, Object>> resultSet1Data = extractData(actor, resultSet1);
            List<Map<String, Object>> resultSet2Data = extractData(actor, resultSet2);
            isTrue = compareData(actor, message, resultSet1Data, resultSet2Data, IGNORE_FIELDS);
            resultSet1.close();
            resultSet2.close();
        } catch (Exception e) {
            LogUtils.fail(actor, "Issue in getting the result of result set comparison");
        }
        return isTrue;
    }

    private static List<Map<String, Object>> extractData(Actor actor, ResultSet resultSet) {
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i=1; i<=columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            LogUtils.fail(actor, "Issue in ResultSetComparator extract data");
        }
        return data;
    }

    private static boolean compareData(Actor actor, String message, List<Map<String, Object>> data1, List<Map<String, Object>> data2, List<String> IGNORE_FIELDS) {
        List<String> mismatches = new ArrayList<>();
        // Compare size first
        if (data1.size() != data2.size()) {
            return false;
        }
        List<String> dataSet1 = new ArrayList<>();
        List<String> dataSet2 = new ArrayList<>();
        // Compare each row
        for (int i=0; i<data1.size(); i++) {
            Map<String, Object> row1 = data1.get(i);
            Map<String, Object> row2 = data2.get(i);
            // Compare values of each column
            for (String key : row1.keySet()) {
                dataSet1.add(key + " ==> "+row1.get(key));
                dataSet2.add(key + " ==> "+row2.get(key));
                if (!IGNORE_FIELDS.contains(key)) {
                    String value1 = String.valueOf(row1.get(key));
                    String value2 = String.valueOf(row2.get(key));
                    if (JsonUtils.isJsonString(value1) && JsonUtils.isJsonString(value2)) {
                        boolean isTrue = new JsonUtils().compareJsonWithMismatchExpected(actor, key.toUpperCase() + " ==> " + message, value1, value2, IGNORE_FIELDS);
                        if (isTrue) continue;
                    }
                    if (!value1.equals(value2)) {
                        mismatches.add(key + " : " + value1 + " <> " + value1);
                    }
                }
            }
        }
        SerenityUtil.attachContents(message+" 1", dataSet1);
        SerenityUtil.attachContents(message+" 2", dataSet2);
        String passOrFail = !mismatches.isEmpty() ? "FAIL" : "PASS";
        SerenityUtil.attachContents(passOrFail + " ==> " + message.toUpperCase(), mismatches);
        return mismatches.isEmpty();
    }
}
