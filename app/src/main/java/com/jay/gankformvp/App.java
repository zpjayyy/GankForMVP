package com.jay.gankformvp;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jay on 16/5/16.
 */
public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    LeakCanary.install(this);
  }
}
