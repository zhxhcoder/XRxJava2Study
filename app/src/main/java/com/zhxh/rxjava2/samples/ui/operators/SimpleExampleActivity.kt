package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.User
import com.zhxh.rxjava2.samples.rxbus.RxBus
import com.zhxh.rxjava2.samples.rxbus.Subscribe
import com.zhxh.rxjava2.samples.rxbus.ThreadMode
import com.zhxh.rxjava2.samples.utils.AppConstant

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class SimpleExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView
    internal lateinit var tvBus: TextView
    internal lateinit var tvKitBus: TextView
    protected var mDisposables: CompositeDisposable? = CompositeDisposable()

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
        tvBus = findViewById(R.id.tvBus)
        tvKitBus = findViewById(R.id.tvKitBus)

        btn.setOnClickListener { doSomeWork2() }


        // 注册带 tag 为 "my tag" 的 String 类型事件
        com.zhxh.xlibkit.rxbus.RxBus.getDefault().subscribeSticky(this, "myTag", com.zhxh.xlibkit.rxbus.RxBus.Callback<User> { s -> tvKitBus.append("\neventTag " + s.id) })
    }

    override fun onStart() {
        super.onStart()
        /*注册*/
        RxBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        /*注销*/
        RxBus.getDefault().unRegister(this)

        if (mDisposables != null) {
            mDisposables!!.clear()
        }
        com.zhxh.xlibkit.rxbus.RxBus.getDefault().unregister(this)

    }

    private fun doSomeWork2() {
        Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.d("xxxxx", "subscribe " + +System.currentTimeMillis())

            emitter.onNext("嘟嘟")
            emitter.onNext("团团")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    Log.d("xxxxx", "onSubscribe " + System.currentTimeMillis())
                }

                override fun onNext(s: String) {
                    Log.d("xxxxx", "onNext " + +System.currentTimeMillis())
                }

                override fun onError(e: Throwable) {
                    Log.d("xxxxx", "onError " + +System.currentTimeMillis())
                }

                override fun onComplete() {
                    Log.d("xxxxx", "onComplete " + +System.currentTimeMillis())
                }
            })
    }

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD, sticky = true)
    fun onBusInterval(data: User) {
        tvBus.append("\nonBusInterval " + data.id)
    }

    companion object {
        private val TAG = "rxbus"
    }

}