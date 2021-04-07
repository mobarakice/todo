package com.mobarak.todo.ui.statistics

import com.mobarak.todo.data.db.entity.Task

object StatisticsUtils {
    fun getActiveAndCompletedStats(tasks: List<Task?>?): StatsResult {
        return if (tasks == null || tasks.isEmpty()) {
            StatsResult(0f, 0f)
        } else {
            val totalTasks = tasks.size
            var numberOfActiveTasks = 0
            for (task in tasks) {
                if (!task!!.isCompleted) {
                    numberOfActiveTasks++
                }
            }
            StatsResult(100f * numberOfActiveTasks / tasks.size,
                    100f * (totalTasks - numberOfActiveTasks) / tasks.size)
        }
    }
}