package com.mobarak.todo.ui.statistics;

public class StatsResult {
    float activeTasksPercent;
    float completedTasksPercent;

    public StatsResult(float activeTasksPercent, float completedTasksPercent) {
        this.activeTasksPercent = activeTasksPercent;
        this.completedTasksPercent = completedTasksPercent;
    }

    public float getActiveTasksPercent() {
        return activeTasksPercent;
    }

    public void setActiveTasksPercent(float activeTasksPercent) {
        this.activeTasksPercent = activeTasksPercent;
    }

    public float getCompletedTasksPercent() {
        return completedTasksPercent;
    }

    public void setCompletedTasksPercent(float completedTasksPercent) {
        this.completedTasksPercent = completedTasksPercent;
    }
}
