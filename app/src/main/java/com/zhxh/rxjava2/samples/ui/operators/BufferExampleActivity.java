package com.zhxh.rxjava2.samples.ui.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhxh.rxjava2.samples.R;
import com.zhxh.rxjava2.samples.utils.AppConstant;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhxh on 2018/1/19
 */
public class BufferExampleActivity extends AppCompatActivity {

    private static final String TAG = BufferExampleActivity.class.getSimpleName();
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
    }

    /*
     * buffer操作符 传的值以列表list存储
     *
     */
    private void doSomeWork() {

        Observable<List<String>> buffered = getObservable().buffer(3, 2);

        // 3 表示取最大的三个数值，这些数值从获取最开始index开始并创建
        // 1 表示每次一步
        // 得到如下列表
        // 1 - 1, 2, 3
        // 2 - 2, 3, 4
        // 3 - 3, 4, 5
        // 4 - 4, 5
        // 5 - 5

        // 3 表示取最大的三个数值，这些数值从获取最开始index开始并创建
        // 2 表示每次一步
        // 得到如下列表
        // 1 - 1, 2, 3
        // 3 - 3, 4, 5
        // 5 - 5

        buffered.subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("1", "2", "3", "4", "5");
    }

    private Observer<List<String>> getObserver() {
        return new Observer<List<String>>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<String> stringList) {
                textView.append(" onNext size : " + stringList.size());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onNext : size :" + stringList.size());
                for (String value : stringList) {
                    textView.append(" value : " + value);
                    textView.append(AppConstant.LINE_SEPARATOR);
                    Log.d(TAG, " : value :" + value);
                }

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