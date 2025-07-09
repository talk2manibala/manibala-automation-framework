package com.manibala.framework.util;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public class LogUtils {

    public static void with(Actor actor, String message) {
        try {
            if (message == null)
                message = "NULL";
            actor.should(message);
        } catch (Exception e) {
        }
    }

    public static void with(Actor actor, String subject, String message) {
        actor.attemptsTo(
            Task.where(subject, new Performable() {
                    @Override
                    public <T extends Actor> void performAs(T actor) {
                        with(actor, message);
                    }
                }
            )
        );
    }
}
