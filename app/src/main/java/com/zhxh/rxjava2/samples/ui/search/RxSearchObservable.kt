package com.zhxh.rxjava2.samples.ui.search

import android.widget.SearchView

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by zhxh on 2018/1/23
 */
object RxSearchObservable {

    fun fromView(searchView: SearchView): Observable<String> {

        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })

        return subject
    }
}// no instance
