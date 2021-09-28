package com.github.lugawe.wrappinger;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class Wrapper {

    private static final Logger log = LoggerFactory.getLogger(Wrapper.class);

    protected final ClassLoader classLoader;

    public Wrapper(ClassLoader classLoader) {
        this.classLoader = Objects.requireNonNull(classLoader);
    }

    public Wrapper() {
        this(ClassLoader.getSystemClassLoader());
    }

    public <T, A extends Annotation> Class<? extends T> wrap(Class<? extends T> targetClass,
                                                             Interceptor<A> interceptor) {

        if (targetClass == null) {
            throw new NullPointerException("targetClass");
        }
        if (interceptor == null) {
            throw new NullPointerException("interceptor");
        }

        ByteBuddy byteBuddy = new ByteBuddy();
        Class<? extends T> result = byteBuddy
                .subclass(targetClass)
                .method(ElementMatchers.isAnnotatedWith(interceptor.annotationClass))
                .intercept(MethodDelegation.to(interceptor))
                .make()
                .load(classLoader)
                .getLoaded();

        log.debug("wrapped class created: {}", result);
        return result;
    }

    public <T, A extends Annotation> Class<? extends T> wrap(Class<? extends T> targetClass,
                                                             Class<A> annotationClass,
                                                             Handler<A> handler) {

        return wrap(targetClass, new Interceptor<>(annotationClass, handler));
    }

}
