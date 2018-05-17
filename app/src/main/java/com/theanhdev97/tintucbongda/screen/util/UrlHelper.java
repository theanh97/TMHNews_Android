package com.theanhdev97.tintucbongda.screen.util;

/**
 * Created by DELL on 16/05/2018.
 */

public class UrlHelper {
  public static String getUrlByPage(String url, int page) {
    String result = url;
    if (page != 1)
      if (result.contains("?categories")) {
        result += "&page=" + page;
      } else {
        result += "?page=" + page;
      }
    return result;
  }

}
