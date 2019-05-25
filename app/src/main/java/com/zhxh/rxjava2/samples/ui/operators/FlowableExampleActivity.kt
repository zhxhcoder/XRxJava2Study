package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import io.reactivex.Flowable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

/**
 * Created by zhxh on 2018/1/18
 */
class FlowableExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observer: SingleObserver<String>
        get() = object : SingleObserver<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: String) {
                textView.append(" onSuccess : value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onSuccess : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
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
     * Flowable操作符
     */
    private fun doSomeWork() {

        val observable = Flowable.just("a", "b", "c", "d")

        observable.reduce("X", { t1, t2 -> t1 + t2 }).subscribe(observer)

    }

    companion object {

        private val TAG = FlowableExampleActivity::class.java.simpleName
    }
}