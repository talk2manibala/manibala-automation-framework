package com.manibala.application.groq.ui;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.framework.constants.WaitCondition;
import com.manibala.framework.ui.UiFactory;
import com.manibala.framework.ui.WaitTask;
import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class LoginWebsiteTask implements Task {

    @Override
    @Step("Login Website Application")
    public <T extends Actor> void performAs(T actor) {
        try {
            new UiFactory().perform()
                .openApplication(actor, ConfigProperties.getWebsiteUiUrl(), WebsiteLocators.USERNAME_TXT)
                .wait(actor, WebsiteLocators.USERNAME_TXT, WaitCondition.VISIBLE)
                .type(actor, WebsiteLocators.USERNAME_TXT, ConfigProperties.getWebsiteUiUsername())
                .type(actor, WebsiteLocators.PASSWORD_TXT, ConfigProperties.getWebsiteUiPassword())
                .click(actor, WebsiteLocators.LOGIN_BTN)
                .zoomOut(actor, 60)
                .moveToElement(actor, WebsiteLocators.SIGN_UP_BTN)
                .hardWait(actor, 10)
                .getText(actor, WebsiteLocators.USERNAME_TXT);
        } catch (Exception e) {
            LogUtils.with(actor, "Issue at Website Login - " + e);
        }
    }

    public LoginWebsiteTask() {
    }

    public static void with(Actor actor) {
         actor.attemptsTo(with());
    }

    private static LoginWebsiteTask with() {
        return Tasks.instrumented(LoginWebsiteTask.class);
    }
}