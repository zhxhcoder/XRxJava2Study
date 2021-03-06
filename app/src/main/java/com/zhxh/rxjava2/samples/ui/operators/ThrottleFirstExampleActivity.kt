package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by zhxh on 2018/1/18
 */

class ThrottleFirstExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private// send events with simulated time wait
    // deliver
    // skip
    // deliver
    // skip
    // skip
    // skip
    // deliver
    val observable: Observable<Int>
        get() = Observable.create { emitter ->
            Thread.sleep(0)
            emitter.onNext(1)
            emitter.onNext(2)
            Thread.sleep(505)
            emitter.onNext(3)
            Thread.sleep(99)
            emitter.onNext(4)
            Thread.sleep(100)
            emitter.onNext(5)
            emitter.onNext(6)
            Thread.sleep(305)
            emitter.onNext(7)
            Thread.sleep(510)
            emitter.onComplete()
        }

    private val observer: Observer<Int>
        get() = object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                textView.append(" onNext : ")
                textView.append(AppConstant.LINE_SEPARATOR)
                textView.append(" value : " + value!!)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext ")
                Log.d(TAG, " value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener { doSomeWork() }
    }

    /*
    * Using throttleFirst() -> if the source Observable has emitted no items since
    * the last time it was sampled, the Observable that results from this operator
    * will emit no item for that sampling period.
    */
    private fun doSomeWork() {
        observable
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    companion object {

        private val TAG = ThrottleFirstExampleActivity::class.java.simpleName
    }

}