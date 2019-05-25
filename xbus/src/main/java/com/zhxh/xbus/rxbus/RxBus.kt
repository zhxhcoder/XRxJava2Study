package com.zhxh.xbus.rxbus

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor


class RxBus private constructor() {

    private val mBus: FlowableProcessor<Any>

    private val mOnError = Consumer<Throwable> { throwable -> Utils.logE(throwable.toString()) }

    init {
        mBus = PublishProcessor.create<Any>().toSerialized()
    }

    fun post(event: Any) {
        post(event, "", false)
    }

    fun post(event: Any, tag: String) {
        post(event, tag, false)
    }

    fun postSticky(event: Any) {
        post(event, "", true)
    }

    fun postSticky(event: Any, tag: String) {
        post(event, tag, true)
    }

    private fun post(event: Any,
                     tag: String,
                     isSticky: Boolean) {
        Utils.requireNonNull(event, tag)

        val msgEvent = TagMessage(event, tag)
        if (isSticky) {
            CacheUtils.instance.addStickyEvent(msgEvent)
        }
        mBus.onNext(msgEvent)
    }

    fun <T> subscribe(subscriber: Any,
                      callback: Callback<T>) {
        subscribe(subscriber, "", false, null, callback)
    }

    fun <T> subscribe(subscriber: Any,
                      tag: String,
                      callback: Callback<T>) {
        subscribe(subscriber, tag, false, null, callback)
    }

    fun <T> subscribe(subscriber: Any,
                      scheduler: Scheduler,
                      callback: Callback<T>) {
        subscribe(subscriber, "", false, scheduler, callback)
    }

    fun <T> subscribe(subscriber: Any,
                      tag: String,
                      scheduler: Scheduler,
                      callback: Callback<T>) {
        subscribe(subscriber, tag, false, scheduler, callback)
    }

    fun <T> subscribeSticky(subscriber: Any,
                            callback: Callback<T>) {
        subscribe(subscriber, "", true, null, callback)
    }

    fun <T> subscribeSticky(subscriber: Any,
                            tag: String,
                            callback: Callback<T>) {
        subscribe(subscriber, tag, true, null, callback)
    }

    fun <T> subscribeSticky(subscriber: Any,
                            scheduler: Scheduler,
                            callback: Callback<T>) {
        subscribe(subscriber, "", true, scheduler, callback)
    }

    fun <T> subscribeSticky(subscriber: Any,
                            tag: String,
                            scheduler: Scheduler,
                            callback: Callback<T>) {
        subscribe(subscriber, tag, true, scheduler, callback)
    }

    private fun <T> subscribe(subscriber: Any,
                              tag: String,
                              isSticky: Boolean,
                              scheduler: Scheduler?,
                              callback: Callback<T>) {
        Utils.requireNonNull(subscriber, tag, callback)

        val typeClass = Utils.getTypeClassFromCallback(callback)

        val onNext = Consumer<T> { t -> callback.onEvent(t) }

        if (isSticky) {
            val stickyEvent = CacheUtils.instance.findStickyEvent(typeClass!!, tag)
            if (stickyEvent != null) {
                var stickyFlowable = Flowable.create(FlowableOnSubscribe<T> { emitter -> emitter.onNext(typeClass!!.cast(stickyEvent!!.mEvent)) }, BackpressureStrategy.LATEST)
                if (scheduler != null) {
                    stickyFlowable = stickyFlowable.observeOn(scheduler)
                }
                val stickyDisposable = FlowableUtils.subscribe(stickyFlowable, onNext, mOnError)
                CacheUtils.instance.addDisposable(subscriber, stickyDisposable)
            } else {
                Utils.logW("sticky event is empty.")
            }
        }
        val disposable = FlowableUtils.subscribe(
            toFlowable(typeClass, tag, scheduler), onNext, mOnError
        )
        CacheUtils.instance.addDisposable(subscriber, disposable)
    }

    private fun <T> toFlowable(eventType: Class<T>?,
                               tag: String,
                               scheduler: Scheduler?): Flowable<T> {
        val flowable = mBus.ofType<TagMessage>(TagMessage::class.java!!)
            .filter { tagMessage -> tagMessage.isSameType(eventType!!, tag) }
            .map { tagMessage -> tagMessage.mEvent }
            .cast(eventType!!)
        return if (scheduler != null) {
            flowable.observeOn(scheduler)
        } else flowable
    }

    fun unregister(subscriber: Any) {
        CacheUtils.instance.removeDisposables(subscriber)
    }

    private object Holder {
        internal val BUS = RxBus()
    }

    interface Callback<T> {
        fun onEvent(t: T)
    }

    companion object {
        val default: RxBus
            get() = Holder.BUS
    }
}