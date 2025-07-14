package com.manibala.application.groq.api.pojo.clone;

import com.manibala.application.groq.api.data.CompletionsDataPojo;
import com.manibala.framework.api.ApiPojo;
import com.manibala.framework.database.DbPojo;
import com.manibala.framework.ui.UiPojo;
import org.apache.commons.lang3.SerializationUtils;

public class ClonePojo {

    public static ApiPojo apiPojo() {
        ApiPojo apiPojo = new ApiPojo();
        return SerializationUtils.clone(apiPojo);
    }

    public static DbPojo dbPojo() {
        DbPojo dbPojo = new DbPojo();
        return SerializationUtils.clone(dbPojo);
    }

    public static UiPojo uiPojo() {
        UiPojo uiPojo = new UiPojo();
        return SerializationUtils.clone(uiPojo);
    }

    public static CompletionsDataPojo completionsDataPojo(String tcId) {
        CompletionsDataPojo completionsData = new CompletionsDataPojo(tcId);
        return SerializationUtils.clone(completionsData);
    }
}
