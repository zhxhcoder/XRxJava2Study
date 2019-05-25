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
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * Created by zhxh on 2018/1/18
 */
class LastOperatorExampleActivity : AppCompatActivity() {
    internal lateinit var btn: Button
    internal lateinit var textView: TextView

    private val observable: Observable<String>
        get() = Observable.just("A1", "A2", "A3", "A4", "A5", "A6")

    private val observer: SingleObserver<String>
        get() = object : SingleObserver<String> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: String) {
                textView.append(" onNext : value : $value")
                textView.append(AppConstant.LINE_SEPARATOR)
                Log.d(TAG, " onNext value : $value")
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
    * last() emits only the last item emitted by the Observable.
    */
    private fun doSomeWork() {
        observable.last("A1") // the default item ("A1") to emit if the source ObservableSource is empty
            .subscribe(observer)
    }

    companion object {

        private val TAG = DistinctExampleActivity::class.java.simpleName
    }
}
