package com.zhxh.rxjava2.samples.ui.rxbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.zhxh.rxjava2.samples.MyApplication
import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.Events

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/23
 */
class RxBusActivity : AppCompatActivity() {
    internal lateinit var textView: TextView
    internal lateinit var button: Button
    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // do not send event after activity has been destroyed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxbus)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)

        disposables.add((application as MyApplication)
            .bus()!!
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { `object` ->
                if (`object` is Events.AutoEvent) {
                    textView.text = "Auto Event Received"
                } else if (`object` is Events.TapEvent) {
                    textView.text = "Tap Event Received"
                }
            })

        button.setOnClickListener {
            (application as MyApplication)
                .bus()!!
                .send(Events.TapEvent())
        }
    }

    companion object {

        val TAG = RxBusActivity::class.java.simpleName
    }


}
