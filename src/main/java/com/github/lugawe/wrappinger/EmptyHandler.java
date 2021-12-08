package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;

public final class EmptyHandler<T, A extends Annotation> extends AbstractHandler<T, A> {

    private static final Handler<?, ?> instance = new EmptyHandler<>();

    @SuppressWarnings("unchecked")
    public static <T, A extends Annotation> Handler<T, A> getInstance() {
        return (Handler<T, A>) instance;
    }

    private EmptyHandler() {
    }

    @Override
    public final void before() {
    }

    @Override
    public final void after() {
    }

}
