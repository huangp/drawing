package com.github.huangp.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.huangp.components.Drawable;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.TYPE })
public @interface CommandInstruction {
    String instruction();

    Class<? extends Drawable> handler();

    Arg[] arguments();
}
