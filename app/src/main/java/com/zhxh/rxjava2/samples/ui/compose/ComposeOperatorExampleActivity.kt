package com.zhxh.rxjava2.samples.ui.compose

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.zhxh.rxjava2.samples.R

import io.reactivex.Flowable
import io.reactivex.Observable

class ComposeOperatorExampleActivity : AppCompatActivity() {

    private val schedulers = RxSchedulers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_operator_example)

        /*
            Compose for reusable code.
         */
        Observable.just(1, 2, 3, 4, 5)
            .compose(schedulers.applyObservableAsync())
            .subscribe(/* */)

        Flowable.just(1, 2, 3, 4, 5)
            .compose(schedulers.applyFlowableAsysnc())
            .subscribe(/* */)

    }
}
