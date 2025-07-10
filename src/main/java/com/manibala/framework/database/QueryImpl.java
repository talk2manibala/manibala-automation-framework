package com.manibala.framework.database;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import net.serenitybdd.screenplay.Actor;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class QueryImpl implements Query {

    private static boolean isNoRowExists(Actor actor, String query, String application) {
        return !isRowExists(actor, query, application);
    }

    private static boolean isRowExists(Actor actor, String query, String application) {
        return true;
    }

    private static int doDml(Actor actor, String query, String application) {
        DbPojo dbPojo = ClonePojo.dbPojo();
        dbPojo.setActor(actor);
        dbPojo.setDbQuery(query);
        dbPojo.setDbApplication(application);
        dbPojo.setQueryFlag("DML");
        dbPojo.setFailOrIgnore("IGNORE");
        DatabaseTask.getResult(dbPojo);
        return dbPojo.getAffectedRowsCount();
    }

    @Override
    public int doDmlInRfam(Actor actor, String query) {
       // return doDml(query, "rfam");
        return 0;
    }

    @Override
    public boolean isNoRowExistsInRfam(Actor actor, String query) {
        return false;
    }

    @Override
    public boolean isRowExistsInRfam(Actor actor, String query) {
        return false;
    }

    @Override
    public ResultSet getResultSetInRfam(Actor actor, String query) {
        return null;
    }

    @Override
    public String getSingleResultInRfam(Actor actor, String query) {
        return "";
    }

    @Override
    public List<String> getRowsInListInRfam(Actor actor, String query) {
        return List.of();
    }

    @Override
    public Map<String, String> getRowsColumnsInMapInRfam(Actor actor, String query) {
        return Map.of();
    }

    @Override
    public String getSingleResultInRfamIfExists(Actor actor, String query) {
        return "";
    }

    @Override
    public List<String> getRowsInListInRfamIfExists(Actor actor, String query) {
        return List.of();
    }

    @Override
    public Map<String, String> getRowsColumnsInMapInRfamIfExists(Actor actor, String query) {
        return Map.of();
    }
}
