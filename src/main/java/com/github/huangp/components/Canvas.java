package com.github.huangp.components;

import com.github.huangp.components.point.Point;
import javaslang.collection.Vector;

public interface Canvas {
    int width();
    int height();

    boolean isColumnDrawable(int colNum);

    Vector<Vector<Point>> getPoints();

}
