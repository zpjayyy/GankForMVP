package com.jay.gankformvp.presenter.base;

/**
 * Created by jay on 16/5/16.
 */
public interface BaseView {

  void showLoadingIndicator(boolean activity);

  void showFilure(Throwable throwable);

  void showNoData();

}
