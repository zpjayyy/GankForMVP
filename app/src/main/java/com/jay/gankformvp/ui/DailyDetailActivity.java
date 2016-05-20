package com.jay.gankformvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Gank;
import com.jay.gankformvp.func.OnItemTouchListener;
import com.jay.gankformvp.presenter.DailyGankPresenter;
import com.jay.gankformvp.presenter.contract.DailyGankContract;
import com.jay.gankformvp.ui.adapter.DailyGankAdapter;
import com.jay.gankformvp.ui.base.ToolbarActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/5/18.
 */
public class DailyDetailActivity extends ToolbarActivity implements DailyGankContract.View {

  @BindView(R.id.recyclerview_gank) RecyclerView mRecyclerviewGank;

  private int mYear, mMonth, mDay;

  private DailyGankPresenter mPresenter;
  private DailyGankAdapter mAdapter;
  private List<Gank> mGankList;

  @Override protected int providerContentViewId() {
    return R.layout.activity_daily_detail;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    getData();
    setUpPresenter();
    setUpRecyclerView();
  }

  @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mPresenter.loadGankData(mYear, mMonth, mDay);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mPresenter.detachView();
  }

  private void getData() {
    mYear = getIntent().getIntExtra(MeiziFragment.ARG_YEAR, -1);
    mMonth = getIntent().getIntExtra(MeiziFragment.ARG_MONTH, -1);
    mDay = getIntent().getIntExtra(MeiziFragment.ARG_DAY, -1);
  }

  private void setUpPresenter() {
    mPresenter = new DailyGankPresenter();
    mPresenter.attachView(this);
  }

  private void setUpRecyclerView() {
    mGankList = new ArrayList<>();
    mAdapter = new DailyGankAdapter(mGankList);

    final LinearLayoutManager manager = new LinearLayoutManager(this);
    mRecyclerviewGank.setLayoutManager(manager);
    mRecyclerviewGank.setAdapter(mAdapter);

    mAdapter.setOnItemTouchListener(getOnItemTouchListener());
  }

  @Override public boolean canBack() {
    return true;
  }

  @Override public void showData(List<Gank> results) {
    mGankList.addAll(results);
    mAdapter.notifyDataSetChanged();
  }

  @Override public void showLoadingIndicator(boolean activity) {

  }

  @Override public void showFilure(Throwable throwable) {
    throwable.printStackTrace();
    Snackbar.make(mRecyclerviewGank, "network is not aviable", Snackbar.LENGTH_LONG)
        .setAction("retry", new View.OnClickListener() {
          @Override public void onClick(View v) {
            mPresenter.loadGankData(mYear, mMonth, mDay);
          }
        })
        .show();
  }

  @Override public void showNoData() {

  }

  private OnItemTouchListener getOnItemTouchListener() {
    return new OnItemTouchListener() {
      @Override public void onTouch(View v, int position) {
        startActivity(
            WebViewActivity.newIntent(DailyDetailActivity.this, mGankList.get(position).url));
      }
    };
  }
}
