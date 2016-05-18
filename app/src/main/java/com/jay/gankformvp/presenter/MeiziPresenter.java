package com.jay.gankformvp.presenter;

import android.support.annotation.NonNull;
import com.jay.gankformvp.GankApi;
import com.jay.gankformvp.data.MeiziData;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.presenter.base.Presenter;
import com.jay.gankformvp.presenter.contract.MeiziContract;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jay on 16/5/16.
 */
public class MeiziPresenter extends Presenter<MeiziContract.View>
    implements MeiziContract.Presenter {

  @Override public void loadMeiziData(int page) {
    this.mCompositeSubscription.add(
        mGanApi.getMeiziData(page)
            .map(new Func1<MeiziData, List<Meizi>>() {
              @Override public List<Meizi> call(MeiziData meiziData) {
                return meiziData.results;
              }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Meizi>>() {
              @Override public void onCompleted() {
                mView.showLoadingIndicator(false);
              }

              @Override public void onError(Throwable e) {
                mView.showLoadingIndicator(false);
                mView.showFilure(e);
              }

              @Override public void onNext(List<Meizi> list) {
                mView.showData(list);
              }
            }));
  }
}
