package com.theanhdev97.tintucbongda.screen.data.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DELL on 29/04/2018.
 */

public class News implements Serializable {
//  @SerializedName("title")
//  public Title title;
//  @SerializedName("link")
//  public String link;
//  @SerializedName("content")
//  public String image;
//  @SerializedName("date")
//  public String pubDate;
//  @SerializedName("excerpt")
//  public ShortDescription shortDescription;
//
//  public News(Title title, String link, String image, String pubDate, ShortDescription shortDescription) {
//    this.title = title;
//    this.link = link;
//    this.image = image;
//    this.pubDate = pubDate;
//    this.shortDescription = shortDescription;
//  }
  public String title;
  public String link;
  public String image;
  public String pubDate;
  public String shortDescription;

  public News(String title, String link, String image, String pubDate, String shortDescription) {
    this.title = title;
    this.link = link;
    this.image = image;
    this.pubDate = pubDate;
    this.shortDescription = shortDescription;
  }
}
