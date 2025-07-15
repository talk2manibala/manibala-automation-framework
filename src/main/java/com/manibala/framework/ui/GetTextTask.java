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

public class GetTextTask implements Task {

    @Managed
    WebDriver driver;
    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if (!uiPojo.isJavaScriptExecutorRequired()) {
                if (uiPojo.getTarget() != null) {
                    uiPojo.setActualTxt(uiPojo.getTarget().resolveFor(actor).getText());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getTarget().resolveFor(actor).getTextContent());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getTarget().resolveFor(actor).getValue());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getTarget().resolveFor(actor).getTextValue());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getTarget().resolveFor(actor).getDomAttribute("value"));
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(js.executeScript("return arguments[0].value;", uiPojo.getTarget().resolveFor(actor)).toString());
                }
                if (uiPojo.getElementFacade() != null) {
                    uiPojo.setActualTxt(uiPojo.getElementFacade().getText());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getElementFacade().getTextContent());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getElementFacade().getValue());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getElementFacade().getTextValue());
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(uiPojo.getElementFacade().getDomAttribute("value"));
                    if (uiPojo.getActualTxt() == null || uiPojo.getActualTxt().isEmpty() || uiPojo.getActualTxt().isBlank())
                        uiPojo.setActualTxt(js.executeScript("return arguments[0].value;", uiPojo.getElementFacade()).toString());
                }
            }
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        } finally {
            new UiFactory().perform().screenshot(actor);
        }

    }

    public GetTextTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static GetTextTask on(UiPojo uiPojo) {
        return Tasks.instrumented(GetTextTask.class, uiPojo, "Get text "+uiPojo.getElementName());
    }
}