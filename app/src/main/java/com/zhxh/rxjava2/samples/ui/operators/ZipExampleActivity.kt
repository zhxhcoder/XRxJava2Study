package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.User
import com.zhxh.rxjava2.samples.utils.AppConstant
import com.zhxh.rxjava2.samples.utils.Utils

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class ZipExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val appleFansObservable: Observable<List<User>>
        get() = Observable.create { e ->
            if (!e.isDisposed) {
                e.onNext(Utils.userListWhoLovesApple)
                e.onComplete()
            }
        }

    private val bananaFansObservable: Observable<List<User>>
        get() = Observable.create { e ->
            if (!e.isDisposed) {
                e.onNext(Utils.userListWhoLovesBanana)
                e.onComplete()
            }
        }

    private val observer: Observer<List<User>>
        get() = object : Observer<List<User>> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(userList: List<User>) {
                textView.append(" onNext")
                textView.append(AppConstant.LINE_SEPARATOR)
                for (user in userList) {
                    textView.append(" 博爱者名字 : " + user.firstname!!)
                    textView.append(AppConstant.LINE_SEPARATOR)
                }
                Log.d(TAG, " onNext : " + userList.size)
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

        btn.setOnClickListener { view -> doSomeWork() }
    }

    /*
     * 该例子中
     * 我们得到苹果爱好者与香蕉爱好者并找出博爱者
     */
    private fun doSomeWork() {
        Observable.zip(appleFansObservable, bananaFansObservable,
            BiFunction<List<User>, List<User>, List<User>> { appleFans, bananaFans -> Utils.filterUserWhoLovesBoth(appleFans, bananaFans) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    companion object {

        private val TAG = ZipExampleActivity::class.java.simpleName
    }


}