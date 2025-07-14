package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class TypeTask implements Task {

    @Managed
    WebDriver driver;
    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            if (uiPojo.getTarget()!=null) {
                if (uiPojo.getUiFlag().contains("clear"))
                    uiPojo.getTarget().resolveFor(actor).clear();
                actor.attemptsTo(Enter.theValue(uiPojo.getInputTxt()).into(uiPojo.getTarget()));
                if (uiPojo.getUiFlag().contains("enter"))
                    uiPojo.getTarget().resolveFor(actor).sendKeys(Keys.ENTER);
            } else {
                if (uiPojo.getUiFlag().contains("clear"))
                    uiPojo.getElementFacade().clear();
                uiPojo.getElementFacade().type(uiPojo.getInputTxt());
                if (uiPojo.getUiFlag().contains("enter"))
                    uiPojo.getElementFacade().sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            LogUtils.with(actor, "Error when typing text "+uiPojo.getInputTxt()+" on text box "+uiPojo.getTarget().getName());
        } finally {
            new UiFactory().perform().screenshot(actor);
        }
    }

    public TypeTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static TypeTask with(UiPojo uiPojo) {
        return Tasks.instrumented(TypeTask.class, uiPojo, "Enter "+uiPojo.getInputTxt() +" on textbox "+uiPojo.getTarget().getName());
    }
}