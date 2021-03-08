package com.tu.statement.crud;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @project: jdbc
 * @description: statement test
 * @author: tivnan
 * @create: 2020-2020/10/21-下午7:12
 * @version: 1.0
 **/
public class StatementM {
    // 使用Statement的弊端：需要拼写sql语句，并且存在SQL注入的问题
    public static void main(String[] args) {
        int a = 100;
        System.out.println("开始");
        Scanner scanner = new Scanner(System.in);

        System.out.print("user name: ");
        String userName = scanner.next();
//        String userName = scanner.nextLine();

        System.out.print("user password: ");
        String password = scanner.next();
//        String password = scanner.nextLine();

        String sql = "SELECT user,password FROM user_table WHERE user='" + userName + "' AND password='" + password + "'";
        UserM user = get(sql, UserM.class);

        if (user != null) {
            System.out.println("登录");
        } else {
            System.out.println("登录失败");
        }
    }

    // 使用Statement实现对数据表的查询操作
    public static <T> T get(String sql, Class<T> clazz) {
        T t = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // 1.加载配置文件
            InputStream is = StatementM.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            // 2.读取配置信息
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            // 3.加载驱动
            Class.forName(driverClass);

            // 4.获取连接
            conn = DriverManager.getConnection(url, user, password);

            st = conn.createStatement();

            rs = st.executeQuery(sql);

            // 获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();

            // 获取结果集的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {

                t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    // //1. 获取列的名称
                    // String columnName = rsmd.getColumnName(i+1);

                    // 1. 获取列的别名
                    String columnName = rsmd.getColumnLabel(i + 1);

                    // 2. 根据列名获取对应数据表中的数据
                    Object columnVal = rs.getObject(columnName);

                    // 3. 将数据表中得到的数据，封装进对象
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
