package com.manibala.framework.database;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.screenplay.Actor;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

class QueryImpl implements Query {

    private static ResultSet getResultSet(DbPojo dbPojo) {
        dbPojo.setResultSetFlag("RESULT_SET");
        dbPojo.setFailOrIgnore("FAIL");
        DatabaseTask.getResultSet(dbPojo);
        ResultSet output = dbPojo.getResultSet();
        if (output==null) LogUtils.fail(dbPojo.getActor(), "No result for this query in database");
        return output;
    }

    private static int getRowCount(DbPojo dbPojo) {
        dbPojo.setQueryFlag("ROW_COUNT");
        dbPojo.setFailOrIgnore("FAIL");
        DatabaseTask.getResult(dbPojo);
        return dbPojo.getAffectedRowsCount();
    }

    private static String getSingleResult(DbPojo dbPojo) {
        dbPojo.setQueryFlag("SINGLE");
        dbPojo.setFailOrIgnore("FAIL");
        DatabaseTask.getResult(dbPojo);
        String output = dbPojo.getResultStr();
        output = (output==null) ? "" : output;
        return output;
    }

    private static List<String> getRowsInList(DbPojo dbPojo) {
        dbPojo.setQueryFlag("MULTIPLE_ROWS");
        dbPojo.setFailOrIgnore("FAIL");
        DatabaseTask.getResult(dbPojo);
        List<String> output = dbPojo.getResultList();
        if (output==null) LogUtils.fail(dbPojo.getActor(), "No result for this query in database");
        return output;
    }

    private static Map<String, String> getRowsColumnsInMap(DbPojo dbPojo) {
        dbPojo.setQueryFlag("MULTIPLE_COLUMNS");
        dbPojo.setFailOrIgnore("FAIL");
        DatabaseTask.getResult(dbPojo);
        Map<String, String> output = dbPojo.getResultMap();
        if (output==null) LogUtils.fail(dbPojo.getActor(), "No result for this query in database");
        return output;
    }

    private static String getSingleResultIfExists(DbPojo dbPojo) {
        dbPojo.setQueryFlag("SINGLE");
        dbPojo.setFailOrIgnore("IGNORE");
        DatabaseTask.getResult(dbPojo);
        String output = dbPojo.getResultStr();
        output = (output==null) ? "" : output;
        return output;
    }

    private static List<String> getRowsInListIfExists(DbPojo dbPojo) {
        dbPojo.setQueryFlag("MULTIPLE_ROWS");
        dbPojo.setFailOrIgnore("IGNORE");
        DatabaseTask.getResult(dbPojo);
        return dbPojo.getResultList();
    }

    private static Map<String, String> getRowsColumnsInMapIfExists(DbPojo dbPojo) {
        dbPojo.setQueryFlag("MULTIPLE_COLUMNS");
        dbPojo.setFailOrIgnore("IGNORE");
        DatabaseTask.getResult(dbPojo);
        return dbPojo.getResultMap();
    }

    private static boolean isNoRowExists(DbPojo dbPojo) {
        return !isRowExists(dbPojo);
    }

    private static boolean isRowExists(DbPojo dbPojo) {
        String output = getSingleResultIfExists(dbPojo);
        output = (output==null) ? "0" : output;
        return Integer.parseInt(output)>0;
    }

    private static int doDml(DbPojo dbPojo) {
        dbPojo.setQueryFlag("DML");
        dbPojo.setFailOrIgnore("IGNORE");
        DatabaseTask.getResult(dbPojo);
        return dbPojo.getAffectedRowsCount();
    }

    @Override
    public int doDmlInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return doDml(dbPojo);
    }

    @Override
    public boolean isNoRowExistsInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return isNoRowExists(dbPojo);
    }

    @Override
    public boolean isRowExistsInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return isRowExists(dbPojo);
    }

    @Override
    public ResultSet getResultSetInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getResultSet(dbPojo);
    }

    @Override
    public String getSingleResultInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getSingleResult(dbPojo);
    }

    @Override
    public List<String> getRowsInListInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getRowsInList(dbPojo);
    }

    @Override
    public Map<String, String> getRowsColumnsInMapInRfam(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getRowsColumnsInMap(dbPojo);
    }

    @Override
    public String getSingleResultInRfamIfExists(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getSingleResultIfExists(dbPojo);
    }

    @Override
    public List<String> getRowsInListInRfamIfExists(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getRowsInListIfExists(dbPojo);
    }

    @Override
    public Map<String, String> getRowsColumnsInMapInRfamIfExists(Actor actor, String query) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication("rfam");
        return getRowsColumnsInMapIfExists(dbPojo);
    }
}
