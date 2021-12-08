package com.github.lugawe.wrappinger;

public interface EventListener {

    void onCompletion(Object result);

    void onException(Exception e);

}
