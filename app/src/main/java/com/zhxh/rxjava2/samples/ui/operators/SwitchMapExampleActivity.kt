package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import java.util.Random
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class SwitchMapExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<Int>
        get() = Observable.just(1, 2, 3, 4, 5)

    private val observer: Observer<String>
        get() = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {
                textView.append(" onNext : value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext value : $value")
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

    /* whenever a new item is emitted by the source Observable, it will unsubscribe to and stop
     * mirroring the Observable that was generated from the previously-emitted item,
     * and begin only mirroring the current one.
     *
     * Result: 5x
     */
    private fun doSomeWork() {
        observable
            .switchMap(object : Function<Int, ObservableSource<String>> {
                override fun apply(t: Int): ObservableSource<String> {
                    val delay = Random().nextInt(2)

                    return Observable.just(t.toString() + "x")
                        .delay(delay.toLong(), TimeUnit.SECONDS, Schedulers.io())
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    companion object {

        private val TAG = SwitchMapExampleActivity::class.java.simpleName
    }
}
