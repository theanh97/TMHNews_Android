package com.theanhdev97.tintucbongda.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by DELL on 29/04/2018.
 */


import com.jgabrielfreitas.core.BlurImageView;
import com.theanhdev97.tintucbongda.R;
import com.theanhdev97.tintucbongda.screen.data.api.News;
import com.theanhdev97.tintucbongda.screen.util.ImageHelper;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private Context mContext;
  private ArrayList<News> mObjects;
  private NewsClickListener mClickListener;

  //  private boolean isEmpty;
  private int EMPTY = 0;
  private int HAVE = 1;

  public NewsAdapter(Context context, ArrayList<News> objects) {
    mContext = context;
    mObjects = objects;

//    if (mObjects == null || mObjects.size() == 0)
//      isEmpty = true;
//    else
//      isEmpty = false;
  }

  @Override
  public int getItemViewType(int position) {
    if (mObjects.size() == 0)
      return EMPTY;
    return HAVE;
  }

  @Override
  public int getItemCount() {
    if (mObjects.size() == 0)
      return 1;
    return mObjects.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == EMPTY) {
      View v = LayoutInflater.from(mContext).inflate(R.layout.layout_nodata, parent, false);
      return new NoDataHolder(v);
    } else {
      View v = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
      return new NewsViewHolder(v);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    // have data
    if (holder instanceof NewsViewHolder) {
      final NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
      News news = mObjects.get(position);
      newsViewHolder.tvTitle.setText(news.title);
//      newsViewHolder.tvDescription.setText(news.shortDescription);
      newsViewHolder.tvPubDate.setText(news.pubDate);
      ImageHelper.loadImage(
          mContext,
          newsViewHolder.imvImage,
          news.image,
          R.drawable.ic_news_placeholder);
//      newsViewHolder.imvImage.setBlur(1);
    }
  }

  public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imvImage;
//    private BlurImageView imvImage;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvPubDate;

    public NewsViewHolder(View itemView) {
      super(itemView);
      tvTitle = itemView.findViewById(R.id.tv_title);
//      tvDescription = itemView.findViewById(R.id.tv_description);
      tvPubDate = itemView.findViewById(R.id.tv_pubDate);
      imvImage = itemView.findViewById(R.id.imv_image);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      mClickListener.onClick(getAdapterPosition());
    }
  }

  public class NoDataHolder extends RecyclerView.ViewHolder {
        ImageView imvIcon;
//    BlurImageView imvIcon;

    @SuppressLint("WrongViewCast")
    public NoDataHolder(View itemView) {
      super(itemView);
      imvIcon = itemView.findViewById(R.id.ic_nodata);
    }
  }

  public void setOnClickListener(NewsClickListener listener) {
    this.mClickListener = listener;
  }
}