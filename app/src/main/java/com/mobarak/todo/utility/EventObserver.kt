package com.mobarak.todo.utility

import androidx.lifecycle.Observer

class EventObserver<T>(private val unhandledContent: EventUnhandledContent) : Observer<Event<T?>?> {
    interface EventUnhandledContent {
        fun onEventUnhandledContent(event: Event<*>?)
    }

    override fun onChanged(event: Event<T?>?) {
        if (event != null && event.contentIfNotHandled != null) {
            unhandledContent.onEventUnhandledContent(event)
        }
    }
}