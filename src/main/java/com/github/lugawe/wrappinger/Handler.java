package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Handler<T, A extends Annotation> {

    void init(T parent, A annotation);

    List<? extends EventListener> getEventListeners();

    void before();

    void after();

}
