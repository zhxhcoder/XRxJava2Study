package com.zhxh.rxjava2.samples.ui.operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.ApiUser
import com.zhxh.rxjava2.samples.model.User
import com.zhxh.rxjava2.samples.utils.AppConstant
import com.zhxh.rxjava2.samples.utils.Utils

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */
class MapExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        btn = findViewById(R.id.btn)
        textView = findViewById(R.id.textView)

        btn.setOnClickListener { doSomeWork() }
    }

    /*
     * 从服务器中得到ApiUser对象，而我们需求的是支持本地数据库的
     * User对象，这时我们用Map操作符转换
     */
    private fun doSomeWork() {

        val disposable = arrayOfNulls<Disposable>(1)
        Observable.create(ObservableOnSubscribe<List<ApiUser>> { e ->
            if (!e.isDisposed) {
                e.onNext(Utils.apiUserList)
                e.onComplete()
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { apiUsers -> Utils.convertApiUserListToUserList(apiUsers) }
            .subscribe(object : Observer<List<User>> {

                override fun onSubscribe(d: Disposable) {
                    disposable[0] = d
                    Log.d(TAG, " onSubscribe onSubscribe: " + disposable[0]?.isDisposed())
                }

                override fun onNext(userList: List<User>) {
                    textView.append(" onNext 开始")
                    textView.append(AppConstant.LINE_SEPARATOR)
                    for (user in userList) {
                        textView.append(" 名字 : " + user.firstname!!)
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
                    textView.append(" onComplete整体")
                    textView.append(AppConstant.LINE_SEPARATOR)
                    Log.d(TAG, " onSubscribe onComplete: " + disposable[0]?.isDisposed())

                    disposable[0]?.dispose()

                    Log.d(TAG, " onSubscribe onComplete: " + disposable[0]?.isDisposed())

                }
            })
    }

    companion object {

        private val TAG = MapExampleActivity::class.java.simpleName
    }


}