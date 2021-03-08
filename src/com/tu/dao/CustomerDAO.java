package com.tu.dao;

import com.tu.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDAO {

    //    增加
    void insert(Connection conn, Customer cust);

    //    删除
    void deleteByID(Connection conn, int id);

    //    改
    void update(Connection conn, Customer cust);

    //    查询一条记录
    Customer getInstanceByID(Connection conn, int id);

    //    查询所有记录
    List<Customer> getAll(Connection conn);

    //    返回条目数
    Long getCount(Connection conn);

    //    返回最大的生日数
    Date getMaxBirth(Connection conn);

}
