package com.tu.preparedstatemend.crud;

import com.tu.connection.ConnectionM;
import com.tu.util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: prepared statement
 * @author: tivnan
 * @create: 2020-2020/10/21-下午8:08
 * @version: 1.0
 **/
public class PreparedStatementUpdateM {

    @Test
    public void insertTest() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            InputStream inputStream = ConnectionM.class.getClassLoader().getResourceAsStream("jdbc.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);

            connection = DriverManager.getConnection(url, user, password);


            /*-------------------------------------------------------------------------------------------------------*/
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "lx");
            preparedStatement.setString(2, "lx@lx.com");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse("2000-01-01");

            preparedStatement.setString(3, String.valueOf(new java.sql.Date(date.getTime())));


            preparedStatement.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @Test
    public void insertTest2() {

        try (InputStream inputStream = ConnectionM.class.getClassLoader().getResourceAsStream("jdbc.properties")) {

            Properties properties = new Properties();
            properties.load(inputStream);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);

            try (Connection connection = DriverManager.getConnection(url, user, password)) {

                String sql = "insert into customers(name,email,birth) values(?,?,?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, "lx");
                    preparedStatement.setString(2, "lx@lx.com");

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = simpleDateFormat.parse("2000-01-01");
                    preparedStatement.setString(3, String.valueOf(new java.sql.Date(date.getTime())));

                    preparedStatement.execute();

                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertTest3() {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            ps = connection.prepareStatement(sql);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse("2020-01-01");


            ps.setString(1, "lx");
            ps.setString(2, "lx@lx.com");
            ps.setString(3, String.valueOf(new java.sql.Date(date.getTime())));

            ps.execute();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(connection, ps);
        }
    }

    @Test
    public void updateTest() {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "update customers set name=? where id=?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, "莫扎特");
            ps.setString(2, "18");

            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        JDBCUtils.closeResources(connection, ps);
    }

    @Test
    public void deleteTest() {

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "delete from customers where id =?";

            ps = connection.prepareStatement(sql);

            ps.setString(1, "19");

            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        JDBCUtils.closeResources(connection, ps);


    }

    public static int update(String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

//            ps.execute();

            return ps.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, ps);
        }

        return 0;

    }

    @Test
    public void updateTest1() {
        String sql = "delete from customers where id=?";
        update(sql, 18);
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
