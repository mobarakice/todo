package com.mobarak.todo.utility;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
public class Event<T> {
    private T content;
    private boolean hasBeenHandled;

    public Event(T content) {
        this.content = content;
    }

    /**
     * Returns the content and prevents its use again.
     */
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    public boolean isHasBeenHandled() {
        return hasBeenHandled;
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public T peekContent() {
        return content;
    }
}
