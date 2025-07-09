package com.manibala.application.groq.api.pojo.clone;

import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.framework.api.ApiPojo;
import io.cucumber.java.bs.A;
import org.apache.commons.lang3.SerializationUtils;

public class ClonePojo {

    public static ApiPojo apiPojo() {
        ApiPojo apiPojo = new ApiPojo();
        ApiPojo clonedApiPojo = SerializationUtils.clone(apiPojo);
        return clonedApiPojo;
    }

    public static CompletionsDataPojo completionsDataPojo(String tcId) {
        CompletionsDataPojo completionsData = new CompletionsDataPojo(tcId);
        CompletionsDataPojo clonedCompletionsData = SerializationUtils.clone(completionsData);
        return clonedCompletionsData;
    }
}
