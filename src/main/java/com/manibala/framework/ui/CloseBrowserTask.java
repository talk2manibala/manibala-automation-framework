package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.WebDriver;

public class CloseBrowserTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            uiPojo.getDriver().close();
        } catch (Exception e) {
            LogUtils.with(actor, "Error when open or close the browser - " + e);
        }
    }

    public CloseBrowserTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static CloseBrowserTask with(UiPojo uiPojo) {
        return Tasks.instrumented(CloseBrowserTask.class, uiPojo, uiPojo.getOpenOrCloseApplication() +" : "+uiPojo.getUrl());
    }
}