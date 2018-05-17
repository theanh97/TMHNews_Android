package com.theanhdev97.tintucbongda.screen.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theanhdev97.tintucbongda.screen.data.api.News;
import com.theanhdev97.tintucbongda.screen.data.api.NewsResponse;
import com.theanhdev97.tintucbongda.screen.data.model.ResponseListener;
import com.theanhdev97.tintucbongda.screen.util.L;
import com.theanhdev97.tintucbongda.screen.util.NetworkHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 29/04/2018.
 */

public class GetNewsAsynctask extends AsyncTask<String, Void, Object> {
  ResponseListener mResponseListener;
  String mUrl;

  public GetNewsAsynctask(ResponseListener listener) {
    this.mResponseListener = listener;
  }

  @Override
  protected Object doInBackground(String... strings) {
    mUrl = strings[0];
    L.d("URL : " + strings[0]);
    ArrayList<News> arrayList = new ArrayList<News>();
    try {
      String json = null;
      json = NetworkHelper.downloadContent(strings[0].trim());

      List<News> customers = null;
      JSONArray jsonArray = new JSONArray(json);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
        String title = jsonObject.optJSONObject("title").optString("rendered");
        title = title.replace("&#8220;","");
        title = title.replace("&#8221;","");
        title = title.replace("&#8211;","");
        String pubDate = jsonObject.optString("date").replace("T"," ");
        String shortDescription = jsonObject.optJSONObject("excerpt").getString("rendered");
        String link = jsonObject.optString("link");
        JSONObject embeded = jsonObject.optJSONObject("_embedded");
        String image = "";
        if (embeded != null) {
          JSONArray arr = embeded.optJSONArray("wp:featuredmedia");
          image = arr.optJSONObject(0).optString("source_url");
        }
//        L.d("Image : " + image);
        arrayList.add(new News(title, link, image, pubDate, shortDescription));
      }
      return arrayList;
    } catch (JSONException e) {
      return e.getMessage();
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  @Override
  protected void onPostExecute(Object result) {
    super.onPostExecute(result);
    if (result instanceof String) {
      if (result.equals(mUrl))
        mResponseListener.onNodata();
      else
        mResponseListener.onFailure((String) result);
    }
    // news arraylist
    else {
      mResponseListener.onSuccess((ArrayList<News>) result);
    }
  }
}
