package com.manibala.test.groq.ui;

import com.manibala.application.groq.ui.IrctcLoginLocators;
import com.manibala.framework.ui.BrowserTask;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import org.junit.jupiter.api.Test;

public class UiTest {

    @Test
    @Title("Verify IRCTC Login")
    public void test() {
        BrowserTask.openApplication(ActorUtils.getActor(), IrctcLoginLocators.USERNAME);
    }

}
