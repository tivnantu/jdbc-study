package com.tu.dao;

import com.tu.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: jdbc
 * @description:
 * @author: tivnan
 * @create: 2020-2020/10/25-下午10:56
 * @version: 2.0
 **/
public abstract class BaseDAO<T> {

    private Class<T> clazz = null;

    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) actualTypeArguments[0];
    }

    /**
     * 通用增删改，考虑了事务
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public static int update(Connection conn, String sql, Object... args) {

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

    /**
     * 通用单行查询
     *
     * @param sql
     * @param args
     * @return
     */
    public T query(Connection conn, String sql, Object... args) {
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

    /**
     * 通用多行查询，考虑了事务
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public List<T> queryList(Connection conn, String sql, Object... args) {
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

    /**
     * 通用数值的标量子查询
     *
     * @param conn
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E> E queryValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Object object = rs.getObject(1);
                return (E) object;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResources(null, ps);
        }

        return null;
    }
}
