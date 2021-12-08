package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

public final class EmptyHandler<T, A extends Annotation> implements Handler<T, A> {

    private static final Handler<?, ?> instance = new EmptyHandler<>();

    @SuppressWarnings("unchecked")
    public static <T, A extends Annotation> Handler<T, A> getInstance() {
        return (Handler<T, A>) instance;
    }

    private EmptyHandler() {
    }

    @Override
    public final void init(T parent, A annotation) {
    }

    @Override
    public final List<? extends EventListener> getEventListeners() {
        return Collections.emptyList();
    }

    @Override
    public final void before() {
    }

    @Override
    public final void after() {
    }

}
