package com.manibala.test.groq.ui;

import com.manibala.application.groq.ui.LoginWebsiteTask;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class UiTest {

    @Test
    @Title("Verify Website Login")
    public void test() {
        LoginWebsiteTask.with(ActorUtils.getActor());
    }

}
