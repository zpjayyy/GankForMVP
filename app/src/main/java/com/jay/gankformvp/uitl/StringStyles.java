package com.jay.gankformvp.uitl;

import android.text.SpannableString;

/**
 * Created by jay on 16/5/19.
 */
public class StringStyles {

  public static SpannableString format(Object what, String text) {
    SpannableString spannableString = new SpannableString(text);
    spannableString.setSpan(what, 0, text.length(), 0);
    return spannableString;
  }

}
