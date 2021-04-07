package com.mobarak.todo.utility;

import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {
    private EventUnhandledContent unhandledContent;

    public EventObserver(EventUnhandledContent unhandledContent) {
        this.unhandledContent = unhandledContent;
    }

    public interface EventUnhandledContent {
        void onEventUnhandledContent(Event event);
    }

    @Override
    public void onChanged(Event<T> event) {
        if (event != null && event.getContentIfNotHandled() != null) {
            unhandledContent.onEventUnhandledContent(event);
        }
    }
}
