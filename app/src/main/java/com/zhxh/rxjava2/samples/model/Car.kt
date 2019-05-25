package com.zhxh.rxjava2.samples.model

import java.util.concurrent.Callable

import io.reactivex.Observable
import io.reactivex.ObservableSource

/**
 * Created by zhxh on 30/08/16.
 */
class Car {

    private var brand: String? = null

    fun setBrand(brand: String) {
        this.brand = brand
    }

    fun brandDeferObservable(): Observable<String> {
        return Observable.defer { Observable.just(brand!!) }
    }

}
