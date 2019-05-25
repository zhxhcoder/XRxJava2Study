package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.utils.AppConstant

import java.util.concurrent.Callable

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class DisposableExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener { doSomeWork() }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // 不要在activity销毁后发送事件
    }

    /*
     * 理解disposables小例子
     * disposables须在onDestroy中清除
     */
    internal fun doSomeWork() {
        disposables.add(sampleObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<String>() {
                override fun onNext(value: String) {
                    textView.append(" onNext : value : $value")
                    textView.append(AppConstant.LINE_SEPARATOR)
                    Log.d(TAG, " onNext value : $value")
                }

                override fun onComplete() {
                    textView.append(" onComplete")
                    textView.append(AppConstant.LINE_SEPARATOR)
                    Log.d(TAG, " onComplete")
                }

                override fun onError(e: Throwable) {
                    textView.append(" onError : " + e.message)
                    textView.append(AppConstant.LINE_SEPARATOR)
                    Log.d(TAG, " onError : " + e.message)
                }

            }))
    }

    companion object {

        private val TAG = DisposableExampleActivity::class.java.simpleName

        internal fun sampleObservable(): Observable<String> {
            return Observable.defer {
                // 模拟耗时工作
                SystemClock.sleep(2500)
                Observable.just("1", "2", "3", "4", "5")
            }
        }
    }
}
