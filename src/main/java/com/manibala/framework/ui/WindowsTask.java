package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowsTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            WebDriver driver = BrowseTheWeb.as(actor).getDriver();
            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                driver.switchTo().window(windowHandle);
                boolean isElementVisible = false;
                try {
                    if (uiPojo.getTarget()!=null)
                        isElementVisible = uiPojo.getTarget().isVisibleFor(actor);
                    if (uiPojo.getElementFacade()!=null)
                        isElementVisible = uiPojo.getElementFacade().isVisible();
                } catch (Exception e) {

                }
                String elementName = uiPojo.getTarget()!=null ? uiPojo.getTarget().getName() : uiPojo.getElementFacade().toString();
                if (isElementVisible) {
                    LogUtils.with(actor, "Switched to window successfully as the element "+elementName+" exists.");
                    break;
                }
            }
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public WindowsTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static WindowsTask with(UiPojo uiPojo) {
        return Tasks.instrumented(WindowsTask.class, uiPojo, "Switch to window and look for element");
    }
}