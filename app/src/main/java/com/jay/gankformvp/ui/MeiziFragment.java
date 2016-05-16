package com.jay.gankformvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.presenter.contract.MeiziContract;
import com.jay.gankformvp.ui.base.BaseFragment;
import com.jay.gankformvp.widget.ScrollChildSwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 16/5/16.
 */
public class MeiziFragment extends BaseFragment implements MeiziContract.View {

  MeiziContract.Presenter mPresenter;
  List<Meizi> mLists;
  @BindView(R.id.recycleview_meizi) RecyclerView mRecycleviewMeizi;
  @BindView(R.id.swipe_refresh_layout) ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

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

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Override public void showLoadingIndicator(boolean activity) {

  }

  @Override public void showData(List<Meizi> list) {
  }

  @Override public void showFilure(Throwable throwable) {

  }

  @Override public void showNoData() {

  }

  @Override public void setPresenter(MeiziContract.Presenter presenter) {
    mPresenter = presenter;
  }
}
