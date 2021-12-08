package com.github.lugawe.wrappinger;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WrapperTest {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Wrapped {
    }

    public static class Executor {

        private final UUID id = UUID.randomUUID();

        public Executor() {
        }

        @Wrapped
        public String execute() {
            return id.toString();
        }

    }

    @Test
    void wrap() throws Exception {

        Executor executor = Wrapper.wrap(Executor.class, Wrapped.class, EmptyHandler.getInstance()).newInstance();

        assertNotNull(executor);
        assertNotNull(executor.execute());

    }

}
