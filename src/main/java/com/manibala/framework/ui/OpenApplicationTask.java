package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.WebDriver;

public class OpenApplicationTask implements Task {

    @Managed
    WebDriver driver;

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            uiPojo.setDriver(driver);
            actor.can(BrowseTheWeb.with(driver));
            try {
                actor.attemptsTo(Open.url(uiPojo.getUrl()));
                if (uiPojo.getTarget().isVisibleFor(actor)) {
                    LogUtils.with(actor, "Application opened successfully");
                } else {
                    uiPojo.getDriver().navigate().refresh();
                }
            } catch (Exception e) {
                uiPojo.getDriver().navigate().refresh();
            } finally {
                new UiFactory().perform().screenshot(actor);
            }

        } catch (Exception e) {
            LogUtils.with(actor, "Error when open or close the browser - " + e);
        }
    }

    public OpenApplicationTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static OpenApplicationTask with(UiPojo uiPojo) {
        return Tasks.instrumented(OpenApplicationTask.class, uiPojo, uiPojo.getOpenOrCloseApplication() + " : " + uiPojo.getUrl());
    }
}