package com.zhxh.rxjava2.samples.rxbus;

/**
 * Created by zhxh on 2018/8/24
 */
public class Msg {
    private int code;
    private Object object;

    public Msg(int code, Object o) {
        this.code = code;
        this.object = o;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
