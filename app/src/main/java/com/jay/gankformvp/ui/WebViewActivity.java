package com.jay.gankformvp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jay.gankformvp.R;
import com.jay.gankformvp.ui.base.ToolbarActivity;

/**
 * Created by jay on 16/5/20.
 */
public class WebViewActivity extends ToolbarActivity {

  public static final String ARG_URL = "arg_url";

  @BindView(R.id.progress_bar) ContentLoadingProgressBar mProgressBar;
  @BindView(R.id.web_view) WebView mWebView;

  String mUrl;

  @Override protected int providerContentViewId() {
    return R.layout.activity_web_view;
  }

  public static Intent newIntent(Context context, String url) {
    Intent intent = new Intent(context, WebViewActivity.class);
    intent.putExtra(ARG_URL, url);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    getData();
    setUpWebView();
  }

  @Override protected void onResume() {
    super.onResume();
    if (mWebView != null) mWebView.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    if (mWebView != null) mWebView.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mWebView != null) mWebView.destroy();
  }

  private void getData() {
    mUrl = getIntent().getStringExtra(ARG_URL);
  }

  private void setUpWebView() {

    WebSettings settings = mWebView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setLoadWithOverviewMode(true);
    settings.setAppCacheEnabled(true);
    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    settings.setSupportZoom(true);

    mWebView.setWebViewClient(new MyWebViewClient());
    mWebView.setWebChromeClient(new MyWebChromeClient());

    mWebView.loadUrl(mUrl);
  }


  @Override public boolean canBack() {
    return true;
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
          if (mWebView.canGoBack()) {
            mWebView.goBack();
          } else {
            finish();
          }
          return true;
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  class MyWebViewClient extends WebViewClient {
    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      if (!TextUtils.isEmpty(url)) view.loadUrl(url);
      return true;
    }
  }

  class MyWebChromeClient extends WebChromeClient {

    @Override public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      mProgressBar.setProgress(newProgress);
      if (newProgress == 100) {
        mProgressBar.hide();
      } else {
        mProgressBar.show();
      }
    }
  }

}
