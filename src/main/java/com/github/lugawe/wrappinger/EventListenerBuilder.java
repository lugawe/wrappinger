package com.github.lugawe.wrappinger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventListenerBuilder {

    protected final List<EventListener> eventListeners = new ArrayList<>();

    public EventListenerBuilder() {
    }

    public EventListenerBuilder withEventListener(EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException("eventListener");
        }
        eventListeners.add(eventListener);
        return this;
    }

    public EventListenerBuilder withOnCompletion(Consumer<Object> onCompletion) {
        if (onCompletion == null) {
            throw new NullPointerException("onCompletion");
        }
        eventListeners.add(new EventListener() {

            @Override
            public void onCompletion(Object result) {
                onCompletion.accept(result);
            }

            @Override
            public void onException(Exception e) {
            }

        });
        return this;
    }

    public EventListenerBuilder withOnException(Consumer<Exception> onException) {
        if (onException == null) {
            throw new NullPointerException("onException");
        }
        eventListeners.add(new EventListener() {

            @Override
            public void onCompletion(Object result) {
            }

            @Override
            public void onException(Exception e) {
                onException.accept(e);
            }

        });
        return this;
    }

    public EventListener build() {
        return new MultiEventListener(eventListeners);
    }

}
