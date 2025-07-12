package com.manibala.framework.ui;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebDriver;

public class ClickElementTask implements Task {

    @Managed
    WebDriver driver;
    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            actor.attemptsTo(Click.on(uiPojo.getElementFacade()));
        } catch (Exception e) {
            LogUtils.with(actor, "Error when typing text "+uiPojo.getInputTxt()+" on text box "+uiPojo.getElementFacade().toString());
        } finally {
            new UiFactory().perform().screenshot(actor);
        }

    }

    public ClickElementTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    public static void on(Actor actor, WebElementFacade target) {
        UiPojo uiPojo = ClonePojo.uiPojo();
        uiPojo.setElementFacade(target);
        actor.attemptsTo(on(uiPojo));
    }

    private static ClickElementTask on(UiPojo uiPojo) {
        return Tasks.instrumented(ClickElementTask.class, uiPojo, "Click on "+uiPojo.getElementFacade().getElement());
    }
}