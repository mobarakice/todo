package com.mobarak.todo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mobarak.todo.utility.Utility

/**
 * This is entity class
 * @author mobarak
 */
@Entity(tableName = "tasks")
class Task @Ignore constructor(@field:ColumnInfo(name = "title") var title: String?, @field:ColumnInfo(name = "description") var description: String?) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long

    @ColumnInfo(name = "is_completed")
    var isCompleted = false

    @Ignore
    constructor(id: Long, title: String?, description: String?) : this(title, description) {
        this.id = id
    }

    constructor(id: Long, title: String?, description: String?, isCompleted: Boolean) : this(id, title, description) {
        this.isCompleted = isCompleted
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Task) return false
        val task = o
        if (id != task.id) return false
        if (isCompleted != task.isCompleted) return false
        if (if (title != null) title != task.title else task.title != null) return false
        return if (description != null) description == task.description else task.description == null
    }

    override fun hashCode(): Int {
        var result = (id xor (id ushr 32)).toInt()
        result = 31 * result + if (title != null) title.hashCode() else 0
        result = 31 * result + if (description != null) description.hashCode() else 0
        result = 31 * result + if (isCompleted) 1 else 0
        return result
    }

    fun titleForList(): String? {
        return if (Utility.isNullOrEmpty(title)) description else title
    }
}