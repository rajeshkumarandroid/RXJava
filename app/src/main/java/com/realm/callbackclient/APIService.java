package com.realm.callbackclient;

import com.squareup.okhttp.ResponseBody;

import java.util.Map;
import java.util.Observable;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;

import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;


public interface APIService {
    @FormUrlEncoded
    @POST("/mobile_api_json/")
    io.reactivex.Observable<String> getApiResult(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("/finance_cfl/")
    Call<String> getApiResult1(@FieldMap Map<String, String> fields);

    public Call<ResponseBody> profilePicture(@Url String url);

    @Multipart
    @POST("/mobile_api_json/")
    Call<String> uploadImage(@Part MultipartBody.Part file, @PartMap() Map<String, String> fields);


    @POST()
    Call<String> coreApi(@Url String coreBaseUrl);

    @POST()
    Call<String> financeApi(@Url String financeBaseUrl);

    @POST()
    Call<String> insuranceApi(@Url String insuranceBaseUrl);

}
