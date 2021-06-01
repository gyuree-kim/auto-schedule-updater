package com.android.frontend;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    String BASE_URL = "http://10.10.33.16:3000"; // 서버 url
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitInterface server = retrofit.create(RetrofitInterface.class);

}
