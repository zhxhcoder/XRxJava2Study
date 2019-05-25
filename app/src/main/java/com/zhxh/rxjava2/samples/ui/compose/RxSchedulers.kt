package com.zhxh.rxjava2.samples.ui.compose

import org.reactivestreams.Publisher

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by werockstar on 5/19/2017.
 */

class RxSchedulers {

    fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableCompute(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> applyObservableMainThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable -> observable.observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> applyFlowableMainThread(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> applyFlowableAsysnc(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
    }

    fun <T> applyFlowableCompute(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable -> flowable.observeOn(AndroidSchedulers.mainThread()) }
    }

}
