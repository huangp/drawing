package com.github.huangp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.commands.Arg;
import com.github.huangp.commands.CommandInitializer;
import com.github.huangp.commands.CommandInstruction;
import com.github.huangp.commands.PositiveIntValueConverter;
import com.github.huangp.commands.SingleCharStringValueConverter;
import com.github.huangp.components.point.ColorPoint;
import com.github.huangp.components.point.Point;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import javaslang.Tuple2;
import javaslang.collection.Queue;
import javaslang.collection.Vector;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@CommandInstruction(instruction = "B", handler = BucketFill.class, arguments = {
        @Arg(PositiveIntValueConverter.class),
        @Arg(PositiveIntValueConverter.class),
        @Arg(SingleCharStringValueConverter.class)
})
public class BucketFill implements Drawable {
    private static final Logger log = LoggerFactory.getLogger(BucketFill.class);
    private final int x;
    private final int y;
    private final String color;

    public BucketFill(int x, int y, String color) {
        Preconditions.checkArgument(x > 0 && y > 0, "x and y must all be greater than 0");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(color) && color.length() == 1, "color must be exactly one character");
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @CommandInitializer
    public static Drawable instance(int x, int y, String color) {
        return new BucketFill(x, y, color);
    }

    @Override
    public Canvas draw(Canvas canvas) {
        Vector<Vector<Point>> current = canvas.getPoints();

        if (!canvas.isColumnDrawable(x) || y >= canvas.height()) {
            log.warn("({}, {}) is not a valid point in the canvas", x, y);
            return canvas;
        }

        Point startPoint = current.get(y).get(x);
        if (startPoint.isPainted()) {
            log.warn("({}, {}) has already been painted", x, y);
            return canvas;
        }

        Vector<Vector<CoordinatedPoint>> copy = Vector.empty();

        for (int row = 0; row < current.length(); row++) {
            Vector<CoordinatedPoint> line = Vector.empty();
            for (int col = 0; col < canvas.width(); col++) {
                line = line.append(new CoordinatedPoint(current.get(row).get(col), col, row));
            }
            copy = copy.append(line);
        }

        log.info("before BFT: {}", copy);
        Queue<CoordinatedPoint> queue = Queue.empty();
        CoordinatedPoint start = copy.get(y).get(x);
        queue = queue.enqueue(start);
        while(!queue.isEmpty()) {
            Tuple2<CoordinatedPoint, Queue<CoordinatedPoint>> tuple2 =
                    queue.dequeue();
            CoordinatedPoint point = tuple2._1();
            queue = tuple2._2();
            point.paintWithColor(color);
            point.visited = true;

            // visit all neighbours
            CoordinatedPoint nextPoint = null;
            if (hasPointUnder(point.y, canvas) && !getNextPointDown(point, copy).visited && !getNextPointDown(point, copy).point.isPainted()) {
                nextPoint = getNextPointDown(point, copy);
                queue = queue.enqueue(nextPoint);
            }
            if (hasPointAbove(point.y) && !getNextPointUp(point, copy).visited && !getNextPointUp(point, copy).point.isPainted()) {
                nextPoint = getNextPointUp(point, copy);
                queue = queue.enqueue(nextPoint);
            }
            if (hasPointOnTheLeft(point.x) && !getNextPointLeft(point, copy).visited && !getNextPointLeft(point, copy).point.isPainted()) {
                nextPoint = getNextPointLeft(point, copy);
                queue = queue.enqueue(nextPoint);
            }
            if (hasPointOnTheRight(point.x, canvas) && !getNextPointRight(point, copy).visited && !getNextPointRight(point, copy).point.isPainted()) {
                nextPoint = getNextPointRight(point, copy);
                queue = queue.enqueue(nextPoint);
            }

        }
        log.debug("after BFT: {}", copy);
        Vector<Vector<Point>> newPoints = copy.map(row -> {
            Vector<Point> newRow = row.map(p ->
                    p.newPoint != null ? p.newPoint : p.point);
            return Vector.ofAll(newRow);
        });
        return new CanvasImpl(newPoints);
    }

    private static CoordinatedPoint getNextPointRight(CoordinatedPoint point,
            Vector<Vector<CoordinatedPoint>> copy) {
        return copy.get(point.y).get(moveRight(point.x));
    }

    private static CoordinatedPoint getNextPointLeft(CoordinatedPoint point,
            Vector<Vector<CoordinatedPoint>> copy) {
        return copy.get(point.y).get(moveLeft(point.x));
    }

    private static CoordinatedPoint getNextPointUp(CoordinatedPoint point,
            Vector<Vector<CoordinatedPoint>> copy) {
        return copy.get(moveUp(point.y)).get(point.x);
    }

    private static CoordinatedPoint getNextPointDown(CoordinatedPoint point,
            Vector<Vector<CoordinatedPoint>> copy) {
        return copy.get(moveDown(point.y)).get(point.x);
    }

    private static boolean hasPointOnTheRight(int x, Canvas canvas) {
        return canvas.isColumnDrawable(moveRight(x));
    }

    private static int moveRight(int x) {
        return x + 1;
    }

    private static boolean hasPointOnTheLeft(int x) {
        return moveLeft(x) > 0;
    }

    private static int moveLeft(int x) {
        return x - 1;
    }

    private static int moveUp(int y) {
        return y - 1;
    }

    private static boolean hasPointAbove(int y) {
        return moveUp(y) > 0;
    }

    private static int moveDown(int y) {
        return y + 1;
    }

    private static boolean hasPointUnder(int y, Canvas canvas) {
        return moveDown(y) <= canvas.height();
    }

    private static class CoordinatedPoint {
        private final Point point;
        private final int x;
        private final int y;
        private boolean visited;
        private ColorPoint newPoint;

        private CoordinatedPoint(Point point, int x, int y) {
            this.point = point;
            this.x = x;
            this.y = y;
        }

        void paintWithColor(String color) {
            this.newPoint = new ColorPoint(color);
        }

        @Override
        public String toString() {
            String newPointName = newPoint == null ? null : newPoint.getClass().getName();
            return String.format("(%d,%d) %s -> %s", x, y, point.getClass().getName(), newPointName);
        }
    }
}
