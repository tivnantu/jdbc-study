package com.tu.preparedstatemend.crud;

import com.tu.bean.Customer;
import com.tu.bean.Order;
import com.tu.util.JDBCUtils;
import com.tux.utils.JDBCUtilsWithDBCP;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: jdbc
 * @description: query table
 * @author: tivnan
 * @create: 2020-2020/10/22-下午11:45
 * @version: 1.0
 **/
public class PreparedStatementQueryM {

    /**
     * Customer单行查询
     */
    @Test
    public void queryTest() {


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id=? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);

            rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date date = rs.getDate(4);

                //                System.out.printf("id=%d, name=%s, email=%s, birth=%tF", id, name, email, new java.util.Date(date.getTime()));

                //                Object[] data = new Object[]{id, name, email, date};

                Customer customer = new Customer(id, email, name, date);
                System.out.println(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResources(conn, ps, rs);
        }


    }

    /**
     * 通用Customer单行查询
     *
     * @param sql
     * @param args
     * @return
     */
    public Customer queryForCustomer(String sql, Object... args) {


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {

                Customer customer = new Customer();

                for (int i = 0; i < columnCount; i++) {

                    Object columnValue = rs.getObject(i + 1);
                    String columnName = rsmd.getColumnName(i + 1);

                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer, columnValue);

                }

                return customer;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, ps, rs);
        }
        return null;
    }

    /**
     * 通用测试
     */
    @Test
    public void queryTest1() {

//        String sql = "select id,name,birth,email from customers where id =?";
//        Customer customer = queryForCustomer(sql, 1);

//        String sql = "select id,name,email from customers where id =?";
//        Customer customer = queryForCustomer(sql, 1);

        String sql = "select name,email from customers where name=?";
        Customer customer = queryForCustomer(sql, "周杰伦");

        System.out.println(customer);
    }

    /**
     * Order单行查询
     */
    @Test
    public void queryTes2() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "select order_id,order_name,order_date from `order` where order_id=?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, 1);

            rs = ps.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt(1);
                String orderName = rs.getString(2);
                Date orderDate = rs.getDate(3);

                Order order = new Order(orderId, orderName, orderDate);
                System.out.println(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResources(conn, ps, rs);
        }
    }

    /**
     * 通用Order单行查询
     *
     * @param sql
     * @param args
     * @return
     */
    public Order queryForOrder(String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();


            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();


            if (rs.next()) {
                Order order = new Order();

                for (int i = 0; i < columnCount; i++) {

                    Object colValue = rs.getObject(i + 1);

//                    String colName = rsmd.getColumnName(i + 1);
                    String colLabel = rsmd.getColumnLabel(i + 1);


                    Field field = Order.class.getDeclaredField(colLabel);
                    field.setAccessible(true);
                    field.set(order, colValue);

                }

                return order;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, ps, rs);
        }

        return null;
    }

    @Test
    public void queryTest3() {

        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id =?";
        Order order = queryForOrder(sql, 1);
        System.out.println(order);

    }

    /**
     * 通用单行查询
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T query(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {

                T instance = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {

                    String colLabel = rsmd.getColumnLabel(i + 1);
                    Object colValue = rs.getObject(i + 1);

                    Field field = clazz.getDeclaredField(colLabel);
                    field.setAccessible(true);
                    field.set(instance, colValue);
                }

                return instance;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, ps, rs);

        }
        return null;

    }

    @Test
    public void queryTest4() {

//        String sql="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id =?";
//        Order order = query(Order.class, sql, 1);
//        System.out.println(order);

        String sql = "select id,name,email,birth from customers where id=?";
        Customer customer = query(Customer.class, sql, 2);
        System.out.println(customer);
    }

    /**
     * 通用多行
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryList(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();


            ArrayList<T> arrayList = new ArrayList<>();

            while (rs.next()) {

                T instance = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {

                    String colLabel = rsmd.getColumnLabel(i + 1);
                    Object colValue = rs.getObject(i + 1);

                    Field field = clazz.getDeclaredField(colLabel);
                    field.setAccessible(true);
                    field.set(instance, colValue);
                }
                arrayList.add(instance);
            }

            return arrayList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, ps, rs);

        }
        return null;
    }

    @Test
    public void queryTest5(){
//        String sql = "select id,name,email,birth from customers where id<?";
//        List<Customer> customers = queryList(Customer.class, sql, 3);
//
//        customers.forEach(System.out::println);
        String sql="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id <?";
        List<Order> orders = queryList(Order.class, sql, 12);
        orders.forEach(System.out::println);
    }
}
