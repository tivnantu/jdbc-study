package com.tux.connnection;

import com.alibaba.druid.pool.DruidDataSource;
import com.tu.util.JDBCUtils;
import com.tux.utils.JDBCUtilsWithDruid;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/26-下午2:55
 * @version: 1.0
 **/
public class ConnectionWithDruid {

    @Test
    public void DruidTest() {

        Connection connection = JDBCUtilsWithDruid.getConnection();
        System.out.println(connection);



    }
}
