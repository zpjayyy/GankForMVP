package com.jay.gankformvp.presenter.base;

/**
 * Created by jay on 16/5/16.
 */
public interface BasePresenter<V extends BaseView> {

  void attachView(V view);

  void detachView();

}
