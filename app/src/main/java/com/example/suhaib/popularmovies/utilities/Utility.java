package com.example.suhaib.popularmovies.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by suhaib on 9/13/18.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
