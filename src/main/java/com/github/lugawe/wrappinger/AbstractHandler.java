package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;

public abstract class AbstractHandler<T, A extends Annotation> implements Handler<T, A> {

    protected T parent;
    protected A annotation;

    public AbstractHandler() {
    }

    @Override
    public void init(T parent, A annotation) {
        this.parent = parent;
        this.annotation = annotation;
    }

    @Override
    public void before() {
    }

    @Override
    public void after() {
    }

}
