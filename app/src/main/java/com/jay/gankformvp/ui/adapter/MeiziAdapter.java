package com.jay.gankformvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Meizi;
import com.jay.gankformvp.widget.RatioImageView;
import java.util.List;

/**
 * Created by jay on 16/5/17.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {

  Context mContext;
  List<Meizi> mMeiziList;

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
    Glide.with(mContext)
        .load(meizi.url)
        .centerCrop()
        .into(holder.imageMeizi);
  }

  @Override public int getItemCount() {
    return mMeiziList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_meizi) RatioImageView imageMeizi;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      //imageMeizi.setOriginalSize(50, 50);
    }
  }
}
