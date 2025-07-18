package com.manibala.test.groq.ui;

import com.manibala.application.groq.ui.LoginWebsiteTask;
import com.manibala.framework.jira.ZephyrIntegration;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.io.IOException;

@ExtendWith(SerenityJUnit5Extension.class)
public class UiTest {

    @AfterEach
    public void tearDown() {
        try {
            ZephyrIntegration.postTestResult("MANI-1", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Title("Verify Website Login")
    public void test() {
        LoginWebsiteTask.with(ActorUtils.getActor());
    }

}
