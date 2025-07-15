package com.manibala.framework.ui;

import com.manibala.framework.constants.WaitCondition;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class WaitTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        int waitTime=30;
        try {
            if (uiPojo.getTarget()!=null) {
                switch (flag) {
                    case WaitCondition.CLICKABLE:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.isClickable()).forNoMoreThan(waitTime).seconds());
                        break;
                    case WaitCondition.VISIBLE:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.isVisible()).forNoMoreThan(waitTime).seconds());
                        break;
                    case WaitCondition.PRESENT:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.isPresent()).forNoMoreThan(waitTime).seconds());
                        break;
                    case WaitCondition.ENABLE:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.isEnabled()).forNoMoreThan(waitTime).seconds());
                        break;
                    case WaitCondition.NOTVISIBLE:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.isNotVisible()).forNoMoreThan(waitTime).seconds());
                        break;
                    case WaitCondition.CONTAINS_TEXT:
                        actor.attemptsTo(WaitUntil.the(uiPojo.getTarget(), WebElementStateMatchers.containsText(uiPojo.getExpectedTxt())).forNoMoreThan(waitTime).seconds());
                        break;
                    default:
                        break;
                }
            }
            if (uiPojo.getElementFacade()!=null) {
                switch (flag) {
                    case WaitCondition.CLICKABLE:
                        uiPojo.getElementFacade().withTimeoutOf(Duration.ofSeconds(waitTime)).waitUntilClickable();
                        break;
                    case WaitCondition.VISIBLE:
                        uiPojo.getElementFacade().withTimeoutOf(Duration.ofSeconds(waitTime)).waitUntilVisible();
                        break;
                    case WaitCondition.PRESENT:
                        uiPojo.getElementFacade().withTimeoutOf(Duration.ofSeconds(waitTime)).waitUntilPresent();
                        break;
                    case WaitCondition.ENABLE:
                        uiPojo.getElementFacade().withTimeoutOf(Duration.ofSeconds(waitTime)).waitUntilEnabled();
                        break;
                    case WaitCondition.NOTVISIBLE:
                        uiPojo.getElementFacade().withTimeoutOf(Duration.ofSeconds(waitTime)).waitUntilNotVisible();
                        break;
                    case WaitCondition.CONTAINS_TEXT:
                        uiPojo.getElementFacade().waitForCondition().until(ExpectedConditions.textToBePresentInElement(uiPojo.getElementFacade(), uiPojo.getExpectedTxt()));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public WaitTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static WaitTask with(UiPojo uiPojo) {
        return Tasks.instrumented(WaitTask.class, uiPojo, "Wait for "+uiPojo.getElementName());
    }
}