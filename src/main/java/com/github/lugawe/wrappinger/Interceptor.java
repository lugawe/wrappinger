package com.github.lugawe.wrappinger;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Interceptor<T, A extends Annotation> {

    private final Handler<T, A> handler;
    private final Class<A> annotationClass;

    Interceptor(Handler<T, A> handler, Class<A> annotationClass) {
        this.handler = Objects.requireNonNull(handler, "handler");
        this.annotationClass = Objects.requireNonNull(annotationClass, "annotationClass");
    }

    @SuppressWarnings("unchecked")
    @RuntimeType
    public Object intercept(@This Object parent,
                            @Origin Method method,
                            @SuperCall Callable<Object> resolve) throws Exception {

        handler.init((T) parent, method.getAnnotation(annotationClass));

        EventListener listener = handler.getEventListener();
        if (listener == null) {
            throw new NullPointerException("listener is null");
        }

        handler.before();

        Object result = null;
        boolean completed = false;
        try {
            result = resolve.call();
            completed = true;
        } catch (Exception e) {
            listener.onException(e);
            throw e;
        } finally {
            if (completed) {
                listener.onCompletion(result);
            }
            handler.after();
        }

        return result;
    }

}
