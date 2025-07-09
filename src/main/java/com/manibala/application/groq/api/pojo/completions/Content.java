package com.manibala.application.groq.api.pojo.completions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "type",
        "text",
        "image_url",
        "temperature"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Content {

    @JsonProperty("type")
    private String type;
    @JsonProperty("text")
    private String text;
    @JsonProperty("image_url")
    private ImageUrl imageUrl;
    @JsonProperty("temperature")
    private Double temperature;

    public static Content generate(CompletionsDataPojo completionsData) {
        Content payload = new Content();
        for (String key : completionsData.getTypeAndText().keySet()) {
            if (completionsData.getTypeAndText().get(key).split("::")[0].equalsIgnoreCase("text")) {
                payload.setType(completionsData.getTypeAndText().get(key).split("::")[0]);
                payload.setText(completionsData.getTypeAndText().get(key).split("::")[1]);
            }
            if (completionsData.getTypeAndText().get(key).split("::")[0].contains("url")) {
                payload.setType(completionsData.getTypeAndText().get(key).split("::")[0]);
                payload.setImageUrl(ImageUrl.generate(completionsData));
            }
        }
        payload.setTemperature(completionsData.getTemperature());
        return payload;
    }

}