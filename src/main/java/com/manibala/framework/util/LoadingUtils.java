package com.manibala.framework.util;

import net.serenitybdd.screenplay.Actor;

public class LoadingUtils {

    public static void waitFor(Actor actor, int seconds) {
        int originalSeconds = seconds * 1000;
        try {
            int waitFor = (originalSeconds > 60000) ? 60000 : originalSeconds;
            Thread.sleep(waitFor);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
