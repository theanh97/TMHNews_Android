package com.theanhdev97.tintucbongda.screen.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 16/05/2018.
 */

public class ApiClient {
  public static final String BASE_URL = "http://tienmahoa.net.vn/wp-json/wp/v2/";
  public static Retrofit sRetrofit = null;

  public static Retrofit getClient() {
    if (sRetrofit == null) {
      sRetrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return sRetrofit;
  }
}
