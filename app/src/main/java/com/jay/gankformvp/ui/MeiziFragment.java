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
import com.jay.gankformvp.func.OnMeizTouchListener;
import com.jay.gankformvp.presenter.contract.MeiziContract;
import com.jay.gankformvp.ui.adapter.MeiziAdapter;
import com.jay.gankformvp.ui.base.BaseFragment;
import com.jay.gankformvp.widget.ScrollChildSwipeRefreshLayout;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/5/16.
 */
public class MeiziFragment extends BaseFragment implements MeiziContract.View {

  private static final int PRELOAD_SIZE = 4;

  @BindView(R.id.recycleview_meizi) RecyclerView mRecycleviewMeizi;
  @BindView(R.id.swipe_refresh_layout) ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

  private MeiziContract.Presenter mPresenter;
  private List<Meizi> mLists;
  private MeiziAdapter mAdapter;
  private int mPage = 1;
  private boolean mIsFirstTimeTouchBotom = true;

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

    mAdapter = new MeiziAdapter(getContext(), mLists);
    mRecycleviewMeizi.setAdapter(mAdapter);
    mRecycleviewMeizi.setItemAnimator(new DefaultItemAnimator());

    mAdapter.setmOnMeizTouchListener(getOnMeizTouchListener());
    mRecycleviewMeizi.addOnScrollListener(getOnScrollListener(manager));

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

  private OnMeizTouchListener getOnMeizTouchListener() {
    return new OnMeizTouchListener() {
      @Override public void onTouch(View v, Meizi meizi) {
        Logger.d(meizi.createdAt);
      }
    };
  }

  private RecyclerView.OnScrollListener getOnScrollListener(
      final StaggeredGridLayoutManager layoutManager) {
    return new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int[] a = layoutManager.findLastVisibleItemPositions(new int[2]);
        Logger.d(a[0] + " : " + a[1]);
        Logger.d(mAdapter.getItemCount() + "~~~");
        boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
            >= mAdapter.getItemCount() - PRELOAD_SIZE;

        if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {

          if (!mIsFirstTimeTouchBotom) {
            mSwipeRefreshLayout.setRefreshing(true);
            mPage += 1;
            mPresenter.loadMeiziData(mPage);
          } else {
            mIsFirstTimeTouchBotom = false;
          }

        }
      }
    };
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
