package com.jay.gankformvp.ui.adapter;

import android.content.Context;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Gank;
import java.util.List;

/**
 * Created by jay on 16/5/18.
 */
public class DailyGankAdapter extends BaseQuickAdapter<Gank> {

  public DailyGankAdapter(Context context, List<Gank> data) {
    super(context, R.layout.item_gank_detail, data);
  }

  @Override protected void convert(BaseViewHolder baseViewHolder, Gank gank) {
    baseViewHolder.setText(R.id.textview_title, gank.desc);
  }
}
