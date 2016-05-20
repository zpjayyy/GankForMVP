package com.jay.gankformvp.uitl;

import android.text.SpannableString;

/**
 * Created by jay on 16/5/19.
 */
public class StringStyles {

  public static SpannableString format(String text, Object ... whats) {
    SpannableString spannableString = new SpannableString(text);
    for (Object what : whats) {
      spannableString.setSpan(what, 0, text.length(), 0);
    }
    return spannableString;
  }

}
