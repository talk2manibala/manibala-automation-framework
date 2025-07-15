package com.manibala.framework.ui;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public interface UiTask {

    UiTask openApplication(Actor actor, String url, Target target);
    UiTask closeBrowser(Actor actor);

    UiTask click(Actor actor, Target target);
    UiTask click(Actor actor, WebElementFacade elementFacade);
    UiTask clickWithJs(Actor actor, Target target);
    UiTask clickWithJs(Actor actor, WebElementFacade elementFacade);

    UiTask type(Actor actor, Target target, String value);
    UiTask typeAndEnter(Actor actor, Target target, String value);
    UiTask clearAndType(Actor actor, Target target, String value);
    UiTask clearTypeAndEnter(Actor actor, Target target, String value);

    UiTask type(Actor actor, WebElementFacade elementFacade, String value);
    UiTask typeAndEnter(Actor actor, WebElementFacade elementFacade, String value);
    UiTask clearAndType(Actor actor, WebElementFacade elementFacade, String value);
    UiTask clearTypeAndEnter(Actor actor, WebElementFacade elementFacade, String value);
    UiTask screenshot(Actor actor);

    String getText(Actor actor, Target target);
    String getText(Actor actor, WebElementFacade elementFacade);

    UiTask wait(Actor actor, Target target, String waitCondition);
    UiTask wait(Actor actor, WebElementFacade elementFacade, String waitCondition);

    UiTask moveToElement(Actor actor, Target target);
    UiTask moveToElement(Actor actor, WebElementFacade elementFacade);

    UiTask zoomOut(Actor actor, int zoomOutPercent);
    UiTask switchToWindow(Actor actor, Target target, WebElementFacade elementFacade);

    UiTask hardWait(Actor actor, int waitFor);
}
