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
import io.reactivex.subjects.PublishSubject

/**
 * Created by zhxh on 17/12/16.
 */

class PublishSubjectExampleActivity : AppCompatActivity() {
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
        btn = findViewById<View>(R.id.btn) as Button
        textView = findViewById<View>(R.id.textView) as TextView

        btn.setOnClickListener { doSomeWork() }
    }

    /* PublishSubject emits to an observer only those items that are emitted
     * by the source Observable, subsequent to the time of the subscription.
     */
    private fun doSomeWork() {

        val source = PublishSubject.create<Int>()

        source.subscribe(firstObserver) // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        source.subscribe(secondObserver)

        source.onNext(4)
        source.onComplete()

    }

    companion object {

        private val TAG = PublishSubjectExampleActivity::class.java.simpleName
    }


}