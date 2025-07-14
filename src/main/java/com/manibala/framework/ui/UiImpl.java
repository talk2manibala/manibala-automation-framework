package com.manibala.framework.ui;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

class UiImpl implements UiTask {

    public UiTask screenshot(Actor actor) {
        try {
            Serenity.takeScreenshot();
        } catch (Exception e) {
            LogUtils.with(actor, "Issue while taking the screenshot.");
        }
        return new UiImpl();
    }

    public UiTask openApplication(Actor actor, String url, Target target) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setJavaScriptExecutorRequired(false);
        uiPojo.setUrl(url == null ? "" : url);
        uiPojo.setOpenOrCloseApplication("Open the application");
        uiPojo.setTarget(target);
        actor.attemptsTo(OpenApplicationTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask closeBrowser(Actor actor) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setJavaScriptExecutorRequired(false);
        uiPojo.setOpenOrCloseApplication("Close the application");
        actor.attemptsTo(CloseBrowserTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask click(Actor actor, Target target) {
        return performClick(actor, target, null, false);
    }

    public UiTask click(Actor actor, WebElementFacade elementFacade) {
        return performClick(actor, null, elementFacade, false);
    }

    public UiTask clickWithJs(Actor actor, Target target) {
        return performClick(actor, target, null, true);
    }

    public UiTask clickWithJs(Actor actor, WebElementFacade elementFacade) {
        return performClick(actor, null, elementFacade, true);
    }

    private UiTask performClick(Actor actor, Target target, WebElementFacade elementFacade, boolean isJavaScriptExecutor) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setJavaScriptExecutorRequired(isJavaScriptExecutor);
        uiPojo.setTarget(target);
        uiPojo.setElementFacade(elementFacade);
        actor.attemptsTo(ClickTask.on(uiPojo));
        return new UiImpl();
    }

    public UiTask clearAndType(Actor actor,  Target target, String value) {
        return performTyping(actor, value, "clear:type", target, null);
    }

    public UiTask clearTypeAndEnter(Actor actor,  Target target, String value) {
        return performTyping(actor, value, "clear:type:enter", target, null);
    }

    public UiTask type(Actor actor, Target target, String value) {
        return performTyping(actor, value, "type", target, null);
    }

    public UiTask typeAndEnter(Actor actor, Target target, String value) {
        return performTyping(actor, value, "type:enter", target, null);
    }

    public UiTask clearAndType(Actor actor,  WebElementFacade elementFacade, String value) {
        return performTyping(actor, value, "clear:type", null, elementFacade);
    }

    public UiTask clearTypeAndEnter(Actor actor,  WebElementFacade elementFacade, String value) {
        return performTyping(actor, value, "clear:type:enter", null, elementFacade);
    }

    public UiTask type(Actor actor, WebElementFacade elementFacade, String value) {
        return performTyping(actor, value, "type", null, elementFacade);
    }

    public UiTask typeAndEnter(Actor actor, WebElementFacade elementFacade, String value) {
        return performTyping(actor, value, "type:enter", null, elementFacade);
    }

    // Common private method for all typing actions
    private UiTask performTyping(Actor actor, String value, String flag, Target target, WebElementFacade elementFacade) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setJavaScriptExecutorRequired(false);
        uiPojo.setInputTxt(value);
        uiPojo.setUiFlag(flag);

        if (target != null) {
            uiPojo.setTarget(target);
        }

        if (elementFacade != null) {
            uiPojo.setElementFacade(elementFacade);
        }

        actor.attemptsTo(TypeTask.with(uiPojo));
        return new UiImpl();
    }

    public String getText(Actor actor, Target target) {
        return getText(actor, target, null);
    }

    public String getText(Actor actor, WebElementFacade elementFacade) {
        return getText(actor, null, elementFacade);
    }

    private String getText(Actor actor, Target target, WebElementFacade elementFacade) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setTarget(target);
        uiPojo.setElementFacade(elementFacade);
        actor.attemptsTo(GetTextTask.on(uiPojo));
        return uiPojo.getActualTxt();
    }

    public UiTask wait(Actor actor, Target target, String waitCondition) {
        return wait(actor, target, null, waitCondition);
    }

    public UiTask wait(Actor actor, WebElementFacade elementFacade, String waitCondition) {
        return wait(actor, null, elementFacade, waitCondition);
    }

    private UiTask wait(Actor actor, Target target, WebElementFacade elementFacade, String waitCondition) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setTarget(target);
        uiPojo.setElementFacade(elementFacade);
        uiPojo.setWaitCondition(waitCondition);
        actor.attemptsTo(WaitTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask zoomOut(Actor actor, int zoomOutPercent) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setZoomPercent(zoomOutPercent);
        actor.attemptsTo(ZoomOutTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask switchToWindow(Actor actor, Target target, WebElementFacade elementFacade) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setActor(actor);
        uiPojo.setTarget(target);
        uiPojo.setElementFacade(elementFacade);
        actor.attemptsTo(WindowsTask.with(uiPojo));
        return new UiImpl();
    }
}
