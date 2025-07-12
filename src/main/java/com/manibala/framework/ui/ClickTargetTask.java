package com.manibala.framework.ui;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebDriver;

public class ClickTargetTask implements Task {

    @Managed
    WebDriver driver;
    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            actor.attemptsTo(Click.on(uiPojo.getTarget()));
        } catch (Exception e) {
            LogUtils.with(actor, "Error when click on "+uiPojo.getTarget().getName());
        } finally {
            new UiFactory().perform().screenshot(actor);
        }

    }

    public ClickTargetTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static ClickTargetTask on(UiPojo uiPojo) {
        return Tasks.instrumented(ClickTargetTask.class, uiPojo, "Click on "+uiPojo.getTarget().getName());
    }
}