package com.retrofitwithwidgets.web;

import com.retrofitwithwidgets.web.model.QuoteResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ashish (Min2) on 07/07/16.
 */
public interface QuoteAPIService {

    String ENDPOINT = "http://api.motiwish.com/";

    @GET("quotes/todays-thought")
    Call<QuoteResponse> getTodayThought();


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
