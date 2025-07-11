package com.manibala.application.groq.ui;

import net.serenitybdd.screenplay.targets.Target;

public interface IrctcLoginLocators {
    Target USERNAME = Target.the("Username").locatedBy("//input[@placeholder='User Name']");
}
