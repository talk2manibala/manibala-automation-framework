package com.manibala.framework.ui;

public class UiFactory {
    public UiTask perform() {
        return new UiImpl();
    }
}
