package com.zhxh.rxjava2.samples.ui.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhxh.rxjava2.samples.R;
import com.zhxh.rxjava2.samples.model.User;
import com.zhxh.rxjava2.samples.utils.AppConstant;
import com.zhxh.rxjava2.samples.utils.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhxh on 2018/1/18
 */
public class ZipExampleActivity extends AppCompatActivity {

    private static final String TAG = ZipExampleActivity.class.getSimpleName();
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(view -> doSomeWork());
    }

    /*
     * 该例子中
     * 我们得到苹果爱好者与香蕉爱好者并找出博爱者
     */
    private void doSomeWork() {
        Observable.zip(getAppleFansObservable(), getBananaFansObservable(),
                new BiFunction<List<User>, List<User>, List<User>>() {
                    @Override
                    public List<User> apply(List<User> appleFans, List<User> bananaFans) {
                        return Utils.filterUserWhoLovesBoth(appleFans, bananaFans);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<List<User>> getAppleFansObservable() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                e.onNext(Utils.getUserListWhoLovesApple());
                e.onComplete();
            }
        });
    }

    private Observable<List<User>> getBananaFansObservable() {
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) {
                if (!e.isDisposed()) {
                    e.onNext(Utils.getUserListWhoLovesBanana());
                    e.onComplete();
                }
            }
        });
    }

    private Observer<List<User>> getObserver() {
        return new Observer<List<User>>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<User> userList) {
                textView.append(" onNext");
                textView.append(AppConstant.LINE_SEPARATOR);
                for (User user : userList) {
                    textView.append(" 博爱者名字 : " + user.getFirstname());
                    textView.append(AppConstant.LINE_SEPARATOR);
                }
                Log.d(TAG, " onNext : " + userList.size());
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


}