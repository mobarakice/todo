package com.mobarak.todo.utility;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.mobarak.todo.R;
import com.mobarak.todo.ui.customview.ScrollChildSwipeRefreshLayout;

public class ViewUtil {


    public static void showSnackbar(View view, String snackbarText) {
        Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG).show();
    }


    public static void setupRefreshLayout(
            FragmentActivity activity,
            ScrollChildSwipeRefreshLayout refreshLayout,
            View scrollUpChild
    ) {
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        if(scrollUpChild != null){
            refreshLayout.setScrollUpChild(scrollUpChild);
        }
    }

}
