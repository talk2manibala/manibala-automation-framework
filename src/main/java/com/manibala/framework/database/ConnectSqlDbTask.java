package com.manibala.framework.database;

import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.LogUtils;
import com.manibala.framework.util.SerenityUtil;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class ConnectSqlDbTask implements Task {

    DbPojo dbPojo;

    @Override
    @Step("Database Connection")
    public <T extends Actor> void performAs(T actor) {
        try {
            if (dbPojo.getConnection() != null && !dbPojo.getConnection().isClosed()) {
                DisconnectSqlDbTask.from(actor, dbPojo.getConnection());
            }
        } catch (SQLException e) {
            LogUtils.with(actor, "Error at check the Database connection - " + e);
        }
        try {
            Connection connection = DriverManager.getConnection(dbPojo.getDbUrl(), new Crypt().perform().decode(dbPojo.getDbUsername()), new Crypt().perform().decode(dbPojo.getDbPassword()));
            dbPojo.setConnection(connection);
            SerenityUtil.attachContents("Database", Arrays.asList(dbPojo.getDbUrl(), new Crypt().perform().decode(dbPojo.getDbUsername()), new Crypt().perform().decode(dbPojo.getDbPassword())));
            boolean isTrue = dbPojo.getConnection() != null && !dbPojo.getConnection().isClosed();
            String connectionStatus = (isTrue) ? "Database connected successfully" : "Database not connected";
            LogUtils.with(actor, connectionStatus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectSqlDbTask(DbPojo dbPojo) {
        this.dbPojo = dbPojo;
    }

    public static DbPojo connect(DbPojo dbPojo) {
        dbPojo.getActor().attemptsTo(with(dbPojo));
        return dbPojo;
    }

    private static ConnectSqlDbTask with(DbPojo dbPojo) {
        return Tasks.instrumented(ConnectSqlDbTask.class, dbPojo);
    }
}