package com.mobarak.todo.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mobarak.todo.utility.Utility;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "is_completed")
    private boolean isCompleted = false;

    @Ignore
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Ignore
    public Task(long id, String title, String description) {
        this(title, description);
        this.id = id;
    }

    public Task(long id, String title, String description, boolean isCompleted) {
        this(id, title, description);
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (getId() != task.getId()) return false;
        if (isCompleted() != task.isCompleted()) return false;
        if (getTitle() != null ? !getTitle().equals(task.getTitle()) : task.getTitle() != null)
            return false;
        return getDescription() != null ? getDescription().equals(task.getDescription()) : task.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isCompleted() ? 1 : 0);
        return result;
    }

    public String titleForList() {
        return Utility.isNullOrEmpty(title) ? description : title;
    }
}
