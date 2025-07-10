package com.manibala.framework.util;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.junit.Assert;

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

    public static void fail(Actor actor, String message) {
        with(actor, "FAIL : "+message);
        Assert.assertTrue(false);
    }

    public static void failWithoutBreak(Actor actor, String message) {
        failWithoutBreak(actor, message, null);
    }

    public static void failWithoutBreak(Actor actor, String message, Exception e) {
        if (e==null) {
            with(actor, "FAIL (No break) : " + message);
        } else {
            with(actor, "FAIL (No break) : message - "+e.getMessage());
        }
    }
}
