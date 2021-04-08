package com.mobarak.todo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This is entity class
 * @author mobarak
 */
@Entity(tableName = "tasks")
class Task @JvmOverloads constructor(
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @ColumnInfo(name = "is_completed") var isCompleted: Boolean = false,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var id: Long =0
) {

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + isCompleted.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}