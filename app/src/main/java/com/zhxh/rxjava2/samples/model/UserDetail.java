package com.zhxh.rxjava2.samples.model;

/**
 * Created by zhxh on 2018/1/18
 */

public class UserDetail {

    public UserDetail(long id) {
        this.id = id;
    }

    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
