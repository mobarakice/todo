package com.mobarak.todo.ui.tasks;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobarak.todo.data.db.entity.Task;
import com.mobarak.todo.databinding.TaskItemBinding;

public class TasksAdapter extends ListAdapter<Task, TasksAdapter.ViewHolder> {

    private TasksViewModel viewModel;

    public TasksAdapter(TasksViewModel viewModel) {
        super(new TaskDiffCallback());
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task item = getItem(position);
        holder.bind(viewModel, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TaskItemBinding binding;

        public ViewHolder(TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TasksViewModel viewModel, Task item) {
            binding.setViewmodel(viewModel);
            binding.setTask(item);
            binding.executePendingBindings();
        }
    }

}

/**
 * Callback for calculating the diff between two non-null items in a list.
 * <p>
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {

    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.equals(newItem);
    }
}
