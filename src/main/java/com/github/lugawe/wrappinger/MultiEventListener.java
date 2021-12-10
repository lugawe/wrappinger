package com.github.lugawe.wrappinger;

import java.util.Collection;
import java.util.Collections;

public class MultiEventListener implements EventListener {

    protected final Collection<? extends EventListener> eventListeners;

    public MultiEventListener(Collection<? extends EventListener> eventListeners) {
        this.eventListeners = Collections.unmodifiableCollection(eventListeners);
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
