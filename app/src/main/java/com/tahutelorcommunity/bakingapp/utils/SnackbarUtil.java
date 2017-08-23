package com.tahutelorcommunity.bakingapp.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Fikry-PC on 8/20/2017.
 */

public class SnackbarUtil {
    public static void showSnackBar(View v, Snackbar snackbar, String s, int length){
        if(snackbar != null){
            snackbar.dismiss();
        }
        snackbar = Snackbar.make(v,s,length);
        snackbar.show();
    }
}
