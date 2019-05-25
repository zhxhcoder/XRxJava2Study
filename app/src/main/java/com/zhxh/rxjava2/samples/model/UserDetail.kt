package com.zhxh.rxjava2.samples.model

/**
 * Created by zhxh on 2018/1/18
 */

class UserDetail(var id: Long) {
    var firstname: String? = null
    var lastname: String? = null

    override fun toString(): String {
        return "UserDetail{" +
            "id=" + id +
            ", firstname='" + firstname + '\''.toString() +
            ", lastname='" + lastname + '\''.toString() +
            '}'.toString()
    }
}
