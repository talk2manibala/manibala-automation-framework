package com.manibala.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.serenitybdd.screenplay.Actor;

import java.io.*;
import java.util.Map;

public class FileUtils {

    public static void write(Actor actor, String data, String file) {
        try {
            File fnew = new File(file);
            FileWriter f2 = new FileWriter(fnew, false);
            f2.write(data);
            f2.close();
        } catch (IOException e) {
            LogUtils.fail(actor, "Unable to write the file - "+e.getMessage());
        }
    }

    public static void writeHashMapToJson(Actor actor, Map map, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File(filePath), map);
        } catch (IOException e) {
            LogUtils.fail(actor, "Unable to convert hashMap to Json : "+e.getMessage());
        }
    }

    public static String read(Actor actor, String file) {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line=bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            LogUtils.fail(actor, "File read error : "+e.getMessage());
        }
        return content.toString();
    }

    public static boolean isExists(Actor actor, String file) {
        boolean isTrue=false;
        try {
            File fnew = new File(file);
            isTrue = fnew.exists();
        } catch (Exception e) {
            LogUtils.with(actor, "Is File Exists (Error) - "+e.getMessage());
        }
        return isTrue;
    }

}
