package com.manibala.framework.asserts;

import net.serenitybdd.screenplay.Actor;
import org.junit.Assert;

public class AssertQn {
    public static void verify(Actor actor, String message, boolean isTrue) {
        Assert.assertTrue(message, isTrue);
    }
}
