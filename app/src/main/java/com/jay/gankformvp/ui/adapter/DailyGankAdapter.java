package com.jay.gankformvp.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.data.entity.Gank;
import com.jay.gankformvp.func.OnItemTouchListener;
import com.jay.gankformvp.uitl.StringStyles;
import java.util.List;
import org.w3c.dom.Text;

/**
 * Created by jay on 16/5/18.
 */
public class DailyGankAdapter extends RecyclerView.Adapter<DailyGankAdapter.ViewHolder> {

  private List<Gank> mGankList;
  private OnItemTouchListener mOnItemTouchListener;

  public DailyGankAdapter(List<Gank> data) {
    mGankList = data;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_detail, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Gank gank = mGankList.get(position);
    StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);

    if (position == 0) {
      showCategory(holder);
    } else {

      boolean theLastCategoryIsEqualThis =
          mGankList.get(position - 1).type.equals(mGankList.get(position).type);

      if (!theLastCategoryIsEqualThis) {
        showCategory(holder);
      } else {
        hideCategory(holder);
      }
    }

    holder.mTextCategory.setText(gank.type);

    SpannableStringBuilder builder =
        new SpannableStringBuilder(gank.desc).append(StringStyles.format(span, " (via. " +
            gank.who + ")"));
    CharSequence gankText = builder.subSequence(0, builder.length());

    holder.mTextTitle.setText(gankText);
  }

  private void showCategory(ViewHolder holder) {
    if (!isVisibleOf(holder.mTextCategory)) holder.mTextCategory.setVisibility(View.VISIBLE);

  }

  private void hideCategory(ViewHolder holder) {
    if (isVisibleOf(holder.mTextCategory)) holder.mTextCategory.setVisibility(View.GONE);
  }

  private boolean isVisibleOf(View v) {
    return v.getVisibility() == View.VISIBLE;
  }

  @Override public int getItemCount() {
    return mGankList.size();
  }

  public void setmOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
    this.mOnItemTouchListener = onItemTouchListener;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.textview_category) TextView mTextCategory;
    @BindView(R.id.textview_title) TextView mTextTitle;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @Override public void onClick(View v) {
      mOnItemTouchListener.onTouch(v, getLayoutPosition());
    }
  }
}
