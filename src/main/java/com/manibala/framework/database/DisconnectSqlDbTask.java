package com.manibala.framework.database;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import java.sql.Connection;
import java.sql.SQLException;

public class DisconnectSqlDbTask implements Task {

    Connection connection;

    @Override
    @Step("Disconnect Database")
    public <T extends Actor> void performAs(T actor) {
        try {
            connection.close();
            LogUtils.with(actor, "Disconnected from database");
        } catch (SQLException e) {
            LogUtils.with(actor, "Error at disconnecting the database - " + e);
        }
    }

    public DisconnectSqlDbTask(Connection connection) {
        this.connection = connection;
    }

    public static void from(Actor actor, Connection connection) {
         actor.attemptsTo(with(connection));
    }

    private static DisconnectSqlDbTask with(Connection connection) {
        return Tasks.instrumented(DisconnectSqlDbTask.class, connection);
    }
}