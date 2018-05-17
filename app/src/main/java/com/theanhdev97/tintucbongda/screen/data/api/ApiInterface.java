package com.theanhdev97.tintucbongda.screen.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DELL on 16/05/2018.
 */

public interface ApiInterface {
  @GET("posts")
  Call<ResponseBody> getPost(@Query("page") String page);
}
