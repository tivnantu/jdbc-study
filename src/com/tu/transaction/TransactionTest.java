package com.tu.transaction;

import com.tu.bean.UserTable;
import com.tu.preparedstatemend.crud.PreparedStatementUpdateM;
import com.tu.util.JDBCUtils;
import org.junit.Test;
import sun.security.krb5.internal.ccache.CCacheInputStream;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.tu.transaction.TransactionUpdate.transactionUpdate;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/25-下午7:37
 * @version:
 **/
public class TransactionTest {

    @Test
    public void transactionTest() {

        String sql1 = "update user_table set balance=balance-100 where user=?";
        PreparedStatementUpdateM.update(sql1, "AA");

//        模拟网络错误
        System.out.println(10 / 0);

        String sql2 = "update user_table set balance=balance+100 where user=?";
        PreparedStatementUpdateM.update(sql2, "BB");

        System.out.println("转账成功");
    }

    @Test
    public void transactionTest1() {

        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            String sql1 = "update user_table set balance=balance-100 where user=?";
            transactionUpdate(conn, sql1, "AA");

            System.out.println(10 / 0);

            String sql2 = "update user_table set balance=balance+100 where user=?";
            transactionUpdate(conn, sql2, "BB");

            conn.commit();
            System.out.println("转账成功");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBCUtils.closeResources(conn, null);
        }

    }


    /**
     * 下面两个测试，用于测试脏读
     */

    @Test
    public void transactionTest2() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select user,password,balance from user_table where user=?";
        Connection conn = JDBCUtils.getConnection();

        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        List<UserTable> tables = TransactionQuery.transactionQueryList(conn, UserTable.class, sql, "CC");

        tables.forEach(System.out::println);

    }

    @Test
    public void transactionTest3() throws SQLException, IOException, ClassNotFoundException, InterruptedException {
        String sql = "update user_table set balance=1000 where user=?";

        Connection conn = JDBCUtils.getConnection();

        conn.setAutoCommit(false);

        int cc = transactionUpdate(conn, sql, "CC");


        if (cc > 0){
            System.out.println("修改成功1000,开始恢复");
        }

        Thread.sleep(15000);
        conn.rollback();
        System.out.println("恢复到6000");
    }

}
