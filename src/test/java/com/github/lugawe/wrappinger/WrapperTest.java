package com.github.lugawe.wrappinger;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
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

        Wrapper.wrap(Executor.class, Wrapped.class, new AbstractHandler<Executor, Wrapped>() {

            @Override
            public void before() {
            }

            @Override
            public void after() {
            }

            @Override
            public List<? extends EventListener> getEventListeners() {
                return Collections.singletonList(new EventListener() {

                    @Override
                    public void onCompletion(Object result) {

                        assertTrue(result instanceof String);

                        assertEquals(parent.id.toString(), result);
                    }

                    @Override
                    public void onException(Exception e) {
                    }

                });
            }

        }).newInstance().execute();


    }

}
