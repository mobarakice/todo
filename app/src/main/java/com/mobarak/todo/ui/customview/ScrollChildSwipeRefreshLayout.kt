package com.mobarak.todo.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ScrollChildSwipeRefreshLayout(context: Context, attrs: AttributeSet?) : SwipeRefreshLayout(context, attrs) {
    var scrollUpChild: View? = null
    override fun canChildScrollUp(): Boolean {
        return if (scrollUpChild != null) scrollUpChild!!.canScrollVertically(-1) else super.canChildScrollUp()
    }
}