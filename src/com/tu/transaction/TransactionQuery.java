package com.tu.transaction;

import com.tu.util.JDBCUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/25-下午8:00
 * @version:
 **/
public class TransactionQuery {

    /**
     * 通用多行
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> transactionQueryList(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps, rs);
        }
        return null;
    }
}
