package com.github.lugawe.wrappinger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MultiEventListener implements EventListener {

    protected final List<? extends EventListener> eventListeners;

    public MultiEventListener(Collection<? extends EventListener> eventListeners) {
        if (eventListeners == null) {
            throw new NullPointerException("eventListeners");
        } else if (eventListeners.isEmpty()) {
            this.eventListeners = Collections.emptyList();
        } else {
            this.eventListeners = Collections.unmodifiableList(new ArrayList<>(eventListeners));
        }
    }

    @Override
    public void onCompletion(Object result) {
        eventListeners.forEach(eventListener -> eventListener.onCompletion(result));
    }

    @Override
    public void onException(Exception e) {
        eventListeners.forEach(eventListener -> eventListener.onException(e));
    }

}
