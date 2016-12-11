package com.github.huangp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.commands.Arg;
import com.github.huangp.commands.CommandInitializer;
import com.github.huangp.commands.CommandInstruction;
import com.github.huangp.commands.PositiveIntValueConverter;
import com.github.huangp.components.point.Point;
import com.github.huangp.components.point.RectanglePoint;
import com.google.common.base.Preconditions;
import javaslang.collection.Vector;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@CommandInstruction(instruction = "R", drawable = Rectangle.class, arguments = {
        @Arg(PositiveIntValueConverter.class),
        @Arg(PositiveIntValueConverter.class),
        @Arg(PositiveIntValueConverter.class),
        @Arg(PositiveIntValueConverter.class)
})
public class Rectangle implements Drawable {
    private static final Logger log = LoggerFactory.getLogger(Rectangle.class);
    private final int topLeftX;
    private final int topLeftY;
    private final int bottomRightX;
    private final int bottomRightY;

    public Rectangle(int topLeftX, int topLeftY, int bottomRightX,
            int bottomRightY) {
        Preconditions.checkArgument(topLeftX > 0 && bottomRightX > 0 && topLeftY > 0 && bottomRightY > 0, "x1, y1, x2, y2 must all be greater than 0");
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    @CommandInitializer
    public static Drawable instance(int topLeftX, int topLeftY, int bottomRightX,
            int bottomRightY) {
        return new Rectangle(topLeftX, topLeftY, bottomRightX, bottomRightY);
    }

    @Override
    public Canvas draw(Canvas canvas) {
        if (!(topLeftX < bottomRightX && topLeftY < bottomRightY)) {
            log.warn("invalid top left and bottom right coordinate ({}, {}) and ({}, {})", topLeftX, topLeftY, bottomRightX, bottomRightY);
            return canvas;
        }
        Vector<Vector<Point>> current = canvas.getPoints();
        Vector<Vector<Point>> result = current;

        if (!canvas.isColumnWithinBoundary(topLeftX) ||
                !canvas.isColumnWithinBoundary(bottomRightX)) {
            log.warn(
                    "invalid drawing instruction: rectangle top and bottom x coordinates are out of canvas boundary: 1 to {}", canvas.maxDrawableColumn());
            return canvas;
        }

        if (!canvas.isRowWithinBoundary(bottomRightY)) {
            log.warn(
                    "invalid drawing instruction: rectangle top and bottom y coordinates are out of canvas boundary: 1 to {}", canvas.maxDrawableRow());
            return canvas;
        }

        Vector<Point> topRow = current.get(topLeftY);
        result = result.replace(topRow, replacePointsInRange(topRow, topLeftX,
                bottomRightX));

        for (int i = topLeftY + 1; i < bottomRightY; i++) {
            Vector<Point> oldRow = current.get(i);
            Point leftEdgePoint = oldRow.get(topLeftX);
            Point rightEdgePoint = oldRow.get(bottomRightX);
            Vector<Point> newRow =
                    oldRow.replace(leftEdgePoint, new RectanglePoint())
                            .replace(rightEdgePoint, new RectanglePoint());
            result = result.replace(oldRow, newRow);
        }

        Vector<Point> bottomRow = current.get(bottomRightY);
        result = result.replace(bottomRow,
                replacePointsInRange(bottomRow, topLeftX, bottomRightX));
        return new CanvasImpl(result);

    }

    private static Vector<Point> replacePointsInRange(Vector<Point> oldRow,
            int from,
            int inclusiveTo) {
        Vector<Point> newRow = oldRow;
        for (int i = from; i <= inclusiveTo; i++) {
            Point oldPoint = oldRow.get(i);
            newRow = newRow.replace(oldPoint, new RectanglePoint());
        }
        return newRow;
    }
}
