package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;

public interface Handler<T, A extends Annotation> {

    void init(T parent, A annotation);

    void before();

    void after();

    EventListener getEventListener();

}
