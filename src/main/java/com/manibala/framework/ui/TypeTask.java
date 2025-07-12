package com.manibala.framework.ui;

import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;
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
            actor.attemptsTo(Enter.theValue(uiPojo.getInputTxt()).into(uiPojo.getTarget()));
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