package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Created by zhxh on 2018/1/18
 */
class ReduceExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<Int>
        get() = Observable.just(1, 3, 6, 9)

    private val observer: MaybeObserver<Int>
        get() = object : MaybeObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: Int) {
                textView.append(" onSuccess : value : " + value!!)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onSuccess : value : $value")
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
     * reduce操作符
     * 所以数字相加
     */
    private fun doSomeWork() {
        observable
            .reduce(object : BiFunction<Int, Int, Int> {
                override fun apply(t1: Int, t2: Int): Int {
                    return t1!! + t2!!
                }
            })
            .subscribe(observer)
    }

    companion object {

        private val TAG = ReduceExampleActivity::class.java.simpleName
    }


}