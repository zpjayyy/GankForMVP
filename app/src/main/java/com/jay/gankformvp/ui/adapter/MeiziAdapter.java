package com.jay.gankformvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.func.OnItemTouchListener;
import com.jay.gankformvp.widget.RatioImageView;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;

/**
 * Created by jay on 16/5/17.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {

  private Context mContext;
  private List<Meizi> mMeiziList;
  private OnItemTouchListener mOnMeizTouchListener;

  public MeiziAdapter(Context context, List<Meizi> meiziList) {
    mContext = context;
    mMeiziList = meiziList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Meizi meizi = mMeiziList.get(position);
    Glide.with(mContext).load(meizi.url).centerCrop().into(holder.imageMeizi);
  }

  @Override public int getItemCount() {
    return mMeiziList.size();
  }

  public void setmOnMeizTouchListener(OnItemTouchListener onMeizTouchListener) {
    this.mOnMeizTouchListener = onMeizTouchListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_meizi) RatioImageView imageMeizi;

    public ViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      imageMeizi.setOriginalSize(50, 80);
      RxView.clicks(itemView)
          .throttleFirst(500, TimeUnit.MILLISECONDS)
          .subscribe(new Action1<Void>() {
            @Override public void call(Void aVoid) {
              mOnMeizTouchListener.onTouch(itemView, getLayoutPosition());
            }
          });
    }
  }
}
