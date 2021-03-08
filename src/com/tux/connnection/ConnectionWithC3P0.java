package com.tux.connnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.tu.connection.ConnectionM;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: get db connection with C3P0
 * @author: tivnan
 * @create: 2020-2020/10/26-上午11:16
 * @version: 1.0
 **/
public class ConnectionWithC3P0 {

    @Test
    public void C3P0Test() throws Exception {

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("tivnan");
        cpds.setPassword("asdfgh");
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);

//        DataSources.destroy(cpds);
    }

    @Test
    public void C3P0Test1() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);

    }
}
