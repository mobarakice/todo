package com.mobarak.todo.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobarak.todo.data.db.entity.Task
import com.mobarak.todo.databinding.TaskItemBinding

class TasksAdapter(private val viewModel: TasksViewModel) : ListAdapter<Task?, TasksAdapter.ViewHolder>(TaskDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    class ViewHolder(var binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: TasksViewModel?, item: Task?) {
            binding.viewmodel = viewModel
            binding.task = item
            binding.executePendingBindings()
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
internal class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}