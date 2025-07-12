package com.manibala.application.groq.api.config;

import com.manibala.framework.encrypt.Crypt;
import com.manibala.framework.util.ActorUtils;
import com.manibala.framework.util.LogUtils;
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

    public static void setProperty(String key, String value) {
        System.setProperty(key, value);
    }

    public static String getProperty(String key) {
        config = readConfigProperties();
        String keySysProp = System.getProperty(key);
        String output = (keySysProp!=null && keySysProp.length()>0) ? keySysProp : config.getProperty(key);
        output = output==null ? "" : output;
        log.info("Info : Get config properties : " + key + " = "+output);
        return output;
    }

    public static String getGroqBearerToken() {
        return getProperty("groq_btk");
    }

    public static String getTestDataPath() {
        return getProperty("test_data_path");
    }

    public static String getRfamDbUsername() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_rfam_db_username");
    }

    public static String getRfamDbPassword() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_rfam_db_password");
    }

    public static String getRfamDbServer() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_rfam_db_server");
    }

    public static String getRfamDatabase() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_rfam_db_name");
    }

    public static String getRfamDbPort() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_rfam_db_port");
    }

    public static String getSslFlag() {
        return getProperty("rfam_ssl_config");
    }

    public static String getRfamDbConnector() {
        return getProperty("rfam_db_connector");
    }

    public static String getRfamDbDriver() {
        return getProperty("rfam_db_driver");
    }

    public static String getWebsiteUiUrl() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_website_ui_url");
    }

    public static String getWebsiteUiUsername() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_website_ui_username");
    }

    public static String getWebsiteUiPassword() {
        return getProperty(EnvironmentProperties.getEnvironment()+"_website_ui_password");
    }

}
