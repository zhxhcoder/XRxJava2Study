package com.zhxh.rxjava2.samples.ui.pagination

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar

import com.zhxh.rxjava2.samples.R

import org.reactivestreams.Publisher

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.processors.PublishProcessor

/**
 * Created by zhxh on 15/03/17.
 */

class PaginationActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val paginator = PublishProcessor.create<Int>()
    private var paginationAdapter: PaginationAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var loading = false
    private var pageNumber = 1
    private val VISIBLE_THRESHOLD = 1
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var layoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        layoutManager = LinearLayoutManager(this)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager
        paginationAdapter = PaginationAdapter()
        recyclerView!!.adapter = paginationAdapter
        setUpLoadMoreListener()
        subscribeForData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /**
     * setting listener to get callback for load more
     */
    private fun setUpLoadMoreListener() {
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView,
                                    dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = layoutManager!!.itemCount
                lastVisibleItem = layoutManager!!
                    .findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    pageNumber++
                    paginator.onNext(pageNumber)
                    loading = true
                }
            }
        })
    }

    /**
     * subscribing for data
     */
    private fun subscribeForData() {

        val disposable = paginator
            .onBackpressureDrop()
            .concatMap(object : Function<Int, Publisher<List<String>>> {
                @Throws(Exception::class)
                override fun apply(@NonNull page: Int): Publisher<List<String>> {
                    loading = true
                    progressBar!!.visibility = View.VISIBLE
                    return dataFromNetwork(page!!)
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                paginationAdapter!!.addItems(items)
                paginationAdapter!!.notifyDataSetChanged()
                loading = false
                progressBar!!.visibility = View.INVISIBLE
            }

        compositeDisposable.add(disposable)

        paginator.onNext(pageNumber)

    }

    /**
     * Simulation of network data
     */
    private fun dataFromNetwork(page: Int): Flowable<List<String>> {
        return Flowable.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map(object : Function<Boolean, List<String>> {
                @Throws(Exception::class)
                override fun apply(@NonNull value: Boolean): List<String> {
                    val items = ArrayList<String>()
                    for (i in 1..10) {
                        items.add("Item " + (page * 10 + i))
                    }
                    return items
                }
            })
    }

    companion object {

        val TAG = PaginationActivity::class.java.simpleName
    }
}
