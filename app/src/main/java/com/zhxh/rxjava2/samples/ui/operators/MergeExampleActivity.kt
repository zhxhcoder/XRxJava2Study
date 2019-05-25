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
import io.reactivex.disposables.Disposable

/**
 * Created by zhxh on 2018/1/21
 */

class MergeExampleActivity : AppCompatActivity() {
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
     * Using merge operator to combine Observable : merge does not maintain
     * the order of Observable.
     * It will emit all the 7 values may not be in order
     * Ex - "A1", "B1", "A2", "A3", "A4", "B2", "B3" - may be anything
     */
    private fun doSomeWork() {
        val aStrings = arrayOf("A1", "A2", "A3", "A4")
        val bStrings = arrayOf("B1", "B2", "B3")

        val aObservable = Observable.fromArray(*aStrings)
        val bObservable = Observable.fromArray(*bStrings)

        Observable.merge(aObservable, bObservable)
            .subscribe(observer)
    }

    companion object {

        private val TAG = MergeExampleActivity::class.java.simpleName
    }


}