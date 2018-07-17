package com.zhxh.rxjava2.samples.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhxh.rxjava2.samples.MyApplication;
import com.zhxh.rxjava2.samples.R;
import com.zhxh.rxjava2.samples.ui.compose.ComposeOperatorExampleActivity;
import com.zhxh.rxjava2.samples.ui.networking.NetworkingActivity;
import com.zhxh.rxjava2.samples.ui.pagination.PaginationActivity;
import com.zhxh.rxjava2.samples.ui.rxbus.RxBusActivity;
import com.zhxh.rxjava2.samples.ui.search.SearchActivity;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }

    public void startOperatorsActivity(View view) {
        startActivity(new Intent(SelectionActivity.this, OperatorsActivity.class));
    }

    public void startNetworkingActivity(View view) {
        startActivity(new Intent(SelectionActivity.this, NetworkingActivity.class));
    }

    public void startRxBusActivity(View view) {
        ((MyApplication) getApplication()).sendAutoEvent();
        startActivity(new Intent(SelectionActivity.this, RxBusActivity.class));
    }

    public void startPaginationActivity(View view) {
        startActivity(new Intent(SelectionActivity.this, PaginationActivity.class));
    }

    public void startComposeOperator(View view) {
        startActivity(new Intent(SelectionActivity.this, ComposeOperatorExampleActivity.class));
    }

    public void startSearchActivity(View view) {
        startActivity(new Intent(SelectionActivity.this, SearchActivity.class));
    }

}
