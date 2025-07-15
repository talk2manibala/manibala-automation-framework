package com.manibala.application.groq.ui;

import net.serenitybdd.screenplay.targets.Target;

public interface WebsiteLocators {
    Target USERNAME_TXT = Target.the("Username").locatedBy("//input[@id='username']");
    Target PASSWORD_TXT = Target.the("Password").locatedBy("//input[@id='password']");
    Target LOGIN_BTN = Target.the("Login").locatedBy("//button[@title='Log In']");
    Target SIGN_UP_BTN = Target.the("Sign Up").locatedBy("//a[text()='Sign Up']");
}
