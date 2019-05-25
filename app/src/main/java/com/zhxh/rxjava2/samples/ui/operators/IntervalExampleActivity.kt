package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.User
import com.zhxh.rxjava2.samples.model.UserDetail
import com.zhxh.rxjava2.samples.rxbus.RxBus
import com.zhxh.rxjava2.samples.utils.AppConstant

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class IntervalExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView
    private val disposables = CompositeDisposable()

    private val observable: Observable<out Long>
        get() = Observable.interval(0, 2, TimeUnit.SECONDS)

    private val observer: DisposableObserver<Long>
        get() = object : DisposableObserver<Long>() {

            override fun onNext(value: Long) {

                RxBus.getDefault().post(User(value!!))

                com.zhxh.xlibkit.rxbus.RxBus.getDefault().postSticky("myTag", User(value))

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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    /*
     * 通过该例子利用interval操作符每隔2s运行某个任务
     * 注意，首次会立即运行
     */
    private fun doSomeWork() {
        disposables.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer))
    }

    companion object {

        private val TAG = IntervalExampleActivity::class.java.simpleName
    }


}