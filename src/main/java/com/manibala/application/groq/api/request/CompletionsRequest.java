package com.manibala.application.groq.api.request;

import com.manibala.application.groq.api.config.ConfigProperties;
import com.manibala.application.groq.api.config.Service;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.application.groq.api.pojo.clone.ClonePojo;
import com.manibala.application.groq.api.pojo.completions.CompletionsPayload;
import com.manibala.framework.api.ApiFactory;
import com.manibala.framework.api.ApiPojo;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CompletionsRequest implements Task {
    private CompletionsDataPojo completionsData;

    @Override
    @Step("API - Groq Chat Completions")
    public <T extends Actor> void performAs(T actor) {
        ApiPojo apiPojo = ClonePojo.apiPojo();
        apiPojo.setActor(completionsData.getActor());
        apiPojo.setApiName(new Service().endpoint.get("GROQ_CHAT_COMPLETIONS").get(0));
        apiPojo.setApiEndpoint(new Service().endpoint.get("GROQ_CHAT_COMPLETIONS").get(1));
        apiPojo.setApiRequestJson(CompletionsPayload.generate(completionsData));
        apiPojo.setStatusCodeValidationRequired(true);
        apiPojo.setBearerToken(ConfigProperties.getGroqBearerToken());
        new ApiFactory().perform().post(apiPojo);
        new ApiFactory().perform().validateStatusCode(apiPojo);
    }

    public CompletionsRequest(CompletionsDataPojo completionsData) {
        this.completionsData = completionsData;
    }

    public static CompletionsDataPojo with(Actor actor, CompletionsDataPojo completionsData) {
        actor.attemptsTo(with(completionsData));
        return completionsData;
    }

    private static CompletionsRequest with(CompletionsDataPojo completionsData) {
        return Tasks.instrumented(CompletionsRequest.class, completionsData);
    }
}
