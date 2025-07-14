package com.manibala.framework.encrypt;

import java.io.UnsupportedEncodingException;

public class ImplCryptTask implements CryptTask {

    @Override
    public String encode(String value) {
        String newValue = value;
        byte[] encoded = java.util.Base64.getEncoder().encode(newValue.getBytes());
        try {
            newValue = new String(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        encoded = java.util.Base64.getEncoder().encode(newValue.getBytes());
        try {
            newValue = new String(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newValue;
    }

    @Override
    public String decode(String value) {
        String newValue = value;
        byte[] decoded = java.util.Base64.getDecoder().decode(newValue.getBytes());
        try {
            newValue = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        decoded = java.util.Base64.getDecoder().decode(newValue.getBytes());
        try {
            newValue = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newValue;

    }
}
