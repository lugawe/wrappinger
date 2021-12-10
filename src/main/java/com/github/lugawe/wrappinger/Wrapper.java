package com.github.lugawe.wrappinger;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.annotation.Annotation;

public final class Wrapper {

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
                .intercept(MethodDelegation.to(new Interceptor<>(handler, annotationClass)))
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
