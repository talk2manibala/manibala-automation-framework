package com.manibala.application.groq.api.config;

import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigProperties {

    private static final String CONFIG_PROPERTIES = "src/main/resources/config.properties";
    private static Properties config = readConfigProperties();

    public static Properties readConfigProperties() {
        Properties config = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(CONFIG_PROPERTIES);
            config.load(input);
        } catch (IOException e) {
            log.info("Error : Reading config properties - "+e.getMessage());
        } finally {
            if (input!=null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.info("Error : Closing config properties - "+e.getMessage());
                }
            }
        }
        return config;
    }

    public static String getGroqBearerToken() {
        config = readConfigProperties();
        String key = "groq_bearer_token";
        String keySysProp = System.getProperty(key);
        String output = (keySysProp!=null && keySysProp.length()>0) ? keySysProp : config.getProperty(key);
        output = output==null ? "" : output;
        log.info("Info : Get config properties : " + key + " = "+output);
        return output;
    }

    public static String getTestDataPath() {
        config = readConfigProperties();
        String key = "test_data_path";
        String keySysProp = System.getProperty(key);
        String output = (keySysProp!=null && keySysProp.length()>0) ? keySysProp : config.getProperty(key);
        output = output==null ? "" : output;
        log.info("Info : Get config properties : " + key + " = "+output);
        return output;
    }
}
