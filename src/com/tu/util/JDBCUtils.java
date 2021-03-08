package com.tu.util;

import com.tu.connection.ConnectionM;
import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @project: jdbc
 * @description: the utils of jdbc
 * @author: tivnan
 * @create: 2020-2020/10/21-下午11:52
 * @version: 1.0
 **/
public class JDBCUtils {

    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void closeResources(Connection connection, PreparedStatement preparedStatement) {
//        try {
//            if (connection != null)
//                connection.close();
//
//            if (preparedStatement != null)
//                preparedStatement.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        try {
            DbUtils.close(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            DbUtils.close(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {

//        try {
//            if (conn != null) {
//                conn.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//            if (rs != null) {
//                rs.close();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        try {
            DbUtils.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            DbUtils.close(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            DbUtils.close(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
