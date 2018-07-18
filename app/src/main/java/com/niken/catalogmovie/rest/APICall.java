package com.niken.catalogmovie.rest;

import com.niken.catalogmovie.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICall {
    @GET("search/movie/")
    Call<MovieModel> getSearch(@Query("api_key") String apiKey, @Query("query") String q);
}
