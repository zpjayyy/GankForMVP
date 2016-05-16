package com.jay.gankformvp.presenter.contract;

import com.jay.gankformvp.data.MeiziData;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.presenter.base.BasePresenter;
import com.jay.gankformvp.presenter.base.BaseView;
import java.util.List;
import rx.Subscription;

/**
 * Created by jay on 16/5/16.
 */
public interface MeiziContract {

  interface View extends BaseView<Presenter> {

    void showLoadingIndicator(boolean activity);

    void showData(List<Meizi> list);

    void showFilure(Throwable throwable);

    void showNoData();
  }

  interface Presenter extends BasePresenter {

    Subscription loadMeiziData();
  }
}
