package com.zhxh.rxjava2.samples.rxbus

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by zhxh on 2018/8/24
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Subscribe(val code: Int = -1, val threadMode: ThreadMode = ThreadMode.MAIN_THREAD, val sticky: Boolean = false)
