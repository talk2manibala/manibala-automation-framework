package com.manibala.framework.ui;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

class UiImpl implements UiTask {

    public UiTask openApplication(Actor actor, String url, Target target) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setUrl(url == null ? "" : url);
        uiPojo.setOpenOrCloseApplication("Open the application");
        uiPojo.setTarget(target);
        actor.attemptsTo(OpenApplicationTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask closeBrowser(Actor actor) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setOpenOrCloseApplication("Close the application");
        actor.attemptsTo(CloseBrowserTask.with(uiPojo));
        return new UiImpl();
    }

    public UiTask click(Actor actor, Target target) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setTarget(target);
        actor.attemptsTo(ClickTargetTask.on(uiPojo));
        return new UiImpl();
    }

    public UiTask screenshot(Actor actor) {
        try {
            Serenity.takeScreenshot();
        } catch (Exception e) {
            LogUtils.with(actor, "Issue while taking the screenshot.");
        }
        return new UiImpl();
    }

    public UiTask type(Actor actor, Target target, String value) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setTarget(target);
        uiPojo.setInputTxt(value);
        actor.attemptsTo(TypeTask.with(uiPojo));
        return new UiImpl();
    }

}
