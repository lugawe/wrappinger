package com.github.lugawe.wrappinger;

public final class EmptyEventListener implements EventListener {

    private static final EventListener instance = new EmptyEventListener();

    public static EventListener getInstance() {
        return instance;
    }

    private EmptyEventListener() {
    }

    @Override
    public final void onCompletion(Object result) {
    }

    @Override
    public final void onException(Exception e) {
    }

}
