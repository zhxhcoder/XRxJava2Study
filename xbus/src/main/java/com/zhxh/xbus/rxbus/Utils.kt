package com.zhxh.xbus.rxbus

import android.util.Log

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class Utils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 判断对象是否相等
         *
         * @param o1 对象1
         * @param o2 对象2
         * @return `true`: 相等<br></br>`false`: 不相等
         */
        fun equals(o1: Any?, o2: Any): Boolean {
            return o1 === o2 || o1 != null && o1 == o2
        }

        /**
         * Require the objects are not null.
         *
         * @param objects The object.
         * @throws NullPointerException if any object is null in objects
         */
        fun requireNonNull(vararg objects: Any) {
            if (objects == null) throw NullPointerException()
            for (`object` in objects) {
                if (`object` == null) throw NullPointerException()
            }
        }

        fun <T> getTypeClassFromCallback(callback: RxBus.Callback<T>?): Class<T>? {
            if (callback == null) return null
            val mySuperClass = callback.javaClass.getGenericInterfaces()[0]
            var type = (mySuperClass as ParameterizedType).actualTypeArguments[0]
            while (type is ParameterizedType) {
                type = type.rawType
            }
            var className = type.toString()
            if (className.startsWith("class ")) {
                className = className.substring(6)
            } else if (className.startsWith("interface ")) {
                className = className.substring(10)
            }
            try {

                return Class.forName(className) as Class<T>
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            return null
        }

        fun getClassFromObject(obj: Any?): Class<*>? {
            if (obj == null) return null
            val objClass = obj.javaClass
            val genericInterfaces = objClass.getGenericInterfaces()
            if (genericInterfaces.size == 1) {
                var type = genericInterfaces[0]
                while (type is ParameterizedType) {
                    type = (type as ParameterizedType).rawType
                }
                var className = type.toString()
                if (className.startsWith("class ")) {
                    className = className.substring(6)
                } else if (className.startsWith("interface ")) {
                    className = className.substring(10)
                }
                try {
                    return Class.forName(className)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

            }
            return objClass
        }

        fun logW(msg: String) {
            Log.w("RxBus", msg)
        }

        fun logE(msg: String) {
            Log.e("RxBus", msg)
        }
    }
}
