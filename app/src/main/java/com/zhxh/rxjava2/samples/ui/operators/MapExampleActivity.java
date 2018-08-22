package com.zhxh.rxjava2.samples.ui.operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhxh.rxjava2.samples.R;
import com.zhxh.rxjava2.samples.model.ApiUser;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhxh on 2018/1/18
 */
public class MapExampleActivity extends AppCompatActivity {

    private static final String TAG = MapExampleActivity.class.getSimpleName();
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
     * 从服务器中得到ApiUser对象，而我们需求的是支持本地数据库的
     * User对象，这时我们用Map操作符转换
     */
    private void doSomeWork() {

        final Disposable[] disposable = new Disposable[1];
        Observable.create(new ObservableOnSubscribe<List<ApiUser>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ApiUser>> e) {
                if (!e.isDisposed()) {
                    e.onNext(Utils.getApiUserList());
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<ApiUser>, List<User>>() {

                    @Override
                    public List<User> apply(List<ApiUser> apiUsers) {
                        return Utils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .subscribe(new Observer<List<User>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable[0] = d;
                        Log.d(TAG, " onSubscribe onSubscribe: " + disposable[0].isDisposed());
                    }

                    @Override
                    public void onNext(List<User> userList) {
                        textView.append(" onNext 开始");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        for (User user : userList) {
                            textView.append(" 名字 : " + user.firstname);
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
                        textView.append(" onComplete整体");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onSubscribe onComplete: " + disposable[0].isDisposed());

                        disposable[0].dispose();

                        Log.d(TAG, " onSubscribe onComplete: " + disposable[0].isDisposed());

                    }
                });
    }


}