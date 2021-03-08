package com.tux.utils;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: JDBC utils with dbcp
 * @author: tivnan
 * @create: 2020-2020/10/26-下午2:33
 * @version: 1.0
 **/
public class JDBCUtilsWithDBCP {

    private static DataSource dbcp = null;

    static {
        try {
            Properties pros = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            pros.load(inputStream);
            dbcp = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dbcp.getConnection();
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
