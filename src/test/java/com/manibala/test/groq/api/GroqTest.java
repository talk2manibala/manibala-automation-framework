package com.manibala.test.groq.api;

import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.application.groq.api.request.CompletionsRequest;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class GroqTest {

    @Test
    @Title("API Groq Chat Completions")
    public void test() {
        CompletionsDataPojo completionsData = ClonePojo.completionsDataPojo("search_with_content_1");
        completionsData.setActor(ActorUtils.getActor());
        CompletionsRequest.with(completionsData.getActor(), completionsData);
    }

}
