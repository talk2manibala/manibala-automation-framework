package com.manibala.test.groq.api;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.application.groq.api.request.CompletionsRequest;
import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.ActorUtils;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class GroqApiTest {

    @Test
    @Title("API Groq Chat Completions")
    public void test() {
        CompletionsDataPojo completionsData = ClonePojo.completionsDataPojo("search_with_content_1");
        completionsData.setActor(ActorUtils.getActor());
        CompletionsRequest.with(completionsData.getActor(), completionsData);
    }

    @Test
    public void test1() {
        String u = new Crypt().perform().encode(ConfigProperties.getRfamDbServer());
        String p = new Crypt().perform().encode(ConfigProperties.getRfamDatabase());
        System.out.println(u);
        System.out.println(p);
        System.out.println(new Crypt().perform().decode(u));
        System.out.println(new Crypt().perform().decode(p));
    }

}
