package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RemoveElementTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].remove();", uiPojo.getElement());
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public RemoveElementTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static RemoveElementTask with(UiPojo uiPojo) {
        return Tasks.instrumented(RemoveElementTask.class, uiPojo, "Remove element "+uiPojo.getElementName());
    }
}