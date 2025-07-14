package com.manibala.application.groq.api.pojo.completions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.framework.util.LogUtils;
import com.manibala.framework.util.PayloadUtil;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "model",
        "messages"
})
public class CompletionsPayload {

    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;
    @JsonProperty("model")

    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("messages")
    public List<Message> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public static String generate(CompletionsDataPojo completionsData) {
        CompletionsPayload payload = new CompletionsPayload();
        payload.setModel(completionsData.getModel());
        List<Message> messages = new ArrayList<>();
        messages.add(Message.generate(completionsData));
        payload.setMessages(messages);
        String payload1 = "";
        try {
            payload1 = PayloadUtil.defaultMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            LogUtils.with(completionsData.getActor(), "Issue in generating the completions payload - "+e.getMessage());
        }
        return payload1;
    }
}
