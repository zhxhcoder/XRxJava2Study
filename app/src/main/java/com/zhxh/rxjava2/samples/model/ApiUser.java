package com.zhxh.rxjava2.samples.model;

/**
 * Created by zhxh on 2018/1/18
 */
public class ApiUser {
    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", 姓名='" + firstname + '\'' +
                ", 姓氏='" + lastname + '\'' +
                '}';
    }
}
