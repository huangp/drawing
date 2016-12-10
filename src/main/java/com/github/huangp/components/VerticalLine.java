package com.github.huangp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.components.point.LinePoint;
import com.github.huangp.components.point.Point;
import com.google.common.base.Preconditions;
import javaslang.collection.List;
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
        Preconditions.checkArgument(rowEndNum > rowStartNum);
        Preconditions.checkArgument(colNum > 0);
        this.rowStartNum = rowStartNum;
        this.rowEndNum = rowEndNum;
        this.colNum = colNum;
    }

    @Override
    public Canvas draw(Canvas canvas) {
        Vector<Vector<Point>> current = canvas.getPoints();
        Vector<Vector<Point>> newPoints = current;

        if (rowStartNum >= canvas.width() || rowEndNum >= canvas.width()) {
            log.warn("invalid drawing instruction: row start and end are not within canvas boundary");
            return canvas;
        }

        // assuming line can be drawn on anything except the boundary
        if (canvas.isColumnDrawable(colNum)) {
            for (int i = rowStartNum; i <= rowEndNum; i++) {
                Vector<Point> oldRow = current.get(i);
                Point oldPoint = oldRow.get(colNum);
                Vector<Point> newRow = oldRow.replace(oldPoint, new LinePoint());
                newPoints = newPoints.replace(oldRow, newRow);
            }
            return new CanvasImpl(newPoints);
        }
        log.warn("invalid drawing instruction: column number is not within the canvas boundary");
        return canvas;
    }
}
