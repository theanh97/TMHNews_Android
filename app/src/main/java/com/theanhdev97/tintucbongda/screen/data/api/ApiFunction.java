package com.theanhdev97.tintucbongda.screen.data.api;

import com.theanhdev97.tintucbongda.screen.data.model.ResponseListener;
import com.theanhdev97.tintucbongda.screen.util.L;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 16/05/2018.
 */

public class ApiFunction {
  public static void getPost(String page, ResponseListener response){
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Call<ResponseBody> call = apiService.getPost(page);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
          String data = response.body().string();
          L.d("Data From Retrofit : " + data);
        } catch (IOException e) {
          L.d("Error From Retrofit : " + e.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        L.d("Error From Retrofit : " + t.getMessage());
      }
    });
  }
}
