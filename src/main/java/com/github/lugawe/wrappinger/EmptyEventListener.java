package com.github.lugawe.wrappinger;

public final class EmptyEventListener implements EventListener {

    public static final EmptyEventListener INSTANCE = new EmptyEventListener();

    private EmptyEventListener() {
    }

    @Override
    public final void onCompletion(Object result) {
    }

    @Override
    public final void onException(Exception e) {
    }

}
