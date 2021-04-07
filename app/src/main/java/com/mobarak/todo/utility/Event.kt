package com.mobarak.todo.utility

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<T>(private val content: T) {
    var isHasBeenHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    val contentIfNotHandled: T?
        get() = if (isHasBeenHandled) {
            null
        } else {
            isHasBeenHandled = true
            content
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T {
        return content
    }
}