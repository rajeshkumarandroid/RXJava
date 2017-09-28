package com.realm.myapplication;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.txt);

        getGistObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //subcribes based on data emits like getting data from server or events listing
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("onSubscribe is ", "<>>>");

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        InputStream is =
                                new ByteArrayInputStream(s.getBytes());

                        String result = getStringFromInputStream(is);
                        Log.e("response is ", "<>>>" + result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onError is ", "<>>>" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete is ", "<>>>");
                    }
                });

    }


    public Observable<String> getGistObservable() {

        return Observable.defer(new ObservableFromCallable<>(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                try {
                    return Observable.just(getGist());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;

            }
        }));
    }

    @Nullable
    private String getGist() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("https://api.github.com/users/hadley/repos")
                .build();
        Response response = client.newCall(req).execute();
        if (response.isSuccessful()) {
            return response.body().source().readUtf8();
        }
        return null;

    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}
