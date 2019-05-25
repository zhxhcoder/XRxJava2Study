package com.zhxh.rxjava2.samples

import android.app.Application

import com.zhxh.rxjava2.samples.model.Events
import com.zhxh.rxjava2.samples.ui.rxbus.RxBus

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by threshold on 2017/1/12.
 */

class MyApplication : Application() {
    private var bus: RxBus? = null

    override fun onCreate() {
        super.onCreate()
        bus = RxBus()
    }

    fun bus(): RxBus? {
        return bus
    }

    fun sendAutoEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe { bus!!.send(Events.AutoEvent()) }
    }

    companion object {

        val TAG = "MyApplication"
    }

}
