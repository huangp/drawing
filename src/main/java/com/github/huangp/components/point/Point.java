package com.github.huangp.components.point;

public interface Point {

    boolean isPainted();

    default <P extends Point> P paint(P newPoint) {
        throw new UnsupportedOperationException("can not paint on " + this.getClass().getName());
    }

    String paint();
}
