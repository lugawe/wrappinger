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

        Executor executor = Wrapper.wrap(Executor.class, Wrapped.class, new AbstractHandler<Executor, Wrapped>() {

            @Override
            public EventListener getEventListener() {
                return EmptyEventListener.getInstance();
            }

        }).newInstance();

        assertNotNull(executor);
        assertNotNull(executor.execute());

        Wrapper.wrap(Executor.class, Wrapped.class, new AbstractHandler<Executor, Wrapped>() {

            @Override
            public EventListener getEventListener() {
                return new EventListener() {

                    @Override
                    public void onCompletion(Object result) {

                        assertTrue(result instanceof String);

                        assertEquals(parent.id.toString(), result);
                    }

                    @Override
                    public void onException(Exception e) {
                    }

                };
            }

        }).newInstance().execute();


    }

}
