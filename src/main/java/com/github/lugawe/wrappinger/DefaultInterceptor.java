package com.github.lugawe.wrappinger;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class DefaultInterceptor<A extends Annotation> implements Interceptor<A> {

    private final Class<A> annotationClass;
    private final Handler<A> handler;

    public DefaultInterceptor(Class<A> annotationClass, Handler<A> handler) {
        this.annotationClass = Objects.requireNonNull(annotationClass);
        this.handler = Objects.requireNonNull(handler);
    }

    @Override
    @RuntimeType
    public final Object intercept(@Origin Method method, @SuperCall Callable<Object> resolve) throws Throwable {

        handler.init(method.getAnnotation(annotationClass));
        handler.before();

        Object result = null;
        try {
            result = resolve.call();
        } finally {
            handler.after(result);
        }

        return result;
    }

    @Override
    public final Class<A> getAnnotationClass() {
        return annotationClass;
    }

    public final Handler<A> getHandler() {
        return handler;
    }

}
