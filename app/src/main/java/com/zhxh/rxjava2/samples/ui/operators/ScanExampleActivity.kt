package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ScanExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<Int>
        get() = Observable.just(1, 2, 3, 4, 5)

    private val observer: Observer<Int>
        get() = object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                textView.append(" onNext : value : " + value!!)
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
        btn = findViewById<View>(R.id.btn) as Button
        textView = findViewById<View>(R.id.textView) as TextView

        btn.setOnClickListener { doSomeWork() }
    }

    /* Using scan operator, it sends also the previous result */
    private fun doSomeWork() {
        observable
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .scan(object : BiFunction<Int, Int, Int> {
                @Throws(Exception::class)
                override fun apply(int1: Int, int2: Int): Int {
                    return int1!! + int2!!
                }
            })
            .subscribe(observer)
    }

    companion object {

        private val TAG = ScanExampleActivity::class.java.simpleName
    }


}