package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject

/**
 * Created by zhxh on 2018/1/18
 */

class ReplaySubjectExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView


    private val firstObserver: Observer<Int>
        get() = object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                textView.append(" First onNext : value : " + value!!)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " First onNext value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" First onError : " + e.message)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " First onError : " + e.message)
            }

            override fun onComplete() {
                textView.append(" First onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " First onComplete")
            }
        }

    private val secondObserver: Observer<Int>
        get() = object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed)
                Log.d(TAG, " Second onSubscribe : " + d.isDisposed)
                textView.append(AppConstant.LINE_SEPARATOR)
            }

            override fun onNext(value: Int) {
                textView.append(" Second onNext : value : " + value!!)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " Second onNext value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" Second onError : " + e.message)
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " Second onError : " + e.message)
            }

            override fun onComplete() {
                textView.append(" Second onComplete")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " Second onComplete")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener { doSomeWork() }
    }

    /* ReplaySubject emits to any observer all of the items that were emitted
     * by the source Observable, regardless of when the observer subscribes.
     */
    private fun doSomeWork() {

        val source = ReplaySubject.create<Int>()

        source.subscribe(firstObserver) // it will get 1, 2, 3, 4

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)
        source.onNext(4)
        source.onComplete()

        /*
         * it will emit 1, 2, 3, 4 for second observer also as we have used replay
         */
        source.subscribe(secondObserver)

    }

    companion object {

        private val TAG = ReplaySubjectExampleActivity::class.java.simpleName
    }


}