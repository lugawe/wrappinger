package com.github.lugawe.wrappinger;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WrapperTest {

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

        Executor executor = Wrapper.wrap(Executor.class, new AbstractHandler<Executor, Wrapped>() {

            @Override
            public EventListener getEventListener() {
                return new EventListener() {
                    @Override
                    public void onCompletion(Object result) {

                    }

                    @Override
                    public void onException(Exception e) {

                    }
                };
            }

        }).newInstance();

        assertNotNull(executor);
        assertNotNull(executor.execute());

        Wrapper.wrap(Executor.class, new AbstractHandler<Executor, Wrapped>() {

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
