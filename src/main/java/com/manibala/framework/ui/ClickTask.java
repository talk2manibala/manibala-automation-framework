package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ClickTask implements Task {

    @Managed
    WebDriver driver;
    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            if (!uiPojo.isJavaScriptExecutorRequired()) {
                if (uiPojo.getTarget() != null)
                    actor.attemptsTo(Click.on(uiPojo.getTarget()));
                if (uiPojo.getElementFacade() != null)
                    actor.attemptsTo(Click.on(uiPojo.getElementFacade()));
            } else {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                if (uiPojo.getTarget() != null)
                    js.executeScript(uiPojo.getTarget().getCssOrXPathSelector()+".click()");
                if (uiPojo.getElementFacade() != null)
                    js.executeScript(uiPojo.getElementFacade()+".click()");
            }
        } catch (Exception e) {
            LogUtils.with(actor, "Error when click on "+uiPojo.getTarget().getName());
        } finally {
            new UiFactory().perform().screenshot(actor);
        }

    }

    public ClickTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static ClickTask on(UiPojo uiPojo) {
        return Tasks.instrumented(ClickTask.class, uiPojo, "Click on "+uiPojo.getTarget().getName());
    }
}