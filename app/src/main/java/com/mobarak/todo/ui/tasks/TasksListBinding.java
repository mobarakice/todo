package com.mobarak.todo.ui.tasks;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobarak.todo.data.db.entity.Task;

import java.util.List;

public class TasksListBinding {

    /**
     * [BindingAdapter]s for the [Task]s list.
     */
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView listView, List<Task> items) {
        if (items != null) {
            if (listView.getAdapter() instanceof TasksAdapter) {
                ((TasksAdapter) listView.getAdapter()).submitList(items);
            }
        }
    }

    @BindingAdapter("app:completedTask")
    public static void setStyle(TextView textView, boolean enabled) {
        if (enabled) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
