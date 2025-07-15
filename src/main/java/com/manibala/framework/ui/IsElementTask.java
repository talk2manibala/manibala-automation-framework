package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

public class IsElementTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public IsElementTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static IsElementTask with(UiPojo uiPojo) {
        return Tasks.instrumented(IsElementTask.class, uiPojo, "Is Element ");
    }
}