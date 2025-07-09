package com.manibala.application.groq.api.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    public Map<String, List<String>> endpoint = new HashMap<>();

    public Service() {
        endpoint.put("GROQ_CHAT_COMPLETIONS", Arrays.asList("GROQ_CHAT_COMPLETIONS", "https://api.groq.com/openai/v1/chat/completions"));
    }

}
