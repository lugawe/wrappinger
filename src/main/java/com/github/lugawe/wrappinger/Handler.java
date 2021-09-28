package com.github.lugawe.wrappinger;

import java.lang.annotation.Annotation;

public interface Handler<A extends Annotation> {

    void init(A annotation);

    void before();

    void after(Object result);

}
