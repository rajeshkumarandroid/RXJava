package com.realm.Operations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.realm.R;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Rajesh Kumar on 28-04-2018.
 */
public class Operators extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        jusoperation();
//        fromOperation();
//        concatOperation();
        zipOperation();
    }

    private void jusoperation(){
        Observable<Integer> obserable = Observable.just(1,2,3,4,5);
            obserable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("onSubscribe is ", "<>>>");

                    }

                    @Override
                    public void onNext(@NonNull Integer s) {

//                        InputStream is =
//                                new ByteArrayInputStream(s.getBytes());
//
//                        String result = getStringFromInputStream(is);
                        Log.e("response is ", "<>>>" + s);
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

    private void fromOperation(){
        Observable<String> observable = Observable.fromArray(new String[] {"A","B","C","D"});

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String >(){

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe is ", "<>>>");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("response is ", "<>>>" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError is ", "<>>>" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete is ", "<>>>");
                    }
                });
    }

    private void concatOperation(){
        Observable<String > observable1 = Observable.fromArray(new String[] {"a","b","c"});
        Observable<String > observable2 = Observable.fromArray(new String[] {"d","e","f"});

        Observable.concat(observable1,observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("subcribe ","<>concatination<><");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("emitted values is ","<><><>"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error found","<><>"+e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                            Log.e("emitted completed","<>><");
                    }
                });




    }


    private void zipOperation(){
        Observable<Integer> observable1 = Observable.fromArray(new Integer[]{1,2,3,4,5});
        Observable<String > observable2 = Observable.fromArray(new String[] {"A","B","C","D"});

        Observable<ZipObject> objectObservable = Observable.zip(observable1, observable2, new BiFunction<Integer, String, ZipObject>() {
            @Override
            public ZipObject apply(Integer integer, String s) throws Exception {
                ZipObject zipObject = new ZipObject();
                zipObject.number = integer;
                zipObject.alphabet= s;
                return zipObject;
            }
        });

        objectObservable.subscribe(new Observer<ZipObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ZipObject zipObject) {
                Log.e("values is ","<<><>><"+zipObject.number+" string "+zipObject.alphabet);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    class ZipObject{
        int number;
        String alphabet;
    }



}
