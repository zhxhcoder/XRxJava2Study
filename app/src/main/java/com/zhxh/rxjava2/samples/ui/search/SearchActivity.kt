package com.zhxh.rxjava2.samples.ui.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SearchView
import android.widget.TextView

import com.zhxh.rxjava2.samples.R

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/23
 */

class SearchActivity : AppCompatActivity() {
    private var searchView: SearchView? = null
    private var textViewResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchView = findViewById(R.id.searchView)
        textViewResult = findViewById(R.id.textViewResult)

        setUpSearchObservable()
    }

    private fun setUpSearchObservable() {
        RxSearchObservable.fromView(searchView!!)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { text ->
                if (text.isEmpty()) {
                    textViewResult!!.text = ""
                    false
                } else {
                    true
                }
            }
            .distinctUntilChanged()
            .switchMap { query -> dataFromNetwork(query) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> textViewResult!!.text = result }
    }

    /**
     * Simulation of network data
     */
    private fun dataFromNetwork(query: String): Observable<String> {
        return Observable.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map(object : Function<Boolean, String> {
                override fun apply(@NonNull value: Boolean): String {
                    return query
                }
            })
    }

    companion object {

        val TAG = SearchActivity::class.java.simpleName
    }

}
