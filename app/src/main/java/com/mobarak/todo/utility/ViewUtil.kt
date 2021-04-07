package com.mobarak.todo.utility

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.mobarak.todo.R
import com.mobarak.todo.ui.customview.ScrollChildSwipeRefreshLayout

object ViewUtil {
    fun showSnackbar(view: View, snackbarText: String?) {
        val snackbar = Snackbar.make(view, snackbarText!!, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorAccent))
        snackbar.show()
    }

    fun setupRefreshLayout(
            activity: FragmentActivity?,
            refreshLayout: ScrollChildSwipeRefreshLayout,
            scrollUpChild: View?
    ) {
        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity!!, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark)
        )
        // Set the scrolling view in the custom SwipeRefreshLayout.
        if (scrollUpChild != null) {
            refreshLayout.setScrollUpChild(scrollUpChild)
        }
    }
}