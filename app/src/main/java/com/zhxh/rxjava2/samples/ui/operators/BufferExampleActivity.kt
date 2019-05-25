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
 * Created by zhxh on 2018/1/19
 * http://reactivex.io/documentation/operators/buffer.html
 */
class BufferExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<String>
        get() = Observable.just("1", "2", "3", "4", "5")

    private val observer: Observer<List<String>>
        get() = object : Observer<List<String>> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(stringList: List<String>) {
                textView.append(" onNext size : " + stringList.size)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext : size :" + stringList.size)
                for (value in stringList) {
                    textView.append(" value : $value")
                    textView.append(AppConstant.LINE_SEPARATOR)
                    Log.d(TAG, " : value :$value")
                }

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
     * buffer操作符 传的值以列表list存储
     *
     */
    private fun doSomeWork() {

        val buffered = observable.buffer(3, 2)

        // 3 表示取最大的三个数值，这些数值从获取最开始index开始并创建
        // 1 表示每次一步
        // 得到如下列表
        // 1 - 1, 2, 3
        // 2 - 2, 3, 4
        // 3 - 3, 4, 5
        // 4 - 4, 5
        // 5 - 5

        // 3 表示取最大的三个数值，这些数值从获取最开始index开始并创建
        // 2 表示每次一步
        // 得到如下列表
        // 1 - 1, 2, 3
        // 3 - 3, 4, 5
        // 5 - 5

        buffered.subscribe(observer)
    }

    companion object {

        private val TAG = BufferExampleActivity::class.java.simpleName
    }


}