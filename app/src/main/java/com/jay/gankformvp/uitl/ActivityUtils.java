package com.jay.gankformvp.uitl;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jay on 16/5/16.
 */
public class ActivityUtils {

  public static void addFragmentToActivity(@Nullable FragmentManager manager,
      @Nullable Fragment fragment, int frameId) {
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.add(frameId, fragment);
    transaction.commit();
  }
}
