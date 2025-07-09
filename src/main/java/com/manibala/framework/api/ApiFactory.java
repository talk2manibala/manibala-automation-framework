package com.manibala.framework.api;

import com.manibala.application.groq.api.config.EnvironmentProperties;

public class ApiFactory {
    public ApiTask perform() {
        if (EnvironmentProperties.getApiEngine().equalsIgnoreCase("rest"))
            return new RestApiTask();
        return null;
    }
}
