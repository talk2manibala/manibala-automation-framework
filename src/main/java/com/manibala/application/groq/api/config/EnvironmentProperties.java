package com.manibala.application.groq.api.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class EnvironmentProperties {
    private static final String ENV_PROPERTIES = "src/test/resources/environment.properties";
    private static Properties env = readConfigProperties();

    public static Properties readConfigProperties() {
        Properties env = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(ENV_PROPERTIES);
            env.load(input);
        } catch (IOException e) {
            log.info("Error : Reading environment properties - "+e.getMessage());
        } finally {
            if (input!=null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.info("Error : Closing environment properties - "+e.getMessage());
                }
            }
        }
        return env;
    }

    public static void setProperty(String key, String value) {
        System.setProperty(key, value);
    }

    public static String getProperty(String key) {
        env = readConfigProperties();
        String keySysProp = System.getProperty(key);
        String output = (keySysProp!=null && keySysProp.length()>0) ? keySysProp : env.getProperty(key);
        output = output==null ? "" : output;
        log.info("Info : Get config properties : " + key + " = "+output);
        return output;
    }

    public static String getEnvironment() {
        return getProperty("environment");
    }

    public static String getApiEngine() {
        return getProperty("api_engine");
    }

}
