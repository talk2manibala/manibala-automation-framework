package com.manibala.application.groq.api.pojo.completions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.manibala.application.groq.api.data.CompletionsDataPojo;
import lombok.Data;


@JsonPropertyOrder({
        "role",
        "content"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Message {

    @JsonProperty("role")
    private String role;
    @JsonProperty("content")
    private String content;
/*    @JsonProperty("content")
    private List<Content> contents;*/

    public static Message generate(CompletionsDataPojo completionsData) {
        Message payload = new Message();
        payload.setRole(completionsData.getRole());
        payload.setContent(completionsData.getContent());
//        List<Content> contents1 = new ArrayList<>();
//        contents1.add(Content.generate(completionsData));
//        payload.setContents(contents1);
        return payload;
    }

}
