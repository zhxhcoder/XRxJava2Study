package com.zhxh.rxjava2.samples.ui.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhxh.rxjava2.samples.R;
import com.zhxh.rxjava2.samples.model.User;
import com.zhxh.rxjava2.samples.rxbus.RxBus;
import com.zhxh.rxjava2.samples.rxbus.Subscribe;
import com.zhxh.rxjava2.samples.rxbus.ThreadMode;
import com.zhxh.rxjava2.samples.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhxh on 2018/1/18
 */
public class SimpleExampleActivity extends AppCompatActivity {

    private static final String TAG = "rxbus";
    Button btn;
    TextView textView;
    TextView tvBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);
        tvBus = findViewById(R.id.tvBus);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*注册*/
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*注销*/
        RxBus.getDefault().unRegister(this);
    }

    private void doSomeWork() {
        getObservable()
                //子线程运行
                .subscribeOn(Schedulers.io())
                //主线程收到通知
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("嘟嘟", "团团");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String value) {
                textView.append(" onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onComplete");
            }
        };
    }

    @Subscribe(code = AppConstant.BUS_INTERVAL, threadMode = ThreadMode.MAIN_THREAD)
    public void onBusInterval(User data) {
        tvBus.append("\nonBusInterval " + data.id);
    }
}