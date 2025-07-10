package com.manibala.framework.database;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteQueryTask implements Task {
    DbPojo dbPojo;

    @Override
    @Step("Execute dbPojo.getDbQuery() for #flag")
    public <T extends Actor> void performAs(T actor) {
        boolean isQuerySuccess = true;
        Connection connection = null;
        boolean isTrue = true;
        String errorMsg = "";
        try {
            connection = dbPojo.getConnection();
            Statement stmt = null;
            try {
                LogUtils.with(actor, dbPojo.getDbQuery());
                if (dbPojo.getDbQuery().toLowerCase().matches("\\s*.select.*")) {
                    stmt = connection.createStatement();
                    dbPojo.setStatement(stmt);
                    dbPojo.setResultSet(stmt.executeQuery(dbPojo.getDbQuery()));
                    if (dbPojo.getResultSetFlag().equals("RESULT"))
                        SqlResultSetTask.get(dbPojo);
                }
                if (dbPojo.getDbQuery().toLowerCase().matches("\\s*update.*|\\s*delete.*|\\s*insert.*")) {
                    stmt = connection.createStatement();
                    dbPojo.setStatement(stmt);
                    int rowsAffected = stmt.executeUpdate(dbPojo.getDbQuery());
                    dbPojo.setAffectedRowsCount(rowsAffected);
                }
            } catch (Exception e) {
                isTrue=false;
                errorMsg = e.getMessage();
            } finally {
                if (stmt!=null) {
                    try {
                        stmt.close();
                        if (dbPojo.getResultSet() != null)
                            dbPojo.getResultSet().close();
                    } catch (SQLException e) {

                    }
                }
                try {
                    if (!isTrue)
                        LogUtils.fail(actor, "Issue in the execute the dbPojo.getDbQuery() : "+errorMsg);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            LogUtils.with(actor, "Issue in executing the dbPojo.getDbQuery() : " + e);
            isQuerySuccess = false;
        } finally {
            if (connection!=null)
                DisconnectSqlDbTask.from(actor, connection);
            if (!isQuerySuccess)
                LogUtils.fail(actor, "Query not success. Please check");
        }
    }

    public ExecuteQueryTask(DbPojo dbPojo) {
        this.dbPojo = dbPojo;
    }

    public static DbPojo execute(DbPojo dbPojo) {
         dbPojo.getActor().attemptsTo(with(dbPojo));
         return dbPojo;
    }

    private static ExecuteQueryTask with(DbPojo dbPojo) {
        return Tasks.instrumented(ExecuteQueryTask.class, dbPojo);
    }
}