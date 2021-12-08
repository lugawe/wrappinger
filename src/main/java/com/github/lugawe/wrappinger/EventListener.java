package com.github.lugawe.wrappinger;

public interface EventListener {

    void onException(Exception e);

    void onCompletion(Object result);

}
