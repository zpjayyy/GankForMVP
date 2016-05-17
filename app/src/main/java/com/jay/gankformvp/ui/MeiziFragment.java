package com.jay.gankformvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.presenter.contract.MeiziContract;
import com.jay.gankformvp.ui.adapter.MeiziAdapter;
import com.jay.gankformvp.ui.base.BaseFragment;
import com.jay.gankformvp.widget.ScrollChildSwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/5/16.
 */
public class MeiziFragment extends BaseFragment implements MeiziContract.View {

  @BindView(R.id.recycleview_meizi) RecyclerView mRecycleviewMeizi;
  @BindView(R.id.swipe_refresh_layout) ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

  MeiziContract.Presenter mPresenter;
  List<Meizi> mLists;
  MeiziAdapter mAdapter;

  public static MeiziFragment newInstance() {
    return new MeiziFragment();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mLists = new ArrayList<>();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_meizi_list, container, false);
    ButterKnife.bind(this, root);
    return root;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUpRecyclerView();
  }

  private void setUpRecyclerView() {
    final StaggeredGridLayoutManager manager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    mRecycleviewMeizi.setLayoutManager(manager);

    mAdapter = new MeiziAdapter(getActivity(), mLists);
    mRecycleviewMeizi.setAdapter(mAdapter);
    mRecycleviewMeizi.setItemAnimator(new DefaultItemAnimator());

    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mLists.clear();
        mPresenter.start();
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Override public void showLoadingIndicator(boolean activity) {
    mSwipeRefreshLayout.setRefreshing(activity);
  }

  @Override public void showData(List<Meizi> list) {
    mLists.addAll(list);
    mAdapter.notifyDataSetChanged();
  }

  @Override public void showFilure(Throwable throwable) {
    Snackbar.make(mSwipeRefreshLayout, "network is not aviable", Snackbar.LENGTH_LONG)
        .setAction("Action", new View.OnClickListener() {
          @Override public void onClick(View v) {
          }
        })
        .show();
  }

  @Override public void showNoData() {

  }

  @Override public void setPresenter(MeiziContract.Presenter presenter) {
    mPresenter = presenter;
  }
}
