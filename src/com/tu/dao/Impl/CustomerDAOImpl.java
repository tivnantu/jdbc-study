package com.tu.dao.Impl;

import com.tu.bean.Customer;
import com.tu.dao.BaseDAO;
import com.tu.dao.CustomerDAO;
import com.tu.util.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @project: jdbc
 * @description: customer dao impl
 * @author: tivnan
 * @create: 2020-2020/10/26-上午9:30
 * @version:
 **/
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteByID(Connection conn, int id) {
        String sql = "delete from customers where id =?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "update customers set name=? ,email=?,birth=? where id=?";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth(), cust.getId());
    }

    @Override
    public Customer getInstanceByID(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id=?";
        Customer customer = query(conn, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> customers = queryList(conn, sql);
        return customers;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return queryValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return queryValue(conn, sql);
    }
}
