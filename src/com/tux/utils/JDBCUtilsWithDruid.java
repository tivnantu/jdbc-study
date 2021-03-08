package com.tux.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/26-下午3:07
 * @version: 1.0
 **/
public class JDBCUtilsWithDruid {

    private static DataSource dds = null;

    static {
        try {
            Properties pros = new Properties();
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(inputStream);
            dds = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dds.getConnection();
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
