package com.manibala.framework.ui;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.sql.SQLException;

public class BrowserTask implements Task {

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
            if (flag.toUpperCase().startsWith("OPEN")) {
                try {
                    actor.attemptsTo(Open.url(uiPojo.getUrl()));
                    if (uiPojo.getTarget().isVisibleFor(actor)) {
                        LogUtils.with(actor, "Application opened successfully");
                    } else {
                        uiPojo.getDriver().navigate().refresh();
                    }
                } catch (Exception e) {
                    uiPojo.getDriver().navigate().refresh();
                }

            }
            if (flag.toUpperCase().startsWith("CLOSE"))
                uiPojo.getDriver().close();

        } catch (Exception e) {
            LogUtils.with(actor, "Error when open or close the browser - " + e);
        }
    }

    public BrowserTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    public static void close(Actor actor) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setOpenOrCloseApplication("Close the application");
        uiPojo.setUrl("");
        actor.attemptsTo(with(uiPojo));
    }

    public static void openApplication(Actor actor, Target target) {
        String url = ConfigProperties.getIrctcUiUrl();
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setUrl(url==null ? "" : url);
        uiPojo.setOpenOrCloseApplication("Open the application");
        uiPojo.setTarget(target);
        actor.attemptsTo(with(uiPojo));
    }

    private static BrowserTask with(UiPojo uiPojo) {
        return Tasks.instrumented(BrowserTask.class, uiPojo, uiPojo.getOpenOrCloseApplication() +" : "+uiPojo.getUrl());
    }
}