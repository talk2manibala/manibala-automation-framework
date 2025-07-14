package com.manibala.framework.ui;

import com.manibala.framework.util.LogUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public class ZoomOutTask implements Task {

    UiPojo uiPojo;
    String flag;

    @Override
    @Step("#flag")
    public <T extends Actor> void performAs(T actor) {
        try {
            uiPojo.setZoomPercent(uiPojo.getZoomPercent()==0 ? 70 : uiPojo.getZoomPercent());
            JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
            js.executeScript("document.body.style.zoom='"+uiPojo.getZoomPercent()+"%'");
        } catch (Exception e) {
            LogUtils.with(actor, "Error when open or close the browser - " + e);
        }
    }

    public ZoomOutTask(UiPojo uiPojo, String flag) {
        this.uiPojo = uiPojo;
        this.flag = flag;
    }

    static ZoomOutTask with(UiPojo uiPojo) {
        return Tasks.instrumented(ZoomOutTask.class, uiPojo, "Zoom out to "+uiPojo.getZoomPercent()+"%");
    }
}