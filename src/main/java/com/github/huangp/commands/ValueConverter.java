package com.github.huangp.commands;

public interface ValueConverter<T> {

    T from(String value);
}
