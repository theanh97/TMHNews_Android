package com.theanhdev97.tintucbongda.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theanhdev97.tintucbongda.R;
import com.theanhdev97.tintucbongda.screen.adapter.NewsAdapter;
import com.theanhdev97.tintucbongda.screen.adapter.NewsClickListener;
import com.theanhdev97.tintucbongda.screen.asynctask.GetNewsAsynctask;
import com.theanhdev97.tintucbongda.screen.data.api.News;
import com.theanhdev97.tintucbongda.screen.data.model.ResponseListener;
import com.theanhdev97.tintucbongda.screen.screen.NewsActivity;
import com.theanhdev97.tintucbongda.screen.util.Constants;
import com.theanhdev97.tintucbongda.screen.util.EndlessRecyclerViewScrollListener;
import com.theanhdev97.tintucbongda.screen.util.L;
import com.theanhdev97.tintucbongda.screen.util.NetworkHelper;
import com.theanhdev97.tintucbongda.screen.util.UrlHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 29/04/2018.
 */

public class NewsFragment extends Fragment implements ResponseListener, NewsClickListener, SwipeRefreshLayout.OnRefreshListener {
  @BindView(R.id.recycler_view)
  RecyclerView mRecyclerView;
  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;

  private Context mContext;
  private String mUrl;
  private int mCurrentPage = 1;
  private boolean mIsFirstLaunch;
  private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
  private NewsAdapter mNewsAdapter;
  private ArrayList<News> mNews;
  private GridLayoutManager mLayoutManager;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    mUrl = getArguments().getString(Constants.KEY_NEWS_URL);
    mContext = getContext();
    return inflater.inflate(R.layout.news_fragment, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
//    if (mIsFirstLaunch == false) {
    mCurrentPage = 1;
    getNewsByPage(mCurrentPage);
//    }
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    prepareUI();
//    getNewsByPage(mCurrentPage);
  }

  private void prepareUI() {
    mNews = new ArrayList<News>();
    mLayoutManager = new GridLayoutManager(mContext, 2);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mSwipeRefreshLayout.setRefreshing(true);
    mIsFirstLaunch = true;

    mSwipeRefreshLayout.setOnRefreshListener(this);
  }

  public void getNewsByPage(int page) {
    L.d("GetNewByPage() : " + page);
    if (NetworkHelper.isNetworkAvailable(mContext))
      new GetNewsAsynctask(this).execute(UrlHelper.getUrlByPage(mUrl, page));
    else {
      loadDataToView(null);
    }
  }

  @Override
  public void onSuccess(ArrayList<News> news) {
    loadDataToView(news);
  }

  private void loadDataToView(ArrayList<News> news) {
    L.d("Url : " + UrlHelper.getUrlByPage(mUrl, mCurrentPage));
    // no data
    boolean isNodata = false;
    if (news == null || news.size() == 0) {
      isNodata = true;
//      showViewError("Network is error");
    }
    // have
    else {
      if (mCurrentPage == 1) {
        mNews.clear();
      }
      mNews.addAll(news);
      mCurrentPage++;
    }

    if (mIsFirstLaunch) {
      mNewsAdapter = new NewsAdapter(mContext, mNews);
      mNewsAdapter.setOnClickListener(this);
      mRecyclerView.setAdapter(mNewsAdapter);
      mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
          // Nghi vấn bug load more
//          if (page - mCurrentPage == 1 || page - mCurrentPage == 0){
          if (totalItemsCount < page * 10) {
            mCurrentPage = page + 1;
            if (mNews.size() != 0)
              getNewsByPage(page + 1);
          }
//          }
        }
      };
      mRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);

      mIsFirstLaunch = false;
    }

    // config to linear layout for recyclerview
    if (mNews.size() == 0 && isNodata) {
      mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    mNewsAdapter.notifyDataSetChanged();

    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void onFailure(String error) {
    loadDataToView(null);
    showViewError(error);
  }

  @Override
  public void onNodata() {
//    Toast.makeText(mContext, "On nodata", Toast.LENGTH_SHORT).show();
    mSwipeRefreshLayout.setRefreshing(false);
  }

  private void showViewError(String error) {
    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void onClick(int position) {
    News news = mNews.get(position);
    Intent i = new Intent(mContext, NewsActivity.class);
    i.putExtra(Constants.KEY_NEWS_URL, news.link);
    startActivity(i);
  }

  @Override
  public void onRefresh() {
    mCurrentPage = 1;
    mSwipeRefreshLayout.setRefreshing(true);
    getNewsByPage(mCurrentPage);
  }
}
