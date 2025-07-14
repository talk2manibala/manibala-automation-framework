package com.manibala.framework.encrypt;

public class Crypt {
    public CryptTask perform() {
        return new ImplCryptTask();
    }
}
