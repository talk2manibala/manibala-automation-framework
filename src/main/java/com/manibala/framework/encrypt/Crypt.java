package com.manibala.framework.encrypt;

import com.manibala.application.groq.api.config.EnvironmentProperties;

public class Crypt {
    public CryptTask perform() {
        return new ImplCryptTask();
    }
}
