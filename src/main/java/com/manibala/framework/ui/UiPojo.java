package com.manibala.framework.ui;

import lombok.Data;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.List;

@Data
public class UiPojo implements Serializable {
    private String application;
    private String url;
    private String uiFlag;
    private String openOrCloseApplication;
    private String username;
    private String password;
    private String elementName;
    private WebDriver driver;
    private Actor actor;
    private Target target;
    private WebElement element;
    private WebElementFacade elementFacade;
    private String elementType;
    private String inputTxt;
    private String actualTxt;
    private String expectedTxt;
    private String waitCondition;
    private int zoomPercent;
    private boolean isVisible;
    private boolean isDisplayed;
    private boolean isEnabled;
    private boolean isSelected;
    private boolean isJavaScriptExecutorRequired;
    private boolean isClearRequired;
    private List<String> dropdownValues;
}
