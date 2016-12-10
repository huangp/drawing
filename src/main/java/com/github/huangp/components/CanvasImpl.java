package com.github.huangp.components;


import com.github.huangp.components.point.BarPoint;
import com.github.huangp.components.point.Hyphen;
import com.github.huangp.components.point.Point;
import com.github.huangp.components.point.UnpaintedPoint;
import com.google.common.base.Preconditions;
import javaslang.collection.Vector;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class CanvasImpl implements Canvas {

    private final Vector<Vector<Point>> points;
    private final int height;
    private final int width;

    public CanvasImpl(int width, int height) {
        Preconditions.checkArgument(width > 2 && height > 0);
        this.width = width;
        this.height = height;
        points = Vector.fill(height,
                () -> Vector.<Point>fill(width - 2, UnpaintedPoint::new)
                        .append(new BarPoint())
                        .prepend(new BarPoint()))
        .append(Vector.fill(width, Hyphen::new))
        .prepend(Vector.fill(width, Hyphen::new));

    }

    public CanvasImpl(Vector<Vector<Point>> newPoints) {
        points = newPoints;
        // top and bottom boundary is not counted
        height = points.length() - 2;
        width = points.head().length();
    }


    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public boolean isColumnDrawable(int colNum) {
        // can not draw on the left and right edge
        return colNum > 0 && colNum < width() - 1;
    }


    @Override
    public Vector<Vector<Point>> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        points.forEach(line -> {
            line.forEach(point -> sb.append(point.paint()));
            sb.append("\n");
        });
        return sb.toString();
    }
}
