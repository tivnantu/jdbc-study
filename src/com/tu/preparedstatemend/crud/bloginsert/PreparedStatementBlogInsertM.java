package com.tu.preparedstatemend.crud.bloginsert;

import com.tu.bean.Customer;
import com.tu.util.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @project: jdbc
 * @description: insert blog with prepared statement
 * @author: tivnan
 * @create: 2020-2020/10/25-下午1:56
 * @version: 1.0
 **/
public class PreparedStatementBlogInsertM {


    @Test
    public void insertBlogTest() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
            ps = conn.prepareStatement(sql);

            ps.setObject(1, "y");
            ps.setObject(2, "y@y.com");
            ps.setObject(3, "2020-01-01");

            FileInputStream inputStream = new FileInputStream(new File("src/mountain.jpg"));
            ps.setBlob(4, inputStream);

            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResources(conn, ps);
        }

    }


    @Test
    public void queryBlogTest() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id=?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, 22);

            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date date = rs.getDate(4);

                Customer customer = new Customer(id, email, name, date);

                System.out.println(customer);


                Blob photo = rs.getBlob("photo");
                if (photo != null) {
                    try (
                            InputStream inputStream = photo.getBinaryStream();
                            FileOutputStream outputStream = new FileOutputStream("src/y.png");
                    ) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, len);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        JDBCUtils.closeResources(conn, ps, rs);
    }
}
