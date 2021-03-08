package com.tux.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: JDBC utils with C3P0
 * @author: tivnan
 * @create: 2020-2020/10/26-下午12:08
 * @version: 1.0
 **/
public class JDBCUtilsWithC3P0 {

    private  static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

    public static Connection getConnection() throws SQLException {
        Connection conn = cpds.getConnection();
        return conn;
    }


}
