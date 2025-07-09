package com.manibala.framework.encrypt;

import com.manibala.framework.api.API;
import com.manibala.framework.api.ApiPojo;
import com.manibala.framework.asserts.AssertQn;
import com.manibala.framework.constants.MatchersKey;
import com.manibala.framework.util.ListUtils;
import com.manibala.framework.util.LogUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.apache.commons.math3.util.Precision;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.when;

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
