package com.jay.gankformvp.presenter.contract;

import com.jay.gankformvp.data.entity.Gank;
import com.jay.gankformvp.presenter.base.BaseView;
import java.util.List;

/**
 * Created by jay on 16/5/18.
 */
public interface DailyGankContract {

  interface View extends BaseView {
    void showData(List<Gank> results);
  }

  interface Presenter {
    void loadGankData(int year, int month, int day);
  }

}
