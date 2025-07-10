package com.manibala.framework.database;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.framework.api.ApiPojo;
import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.LogUtils;
import com.manibala.framework.util.RegexUtil;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class DatabaseTask implements Task {

    DbPojo dbPojo;
    private String tableName;
    private String application;

    @Override
    @Step("Query - #application - #tableName")
    public <T extends Actor> void performAs(T actor) {
        dbPojo.setSslFlag(ConfigProperties.getSslFlag().contains("yes") ? "true" : "false");
        if (dbPojo.getDbApplication().toLowerCase().contains("rfam")) {
            dbPojo.setDbConnector(ConfigProperties.getRfamDbConnector());
            dbPojo.setDbServer(ConfigProperties.getRfamDbServer());
            dbPojo.setDbName(ConfigProperties.getRfamDatabase());
            dbPojo.setDbPort(ConfigProperties.getRfamDbPort());
            dbPojo.setDbUsername(ConfigProperties.getRfamDbUsername());
            dbPojo.setDbPassword(ConfigProperties.getRfamDbPassword());
            dbPojo.setDbDriver(ConfigProperties.getRfamDbDriver());
            dbPojo.setDbUrl(dbPojo.getDbConnector() + new Crypt().perform().decode(dbPojo.getDbServer()) + ":" + dbPojo.getDbPort() + "/" + new Crypt().perform().decode(dbPojo.getDbName())+"?useSSL="+dbPojo.getSslFlag()+"&serverTimezone=UTC");
        }
        try {
            Class.forName(dbPojo.getDbDriver());
        } catch (ClassNotFoundException e) {
            LogUtils.fail(actor, "Issue in registering the DB driver - "+e.getMessage());
        }
        dbPojo = ConnectSqlDbTask.connect(dbPojo);
        dbPojo = ExecuteQueryTask.execute(dbPojo);
    }

    public DatabaseTask(DbPojo dbPojo, String tableName, String application) {
        this.dbPojo = dbPojo;
        this.tableName = tableName;
        this.application = application;
    }

    public static DbPojo getResult(DbPojo dbPojo) {
        dbPojo.getActor().attemptsTo(result(dbPojo));
        return dbPojo;
    }

    public static DbPojo getResultSet(DbPojo dbPojo) {
        dbPojo.getActor().attemptsTo(resultSet(dbPojo));
        return dbPojo;
    }

    private static DatabaseTask result(DbPojo dbPojo) {
        dbPojo.setResultSetFlag("RESULT");
        DbQn.getTableName(dbPojo);
        return Tasks.instrumented(DatabaseTask.class, dbPojo, dbPojo.getTableName(), dbPojo.getDbApplication());
    }

    private static DatabaseTask resultSet(DbPojo dbPojo) {
        dbPojo.setResultSetFlag("RESULTSET");
        DbQn.getTableName(dbPojo);
        return Tasks.instrumented(DatabaseTask.class, dbPojo, dbPojo.getTableName(), dbPojo.getDbApplication());
    }
}