package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

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
    public List<? extends EventListener> getEventListeners() {
        return Collections.emptyList();
    }

}
