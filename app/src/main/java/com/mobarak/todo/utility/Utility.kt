package com.mobarak.todo.utility

object Utility {
    fun isNullOrEmpty(text: String?): Boolean {
        return text == null || text.trim { it <= ' ' }.length <= 0
    }
}