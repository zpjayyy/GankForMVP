package com.jay.gankformvp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.jay.gankformvp.R;

/**
 * Created by jay on 16/5/16.
 */
public abstract class ToolbarActivity extends BaseActivity {

  abstract protected int providerContentViewId();

  public void onToolbarClick(){}

  protected AppBarLayout mAppBar;
  protected Toolbar mToolbar;
  protected boolean mIsHideen = false;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(providerContentViewId());

    mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    if (mAppBar == null || mToolbar == null) {
      throw new IllegalStateException("not toobar");
    }

    mToolbar.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onToolbarClick();
      }
    });
    setSupportActionBar(mToolbar);

    if (canBack()) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

  }

  public boolean canBack() {
    return false;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  protected void setAppBarAlpha(int alpha) {
    mAppBar.setAlpha(alpha);
  }

  protected void hideOrShowToobar() {
    mAppBar.animate()
        .translationY(mIsHideen ? 0 : -mAppBar.getHeight())
        .setInterpolator(new DecelerateInterpolator(2))
        .start();

    mIsHideen = !mIsHideen;
  }

}
