package com.tuy.dbutils;

import com.tu.bean.Customer;
import com.tu.util.JDBCUtils;
import com.tux.utils.JDBCUtilsWithDBCP;
import com.tux.utils.JDBCUtilsWithDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/26-下午3:26
 * @version:
 **/
public class QueryRunnerTest {

    @Test
    public void insertTest() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        Connection conn = JDBCUtilsWithDruid.getConnection();
        runner.update(conn, sql, "xxxx", "xxxx@xxx", "2020-02-02");
        JDBCUtils.closeResources(conn, null);
    }

    @Test
    public void queryTest() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select id,name,email,birth from customers where id=?";
        Connection conn = JDBCUtilsWithDBCP.getConnection();

        BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
        Customer customer = queryRunner.query(conn, sql, handler, 12);
        System.out.println(customer);
    }

    @Test
    public void queryTest1() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select id,name,email,birth from customers where id=?";
        Connection conn = JDBCUtilsWithDBCP.getConnection();

        ArrayHandler handler = new ArrayHandler();
        Object[] customer = queryRunner.query(conn, sql, handler, 12);
        System.out.println(Arrays.toString(customer));
    }
}
