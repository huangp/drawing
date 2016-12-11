package com.github.huangp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.components.point.LinePoint;
import com.github.huangp.components.point.Point;
import com.google.common.base.Preconditions;
import javaslang.collection.Vector;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class VerticalLine implements Drawable {
    private static final Logger log =
            LoggerFactory.getLogger(HorizontalLine.class);
    private final int rowStartNum;
    private final int rowEndNum;
    private final int colNum;

    public VerticalLine(int rowStartNum, int rowEndNum, int colNum) {
        Preconditions.checkArgument(rowEndNum >= rowStartNum);
        Preconditions.checkArgument(colNum > 0);
        this.rowStartNum = rowStartNum;
        this.rowEndNum = rowEndNum;
        this.colNum = colNum;
    }

    @Override
    public Canvas draw(Canvas canvas) {
        Vector<Vector<Point>> current = canvas.getPoints();
        Vector<Vector<Point>> newPoints = current;

        if (!canvas.isRowWithinBoundary(rowStartNum) || !canvas.isRowWithinBoundary(rowEndNum)) {
            log.warn("invalid drawing instruction: row start and end are not within canvas boundary: 1 to {}", canvas.maxDrawableRow());
            return canvas;
        }

        // assuming line can be drawn on anything except the boundary
        if (!canvas.isColumnWithinBoundary(colNum)) {
            log.warn(
                    "invalid drawing instruction: column number is not within the canvas boundary: 1 to {}", canvas.maxDrawableColumn());
            return canvas;
        }
        for (int i = rowStartNum; i <= rowEndNum; i++) {
            Vector<Point> oldRow = current.get(i);
            Point oldPoint = oldRow.get(colNum);
            Vector<Point> newRow =
                    oldRow.replace(oldPoint, new LinePoint());
            newPoints = newPoints.replace(oldRow, newRow);
        }
        return new CanvasImpl(newPoints);
    }
}
