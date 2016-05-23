package com.jay.gankformvp.presenter;

import com.jay.gankformvp.data.GankData;
import com.jay.gankformvp.data.entity.Gank;
import com.jay.gankformvp.presenter.base.Presenter;
import com.jay.gankformvp.presenter.contract.DailyGankContract;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jay on 16/5/18.
 */
public class DailyGankPresenter extends Presenter<DailyGankContract.View>
    implements DailyGankContract.Presenter {

  private List<Gank> mGankList;

  public DailyGankPresenter() {
    mGankList = new ArrayList<>();
  }

  @Override public void loadGankData(int year, int month, int day) {
    this.mCompositeSubscription.add(
        mGanApi.getGankData(year, month, day)
            .map(new Func1<GankData, GankData.Result>() {
              @Override public GankData.Result call(GankData gankData) {
                return gankData.results;
              }
            })
            .map(new Func1<GankData.Result, List<Gank>>() {
              @Override public List<Gank> call(GankData.Result result) {
                return addAllResults(result);
              }
            })
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
              @Override public void call() {
                mView.showLoadingIndicator(true);
              }
            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Gank>>() {
              @Override public void onCompleted() {
                mView.showLoadingIndicator(false);
              }

              @Override public void onError(Throwable e) {
                mView.showLoadingIndicator(false);
                mView.showFilure(e);
              }

              @Override public void onNext(List<Gank> results) {
                mView.showData(results);
              }
            }));
  }

  private List<Gank> addAllResults(GankData.Result results) {
    if (results.androidList != null) {mGankList.addAll(results.androidList);}
    if (results.iOSList != null) {mGankList.addAll(results.iOSList);}
    if (results.webList != null) {mGankList.addAll(results.webList);}
    if (results.recommendList != null) {mGankList.addAll(results.recommendList);}
    if (results.resourceList != null) {mGankList.addAll(results.resourceList);}
    if (results.videoList != null) {mGankList.addAll(results.videoList);}
    if (results.welfareList != null) {mGankList.addAll(results.welfareList);}

    return mGankList;

  }
}
