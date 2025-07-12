package com.manibala.framework.ui;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public interface UiTask {

    UiTask openApplication(Actor actor, String url, Target target);
    UiTask closeBrowser(Actor actor);
    UiTask click(Actor actor, Target target);
    UiTask type(Actor actor, Target target, String value);
    UiTask screenshot(Actor actor);
}
