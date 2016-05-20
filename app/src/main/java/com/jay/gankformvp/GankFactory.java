package com.jay.gankformvp;

/**
 * Created by jay on 16/5/16.
 */
public class GankFactory {

  public static final int meiziSize = 10;
  public static final int gankSize = 5;

  protected static final Object monitor = new Object();

  static GankApi sGankSingleton = null;

  public static GankApi getGankSingleton() {
    synchronized (monitor) {
      if (sGankSingleton == null) {
        sGankSingleton = new GankRetrofit().getGankService();
      }
      return sGankSingleton;
    }
  }

}
