package com.github.huangp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.commands.Arg;
import com.github.huangp.commands.CommandInstruction;
import com.github.huangp.commands.PositiveIntValueConverter;
import com.github.huangp.components.point.LinePoint;
import com.github.huangp.components.point.Point;
import com.google.common.base.Preconditions;
import javaslang.collection.Vector;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@CommandInstruction(instruction = "L", handler = HorizontalLine.class, arguments = {
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class)
}
)
public class HorizontalLine implements Drawable {
    private static final Logger log =
            LoggerFactory.getLogger(HorizontalLine.class);
    private final int colStartNum;
    private final int colEndNum;
    private final int rowNum;

    public HorizontalLine(int colStartNum, int colEndNum, int rowNum) {
        Preconditions.checkArgument(colStartNum < colEndNum);
        this.colStartNum = colStartNum;
        this.colEndNum = colEndNum;
        this.rowNum = rowNum;
    }


    @Override
    public Canvas draw(Canvas canvas) {
        Vector<Vector<Point>> current = canvas.getPoints();


        // assuming line can be drawn on anything except the boundary
        if (rowNum < canvas.height()) {
            Vector<Point> oldRow = current.get(rowNum);
            if (canvas.isColumnDrawable(colStartNum) &&
                    canvas.isColumnDrawable(colEndNum)) {
                Vector<Point> newRow = oldRow;
                for (int i = colStartNum; i <= colEndNum; i++) {
                    Point point = oldRow.get(i);
                    newRow = newRow.replace(point, new LinePoint());
                }
                return new CanvasImpl(current.replace(oldRow, newRow));
            } else {
                log.warn(
                        "invalid drawing instruction: column start and end are not within the canvas boundary");
            }
        }
        log.warn(
                "invalid drawing instruction: row number is not within the canvas boundary");
        return canvas;
    }
}
