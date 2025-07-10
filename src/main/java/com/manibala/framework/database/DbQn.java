package com.manibala.framework.database;

import com.manibala.framework.util.RegexUtil;

public class DbQn {
    public static DbPojo getTableName(DbPojo dbPojo) {
        String tableName = "";
        String query = dbPojo.getDbQuery();
        tableName = query.matches("\\s*select.*") ? RegexUtil.getGroup1(query, "[^>]*?[from|FROM]\\s+(\\w+)\\s+.*") :
                query.matches("\\s*update.*") ? RegexUtil.getGroup1(query, "\\s*update\\s+(\\w+)\\s+.*") :
                        query.matches("\\s*delete.*") ? RegexUtil.getGroup1(query, "\\s*delete\\s*from\\s*(\\w+)\\s*.*") :
                                query.matches("\\s*insert.*") ? RegexUtil.getGroup1(query, "\\s*insert\\s*into\\s*(\\w+)\\s*.*") :
                                        tableName;
        dbPojo.setTableName(tableName);
        return dbPojo;
    }
}
