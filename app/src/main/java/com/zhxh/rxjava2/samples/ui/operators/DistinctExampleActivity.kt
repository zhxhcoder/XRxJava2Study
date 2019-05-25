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
 * Created by zhxh on 2018/1/23
 */
class DistinctExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<Int>
        get() = Observable.just(1, 2, 1, 1, 2, 3, 4, 6, 4)


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
                Log.d(TAG, " onError : " + e.message)
            }

            override fun onComplete() {
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
     * distinct() suppresses duplicate items emitted by the source Observable.
     * 删除重复的
     * 1,2,3,4,6
     */
    private fun doSomeWork() {

        observable
            .distinct()
            .subscribe(observer)
    }

    companion object {

        private val TAG = DistinctExampleActivity::class.java.simpleName
    }
}