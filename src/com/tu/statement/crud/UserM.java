package com.tu.statement.crud;

/**
 * @project: jdbc
 * @description: user bean
 * @author: tivnan
 * @create: 2020-2020/10/21-下午7:15
 * @version: 1.0
 **/
public class UserM {
    private String user;
    private String password;

    public UserM() {
    }

    public UserM(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [user=" + user + ", password=" + password + "]";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
