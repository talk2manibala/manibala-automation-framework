package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindTextAndClickTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            new UiImpl().zoomOut(actor, 60);
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            WebElement element = driver.findElement(By.xpath("//*[contains(text(), '"+uiPojo.getInputTxt()+"']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            new UiImpl().zoomOut(actor, 90);
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public FindTextAndClickTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static FindTextAndClickTask with(UiPojo uiPojo) {
        return Tasks.instrumented(FindTextAndClickTask.class, uiPojo, "Find text and click "+uiPojo.getInputTxt());
    }
}


















