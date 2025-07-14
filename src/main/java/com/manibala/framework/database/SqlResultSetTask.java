package com.manibala.framework.database;

import com.manibala.framework.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlResultSetTask implements Task {

    DbPojo dbPojo;

    @Override
    @Step("DB Result")
    public <T extends Actor> void performAs(T actor) {
        int rowCount=0;
        try {
            rowCount = dbPojo.getResultSet().getRow();
        } catch (SQLException e) {

        }
        if (!dbPojo.getDbUrl().contains("sybase")) {
            try {
                while (dbPojo.getResultSet().next()) {
                    rowCount++;
                }
                LogUtils.with(actor, "Row size : "+rowCount);
                if (rowCount == 0) {
                    if (dbPojo.getFailOrIgnore().equalsIgnoreCase("FAIL") && !dbPojo.getQueryFlag().equalsIgnoreCase("IS_ROW_EXISTS")) {
                        LogUtils.with(actor, "No results found for the query");
                    } else {
                        LogUtils.failWithoutBreak(actor, "No results found for the query");
                    }
                    dbPojo.setQueryFlag("NO_FLAG");
                } else {
                    try {
                        dbPojo.getResultSet().beforeFirst();
                    } catch (SQLException e) {

                    }
                }
                actor.remember("ROW_COUNT", rowCount);
                actor.remember("IS_ROW_EXISTS", (rowCount>0));
            } catch (SQLException e) {
                LogUtils.fail(actor, "Issue in capture the resultSet : "+e.getMessage());
            }
        }
        if (dbPojo.getQueryFlag().contains("SINGLE")) {
            try {
                dbPojo.getResultSet().first();
                String output = dbPojo.getResultSet().getString(1);
                LogUtils.with(actor, "OUTPUT : "+output);
                dbPojo.setResultStr(output);
                output = (output==null) ? "null" : output;
            } catch (Exception e) {
                LogUtils.fail(actor, "Error at Database.getSingleResultForQuery - "+e);
            } finally {
                try {
                    dbPojo.getResultSet().close();
                } catch (SQLException e) {

                }
            }
        }

        if (dbPojo.getQueryFlag().equals("MULTIPLE_ROWS")) {
            List<String> list = new ArrayList<>();
            try {
                ResultSetMetaData md = dbPojo.getResultSet().getMetaData();
                int columns = md.getColumnCount();
                while (dbPojo.getResultSet().next()) {
                    for (int i=1; i<=columns; i++) {
                        list.add(dbPojo.getResultSet().getString(i));
                    }
                }
                dbPojo.setResultList(list);
                LogUtils.with(actor, ListUtils.listToCommaSeparatedString(list));
            } catch (Exception e) {
                LogUtils.fail(actor, "Error at Database.getValuesInList - " + e);
            } finally {
                try {
                    dbPojo.getResultSet().close();
                } catch (SQLException e) {

                }
            }
        }

        if (dbPojo.getQueryFlag().equals("MULTIPLE_COLUMNS")) {
            Map<String, String> map = new HashMap<>();
            try {
                ResultSetMetaData md = dbPojo.getResultSet().getMetaData();
                int columns = md.getColumnCount();
                int row=1;
                while (dbPojo.getResultSet().next()) {
                    for (int i=1; i<=columns; ++i) {
                        String key="";
                        if (rowCount==1) {
                            key = md.getColumnLabel(i).replaceAll("trim\\(([^>]*?)\\)", "$1");
                        } else {
                            key = "ROW" + row + "." + md.getColumnLabel(i).replaceAll("trim\\(([^>]*?)\\)", "$1");
                        }
                        Object object=null;
                        try {
                            object = dbPojo.getResultSet().getObject(i);
                        } catch (SQLException e) {
                            LogUtils.fail(actor, "There may be issue in access the object. Please check here or ignore...");
                        }
                        String value = (object==null) ? "" : String.valueOf(object);
                        map.put(key, value);
                    }
                    row++;
                }
                dbPojo.setResultMap(map);
                try {
                    SerenityUtil.attachContents("QUERY RESULT", map);
                } catch (OutOfMemoryError e) {
                    String filename = "result_set_" + DateUtils.getCurrentDateTimeNoSpaceWithRandomNumber()+".csv";
                    FileUtils.writeHashMapToJson(actor, map, filename);
                }
            } catch (Exception e) {
                LogUtils.fail(actor, "Fail at Database.getDataInMap - "+e);
            } finally {
                try {
                    dbPojo.getResultSet().close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public SqlResultSetTask(DbPojo dbPojo) {
        this.dbPojo=dbPojo;
    }

    public static void get(DbPojo dbPojo) {
        dbPojo.getActor().attemptsTo(with(dbPojo));
    }

    private static SqlResultSetTask with(DbPojo dbPojo) {
        return Tasks.instrumented(SqlResultSetTask.class, dbPojo);
    }
}