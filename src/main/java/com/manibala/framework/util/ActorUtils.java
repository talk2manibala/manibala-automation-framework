package com.manibala.framework.util;

import net.serenitybdd.screenplay.Actor;

public class ActorUtils {

    public static Actor getActor() {
        return Actor.named("User_"+DateUtils.getCurrentDateTimeNoSpaceWithRandomNumber());
    }

    public static void clearActor(Actor actor) {
        actor=null;
    }

}
