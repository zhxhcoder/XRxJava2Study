package com.zhxh.rxjava2.samples.ui.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by zhxh on 2018/1/23
 */
class RxBus {

    private val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}
