package com.manibala.application.groq.api.data;

import com.manibala.framework.data.TestData;
import io.restassured.response.Response;
import lombok.Data;
import net.serenitybdd.screenplay.Actor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CompletionsDataPojo implements Serializable {
    private String tcId;
    private Actor actor;
    private transient Response response;
    private String model;
    private String role;
    private String content;
    private int max_tokens;
    private double temperature;
    private String stop;
    private Map<String, String> typeAndText;

    public CompletionsDataPojo(String tcId) {
        List<String> data = TestData.readExcel("groq_chat_completions_api", tcId);
        setTcId(tcId);
        setModel(data.get(0));
        setRole(data.get(1));
        setContent(data.get(2));
        setMax_tokens(Integer.valueOf(data.get(3)).intValue());
        setTemperature(Double.valueOf(data.get(4)).doubleValue());
        setStop(data.get(5));
        Map<String, String> typeAndText = new HashMap<>();
        int i=0;
        for (String type : data.get(6).split(":")) {
            typeAndText.put("type"+(i+1), type);
            typeAndText.put("text"+(i+1), data.get(7).split(":")[i]);
            i++;
        }
        setTypeAndText(typeAndText);
    }

}
