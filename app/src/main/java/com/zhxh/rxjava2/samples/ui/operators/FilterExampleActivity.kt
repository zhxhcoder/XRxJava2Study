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
import io.reactivex.functions.Predicate

/**
 * Created by zhxh on 2018/1/20
 */
class FilterExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView


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
     * filter 操作符过滤偶数
     *
     */
    private fun doSomeWork() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .filter(object : Predicate<Int> {
                override fun test(integer: Int): Boolean {
                    return integer!! % 2 == 0
                }
            })
            .subscribe(observer)
    }

    companion object {

        private val TAG = FilterExampleActivity::class.java.simpleName
    }


}