package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;

public abstract class AbstractHandler<A extends Annotation> implements Handler<A> {

    protected A annotation;

    public AbstractHandler() {
    }

    @Override
    public void init(A annotation) {
        this.annotation = annotation;
    }

    @Override
    public void before() {
    }

    @Override
    public void after(Object result) {
    }

}
