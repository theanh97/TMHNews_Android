package com.theanhdev97.tintucbongda.screen.data.model;

import com.theanhdev97.tintucbongda.screen.data.api.News;

import java.util.ArrayList;

/**
 * Created by DELL on 29/04/2018.
 */

public interface ResponseListener {
  void onSuccess(ArrayList<News> news);

  void onNodata();

  void onFailure(String error);
}
