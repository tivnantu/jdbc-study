package com.tu.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.Condition;

/**
 * @project: jdbc
 * @description: connect to the DB
 * @author: tivnan
 * @create: 2020-2020/10/20-下午9:22
 * @version: 1.0
 **/
public class ConnectionM {


    /**
     * 明文直接加载驱动，暴露了第三方API
     * 暴露了信息
     * @throws SQLException
     */
    @Test
    public void testConnection01() throws SQLException {

        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();

        info.setProperty("user", "tivnan");
        info.setProperty("password", "asdfgh");

        Connection connect = driver.connect(url, info);

        System.out.println(connect);
    }

    /**
     * 使用反射加载驱动，没有暴露API
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    @Test
    public void testConnection02() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class<?> aClass = Class.forName(driverName);
        Driver driver = (Driver) aClass.newInstance();

        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();

        info.setProperty("user", "tivnan");
        info.setProperty("password", "asdfgh");

        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    /**
     * 利用DriverManager注册管理驱动
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    @Test
    public void testConnection03() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class<?> aClass = Class.forName(driverName);
        Driver driver = (Driver) aClass.newInstance();

        DriverManager.registerDriver(driver);

        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();

        info.setProperty("user", "tivnan");
        info.setProperty("password", "asdfgh");

        Connection connection = DriverManager.getConnection(url, info);
        System.out.println(connection);
    }

    /**
     * 去掉注册语句
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testConnection04() throws ClassNotFoundException, SQLException {
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);

        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();

        info.setProperty("user", "tivnan");
        info.setProperty("password", "asdfgh");


        Connection connection = DriverManager.getConnection(url, info);
        System.out.println(connection);
    }

    /**
     * 去掉读类语句
     * @throws SQLException
     */
    @Test
    public void testConnection05() throws SQLException {
//MySQL可省略
//        String driverName = "com.mysql.cj.jdbc.Driver";
//        Class.forName(driverName);


        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();

        info.setProperty("user", "tivnan");
        info.setProperty("password", "asdfgh");


        Connection connection = DriverManager.getConnection(url, info);
        System.out.println(connection);
    }

    /**
     * 数据和程序分离
     * 1. 提高移植性
     * 2. 适用于生产环境
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testConnection06() throws IOException, ClassNotFoundException, SQLException {
        InputStream inputStream = ConnectionM.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        InputStream inputStream1 = ConnectionTest.class.getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);

        System.out.println(connection);

    }

}
