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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhxh on 2018/1/18
 */
public class SimpleExampleActivity extends AppCompatActivity {

    private static final String TAG = "rxbus";
    Button btn;
    TextView textView;
    TextView tvBus;
    TextView tvKitBus;
    protected CompositeDisposable mDisposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);
        tvBus = findViewById(R.id.tvBus);
        tvKitBus = findViewById(R.id.tvKitBus);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork2();
            }
        });


        // 注册带 tag 为 "my tag" 的 String 类型事件
        com.zhxh.xlibkit.rxbus.RxBus.getDefault().subscribeSticky(this, "myTag", new com.zhxh.xlibkit.rxbus.RxBus.Callback<User>() {
            @Override
            public void onEvent(User s) {

                tvKitBus.append("\neventTag " + s.getId());

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

        if (mDisposables != null) {
            mDisposables.clear();
        }
        com.zhxh.xlibkit.rxbus.RxBus.getDefault().unregister(this);

    }

    private void doSomeWork() {

        Disposable disposable = Observable.create((ObservableOnSubscribe<String>) e -> {

            e.onNext("嘟嘟");
            Thread.sleep(5);
            e.onNext("团团");
            e.onComplete();

        }).timeout(2, TimeUnit.MILLISECONDS)
                //子线程运行
                .subscribeOn(Schedulers.io())
                //主线程收到通知
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, " accept : " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, " throwable : " + throwable.getMessage());
                    }
                });

        mDisposables.add(disposable);
    }

    private void doSomeWork2() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("xxxxx", "subscribe " + +System.currentTimeMillis());

                emitter.onNext("嘟嘟");
                emitter.onNext("团团");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("xxxxx", "onSubscribe " + System.currentTimeMillis());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("xxxxx", "onNext " + +System.currentTimeMillis());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("xxxxx", "onError " + +System.currentTimeMillis());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("xxxxx", "onComplete " + +System.currentTimeMillis());
                    }
                });
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

    @Subscribe(threadMode = ThreadMode.MAIN_THREAD, sticky = true)
    public void onBusInterval(User data) {
        tvBus.append("\nonBusInterval " + data.getId());
    }


}