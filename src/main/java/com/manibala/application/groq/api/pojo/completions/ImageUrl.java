package com.manibala.application.groq.api.pojo.completions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import lombok.Data;

@JsonPropertyOrder({
        "url"
})
@Data
public class ImageUrl {

    @JsonProperty("url")
    private String url;

    public static ImageUrl generate(CompletionsDataPojo completionsData) {
        ImageUrl payload = new ImageUrl();
        for (String key : completionsData.getTypeAndText().keySet()) {
            if (completionsData.getTypeAndText().get(key).split("::")[0].contains("url")) {
                payload.setUrl(completionsData.getTypeAndText().get(key).split("::")[1]);
            }
        }
        return payload;
    }

}