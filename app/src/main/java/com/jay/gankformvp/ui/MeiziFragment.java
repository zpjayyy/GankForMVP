package com.jay.gankformvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.func.OnItemTouchListener;
import com.jay.gankformvp.presenter.MeiziPresenter;
import com.jay.gankformvp.presenter.contract.MeiziContract;
import com.jay.gankformvp.ui.adapter.MeiziAdapter;
import com.jay.gankformvp.ui.base.BaseFragment;
import com.jay.gankformvp.uitl.DateUtils;
import com.jay.gankformvp.widget.ScrollChildSwipeRefreshLayout;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jay on 16/5/16.
 */
public class MeiziFragment extends BaseFragment implements MeiziContract.View {

  public static final String ARG_YEAR = "arg_year";
  public static final String ARG_MONTH = "arg_month";
  public static final String ARG_DAY = "arg_day";

  private static final int PRELOAD_SIZE = 4;

  @BindView(R.id.recycleview_meizi) RecyclerView mRecycleviewMeizi;
  @BindView(R.id.swipe_refresh_layout) ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

  private MeiziPresenter mPresenter;
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

    mPresenter = new MeiziPresenter();
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
    mPresenter.attachView(this);

    mPresenter.loadMeiziData(mPage);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
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
        mPage = 1;
        mPresenter.loadMeiziData(mPage);
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
  }

  private OnItemTouchListener getOnMeizTouchListener() {
    return new OnItemTouchListener() {
      @Override public void onTouch(View v, int position) {
        Date date = mLists.get(position).createdAt;

        Intent intent = new Intent(getActivity(), DailyDetailActivity.class);
        intent.putExtra(ARG_YEAR, DateUtils.getYear(date));
        intent.putExtra(ARG_MONTH, DateUtils.getMonth(date));
        intent.putExtra(ARG_DAY, DateUtils.getDay(date));

        startActivity(intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
      }
    };
  }

  private RecyclerView.OnScrollListener getOnScrollListener(
      final StaggeredGridLayoutManager layoutManager) {
    return new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

  @Override public void showLoadingIndicator(final boolean activity) {
    if (mSwipeRefreshLayout == null) {
      return;
    }
    if (!activity) {
      mSwipeRefreshLayout.postDelayed(new Runnable() {
        @Override public void run() {
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    } else {
      mSwipeRefreshLayout.post(new Runnable() {
        @Override public void run() {
          mSwipeRefreshLayout.setRefreshing(true);
        }
      });
    }

  }

  @Override public void showData(List<Meizi> list) {
    mLists.addAll(list);
    mAdapter.notifyDataSetChanged();
  }

  @Override public void showFilure(Throwable throwable) {
    Snackbar.make(mSwipeRefreshLayout, "network is not aviable", Snackbar.LENGTH_LONG)
        .setAction("Action", new View.OnClickListener() {
          @Override public void onClick(View v) {
            mPresenter.loadMeiziData(mPage);
          }
        })
        .show();
  }

  @Override public void showNoData() {

  }
}
