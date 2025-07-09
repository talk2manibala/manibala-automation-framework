package com.manibala.framework.encrypt;

import com.manibala.framework.api.ApiPojo;

import java.util.List;

public interface CryptTask {
    String encode(String value);
    String decode(String value);
}
