package com.tu.preparedstatemend.crud.batchinsert;

import com.tu.util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @project: jdbc
 * @description: batch insert
 * @author: tivnan
 * @create: 2020-2020/10/25-下午2:55
 * @version: 1.0
 **/
public class BatchInsert {

//    77774
    @Test
    public void batchInsertTest() {

        Connection conn = null;
        Statement st = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            st = conn.createStatement();

            for (int i = 0; i < 10000; i++) {
                String sql = "insert into goods(name) values('" + "name_" + i + "')";
                st.execute(sql);
            }

            long end = System.currentTimeMillis();

            System.out.println("start:" + start);
            System.out.println("end:" + end);
            System.out.println("between" + ((start - end)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                if (st != null)
                    st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

//    68316
    @Test
    public void batchInsertTest1() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < 10000; i++) {
                ps.setString(1, "name_" + i);
                ps.execute();
            }
            long end = System.currentTimeMillis();

            System.out.println("start:" + start);
            System.out.println("end:" + end);
            System.out.println("between" + ((start - end)));
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

//    61655
    @Test
    public void batchInsertTest2() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 10000; i++) {
                ps.setString(1, "name_" + i);

                ps.addBatch();
                if (i % 500 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }
            long end = System.currentTimeMillis();

            System.out.println("start:" + start);
            System.out.println("end:" + end);
            System.out.println("between" + ((start - end)));
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

//    2049
    @Test
    public void batchInsertTest3() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);

            conn.setAutoCommit(false);

            for (int i = 1; i <= 10000; i++) {
                ps.setString(1, "name_" + i);

                ps.addBatch();
                if (i % 500 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }

            }

            conn.commit();

            long end = System.currentTimeMillis();

            System.out.println("start:" + start);
            System.out.println("end:" + end);
            System.out.println("between" + ((start - end)));
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
}
