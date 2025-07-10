package com.manibala.framework.database;

import lombok.Data;
import net.serenitybdd.screenplay.Actor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Data
public class DbPojo implements Serializable {
    private Actor actor;
    private String dbServer;
    private String dbUsername;
    private String dbPassword;
    private String dbPort;
    private String dbDriver;
    private String dbConnector;
    private String dbName;
    private String dbApplication;
    private String dbQuery;
    private String queryFlag;
    private String dbUrl;
    private String failOrIgnore;
    private String resultSetFlag;
    private int affectedRowsCount;
    private Statement statement;
    private Connection connection;
    private ResultSet resultSet;
    private String sslFlag;
    private String tableName;
    private String resultStr;
    private List<String> resultList;
    private Map<String, String> resultMap;
}
