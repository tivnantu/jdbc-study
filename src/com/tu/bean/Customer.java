package com.tu.bean;

import java.sql.Date;

/**
 * @project: jdbc
 * @description: customer bean
 * @author: tivnan
 * @create: 2020-2020/10/23-上午10:23
 * @version: 1.0
 **/
public class Customer {

    private int id;
    private String email;
    private String name;
    private Date birth;

    public Customer() {
    }

    public Customer(int id, String email, String name, Date birth) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "id = " + id + ", name = " + name + ", email = " + email + ", birth = " + birth + ";";
    }
}
