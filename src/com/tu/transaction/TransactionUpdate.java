package com.tu.transaction;

import com.tu.util.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/25-下午8:01
 * @version:
 **/
public class TransactionUpdate {
    public static int transactionUpdate(Connection conn, String sql, Object... args) {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

//            ps.execute();

            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps);
        }

        return 0;

    }
}
