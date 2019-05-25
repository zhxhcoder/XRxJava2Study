package com.zhxh.xbus.rxbus

import org.reactivestreams.Subscription

import java.util.concurrent.atomic.AtomicReference

import io.reactivex.FlowableSubscriber
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.subscriptions.SubscriptionHelper
import io.reactivex.observers.LambdaConsumerIntrospection
import io.reactivex.plugins.RxJavaPlugins

internal class MyLambdaSubscriber<T>(val onNext: Consumer<in T>, val onError: Consumer<in Throwable>,
                                     val onComplete: Action,
                                     val onSubscribe: Consumer<in Subscription>) : AtomicReference<Subscription>(), FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection {

    override fun onSubscribe(s: Subscription) {
        if (SubscriptionHelper.setOnce(this, s)) {
            try {
                onSubscribe.accept(this)
            } catch (ex: Throwable) {
                Exceptions.throwIfFatal(ex)
                s.cancel()
                onError(ex)
            }

        }
    }

    override fun onNext(t: T) {
        if (!isDisposed) {
            try {
                onNext.accept(t)
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                //                get().cancel();
                onError(e)
            }

        }
    }

    override fun onError(t: Throwable) {
        if (get() !== SubscriptionHelper.CANCELLED) {
            //            lazySet(SubscriptionHelper.CANCELLED);
            try {
                onError.accept(t)
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(CompositeException(t, e))
            }

        } else {
            RxJavaPlugins.onError(t)
        }
    }

    override fun onComplete() {
        if (get() !== SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED)
            try {
                onComplete.run()
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(e)
            }

        }
    }

    override fun dispose() {
        cancel()
    }

    override fun isDisposed(): Boolean {
        return get() === SubscriptionHelper.CANCELLED
    }

    override fun request(n: Long) {
        get().request(n)
    }

    override fun cancel() {
        SubscriptionHelper.cancel(this)
    }

    override fun hasCustomOnError(): Boolean {
        return onError !== Functions.ON_ERROR_MISSING
    }

    companion object {

        private val serialVersionUID = -7251123623727029452L
    }
}
