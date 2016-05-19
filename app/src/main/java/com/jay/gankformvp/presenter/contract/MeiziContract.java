package com.jay.gankformvp.presenter.contract;

import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.presenter.base.BaseView;
import java.util.List;

/**
 * Created by jay on 16/5/16.
 */
public interface MeiziContract {

  interface View extends BaseView {

    void showData(List<Meizi> list);

  }

  interface Presenter {

    void loadMeiziData(int page);
  }
}
