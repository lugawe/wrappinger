package com.github.lugawe.wrappinger;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface Interceptor<A extends Annotation> {

    @RuntimeType
    Object intercept(@Origin Method method, @SuperCall Callable<Object> resolve) throws Throwable;

    Class<A> getAnnotationClass();

}
