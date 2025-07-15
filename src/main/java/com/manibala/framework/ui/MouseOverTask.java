package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.interactions.Actions;

public class MouseOverTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            Actions actions = new Actions(BrowseTheWeb.as(actor).getDriver());
            actions.moveToElement(uiPojo.getTarget().resolveFor(actor).getElement()).build().perform();
        } catch (Exception e) {
            LogUtils.fail(actor, "Failed when "+flag+" - "+e.getMessage());
        }
    }

    public MouseOverTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static MouseOverTask with(UiPojo uiPojo) {
        return Tasks.instrumented(MouseOverTask.class, uiPojo, "Mouse over "+uiPojo.getElementName());
    }
}