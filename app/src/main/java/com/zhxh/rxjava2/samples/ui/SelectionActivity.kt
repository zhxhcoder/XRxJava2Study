package com.zhxh.rxjava2.samples.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.zhxh.rxjava2.samples.MyApplication
import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.ui.compose.ComposeOperatorExampleActivity
import com.zhxh.rxjava2.samples.ui.networking.NetworkingActivity
import com.zhxh.rxjava2.samples.ui.pagination.PaginationActivity
import com.zhxh.rxjava2.samples.ui.rxbus.RxBusActivity
import com.zhxh.rxjava2.samples.ui.search.SearchActivity

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
    }

    fun startOperatorsActivity(view: View) {
        startActivity(Intent(this@SelectionActivity, OperatorsActivity::class.java))
    }

    fun startNetworkingActivity(view: View) {
        startActivity(Intent(this@SelectionActivity, NetworkingActivity::class.java))
    }

    fun startRxBusActivity(view: View) {
        (application as MyApplication).sendAutoEvent()
        startActivity(Intent(this@SelectionActivity, RxBusActivity::class.java))
    }

    fun startPaginationActivity(view: View) {
        startActivity(Intent(this@SelectionActivity, PaginationActivity::class.java))
    }

    fun startComposeOperator(view: View) {
        startActivity(Intent(this@SelectionActivity, ComposeOperatorExampleActivity::class.java))
    }

    fun startSearchActivity(view: View) {
        startActivity(Intent(this@SelectionActivity, SearchActivity::class.java))
    }

}
