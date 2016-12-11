package com.github.huangp.components;

import com.github.huangp.components.point.Point;
import javaslang.collection.Vector;

public interface Canvas extends Drawable {

    boolean isColumnWithinBoundary(int colNum);
    boolean isRowWithinBoundary(int rowNum);

    Vector<Vector<Point>> getPoints();

    int maxDrawableColumn();

    int maxDrawableRow();
}
