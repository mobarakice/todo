package com.mobarak.todo.ui.statistics;

import com.mobarak.todo.data.db.entity.Task;

import java.util.List;

public class StatisticsUtils {
    public static StatsResult getActiveAndCompletedStats(List<Task> tasks) {

        if (tasks == null || tasks.isEmpty()) {
            return new StatsResult(0f, 0f);
        } else {
            int totalTasks = tasks.size();
            int numberOfActiveTasks = 0;
            for (Task task : tasks) {
                if (!task.isCompleted()) {
                    numberOfActiveTasks++;
                }
            }
            return new StatsResult(100f * numberOfActiveTasks / tasks.size(),
                    100f * (totalTasks - numberOfActiveTasks) / tasks.size());
        }
    }
}
