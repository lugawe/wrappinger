package com.github.lugawe.wrappinger;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class Wrapper {

    @SuppressWarnings("unchecked")
    public static class Interceptor<T, A extends Annotation> {

        private final Class<A> annotationClass;
        private final Handler<T, A> handler;

        Interceptor(Class<A> annotationClass, Handler<T, A> handler) {
            this.annotationClass = Objects.requireNonNull(annotationClass);
            this.handler = Objects.requireNonNull(handler);
        }

        private void fireOnException(List<? extends EventListener> listeners, Exception e) {
            listeners.forEach(listener -> listener.onException(e));
        }

        private void fireOnCompletion(List<? extends EventListener> listeners, Object obj) {
            listeners.forEach(listener -> listener.onCompletion(obj));
        }

        @RuntimeType
        public Object intercept(@This Object parent,
                                @Origin Method method,
                                @SuperCall Callable<Object> resolve) throws Exception {

            handler.init((T) parent, method.getAnnotation(annotationClass));

            List<? extends EventListener> listeners = handler.getEventListeners();
            if (listeners == null) {
                throw new NullPointerException("listeners cannot be null");
            } else {
                listeners = Collections.unmodifiableList(listeners);
            }

            handler.before();

            Object result = null;
            try {
                result = resolve.call();
                fireOnCompletion(listeners, result);
            } catch (Exception e) {
                fireOnException(listeners, e);
                throw e;
            } finally {
                handler.after();
            }

            return result;
        }

    }

    private Wrapper() {
    }

    public static <T, A extends Annotation> Class<? extends T> wrap(ClassLoader classLoader,
                                                                    Class<T> targetClass,
                                                                    Class<A> annotationClass,
                                                                    Handler<T, A> handler) {

        if (classLoader == null) {
            throw new NullPointerException("classLoader");
        }
        if (targetClass == null) {
            throw new NullPointerException("targetClass");
        }
        if (annotationClass == null) {
            throw new NullPointerException("annotationClass");
        }
        if (handler == null) {
            throw new NullPointerException("handler");
        }

        return new ByteBuddy()
                .subclass(targetClass)
                .method(ElementMatchers.isAnnotatedWith(annotationClass))
                .intercept(MethodDelegation.to(new Interceptor<>(annotationClass, handler)))
                .make()
                .load(classLoader)
                .getLoaded();
    }

    public static <T, A extends Annotation> Class<? extends T> wrap(Class<T> targetClass,
                                                                    Class<A> annotationClass,
                                                                    Handler<T, A> handler) {

        return wrap(ClassLoader.getSystemClassLoader(), targetClass, annotationClass, handler);
    }

}
