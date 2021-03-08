package com.tux.connnection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: get connection with DBCP
 * @author: tivnan
 * @create: 2020-2020/10/26-下午2:15
 * @version: 1.0
 **/
public class ConnectionWithDBCP {
    @Test
    public void DBCPTest() throws SQLException {
        BasicDataSource dbcp = new BasicDataSource();

        dbcp.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dbcp.setUrl("jdbc:mysql:///test");
        dbcp.setUsername("tivnan");
        dbcp.setPassword("asdfgh");

        dbcp.setInitialSize(10);
        dbcp.setMaxActive(10);

        Connection conn = dbcp.getConnection();

        System.out.println(conn);

    }


    @Test
    public void DBCPTest1() throws Exception {
        Properties pros = new Properties();
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(inputStream);
        DataSource dbcp = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = dbcp.getConnection();

        System.out.println(conn);
    }
}
