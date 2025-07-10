package com.manibala.framework.database;

public class QueryFactory {
    public Query perform() {
        return new QueryImpl();
    }
}
