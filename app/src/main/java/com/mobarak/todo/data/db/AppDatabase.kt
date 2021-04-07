package com.mobarak.todo.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mobarak.todo.data.db.dao.TaskDao;
import com.mobarak.todo.data.db.entity.Task;

/**
 * The Room database that contains the task table
 * @author mobarak
 */
@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "todo.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
