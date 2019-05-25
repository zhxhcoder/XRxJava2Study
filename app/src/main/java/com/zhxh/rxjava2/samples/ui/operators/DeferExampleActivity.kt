package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.Car
import com.zhxh.rxjava2.samples.model.UserDetail
import com.zhxh.rxjava2.samples.rxbus.RxBus
import com.zhxh.rxjava2.samples.utils.AppConstant

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by zhxh on 2018/1/25
 */

class DeferExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observer: Observer<String>
        get() = object : Observer<String> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {
                textView.append(" onNext : value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext : value : $value")
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
     * Defer used for Deferring Observable code until subscription in RxJava
     */
    private fun doSomeWork() {

        val car = Car()

        val brandDeferObservable = car.brandDeferObservable()

        car.setBrand("BMW")  // Even if we are setting the brand after creating Observable
        // we will get the brand as BMW.
        // If we had not used defer, we would have got null as the brand.

        brandDeferObservable
            .subscribe(observer)
    }

    companion object {

        private val TAG = DeferExampleActivity::class.java.simpleName
    }


}