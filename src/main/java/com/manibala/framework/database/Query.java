package com.manibala.framework.database;

import net.serenitybdd.screenplay.Actor;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface Query {
    int doDmlInRfam(Actor actor, String query);
    boolean isNoRowExistsInRfam(Actor actor, String query);
    boolean isRowExistsInRfam(Actor actor, String query);
    ResultSet getResultSetInRfam(Actor actor, String query);
    String getSingleResultInRfam(Actor actor, String query);
    List<String> getRowsInListInRfam(Actor actor, String query);
    Map<String, String> getRowsColumnsInMapInRfam(Actor actor, String query);
    String getSingleResultInRfamIfExists(Actor actor, String query);
    List<String> getRowsInListInRfamIfExists(Actor actor, String query);
    Map<String, String> getRowsColumnsInMapInRfamIfExists(Actor actor, String query);
}
