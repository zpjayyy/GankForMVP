package com.jay.gankformvp.presenter.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by jay on 16/5/18.
 */
public class Presenter<T extends BaseView> implements BasePresenter<T> {

  public T mView;
  public CompositeSubscription mCompositeSubscription;

  @Override public void attachView(T view) {
    this.mView = view;
    this.mCompositeSubscription = new CompositeSubscription();
  }

  @Override public void detachView() {
    this.mView = null;
    this.mCompositeSubscription.unsubscribe();
    this.mCompositeSubscription = null;
  }

  public boolean isViewAttached() {
    return mView != null;
  }

  public T getMvpView() {
    return mView;
  }

  public void checkViewAttached() {
    if (!isViewAttached()) throw new MvpViewNotAttachedException();
  }


  public static class MvpViewNotAttachedException extends RuntimeException {
    public MvpViewNotAttachedException() {
      super("Please call Presenter.attachView(MvpView) before" +
          " requesting data to the Presenter");
    }
  }
}
