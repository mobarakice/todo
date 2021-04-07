package com.mobarak.todo.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    public ScrollChildSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public View scrollUpChild = null;

    @Override
    public boolean canChildScrollUp() {
        return scrollUpChild != null ? scrollUpChild.canScrollVertically(-1) : super.canChildScrollUp();
    }

    public View getScrollUpChild() {
        return scrollUpChild;
    }

    public void setScrollUpChild(View scrollUpChild) {
        this.scrollUpChild = scrollUpChild;
    }
}
