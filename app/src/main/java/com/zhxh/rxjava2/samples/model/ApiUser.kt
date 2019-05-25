package com.zhxh.rxjava2.samples.model

/**
 * Created by zhxh on 2018/1/18
 */
class ApiUser {
    var id: Long = 0
    var firstname: String? = null
    var lastname: String? = null

    override fun toString(): String {
        return "ApiUser{" +
            "id=" + id +
            ", 名字='" + firstname + '\''.toString() +
            ", 姓氏='" + lastname + '\''.toString() +
            '}'.toString()
    }
}
