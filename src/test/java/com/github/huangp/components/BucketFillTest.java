package com.github.huangp.components;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketFillTest {
    private static final Logger log =
            LoggerFactory.getLogger(BucketFillTest.class);
    private static final String COLOR = "o";

    @Test
    public void canFill() {
        Canvas canvas = new CanvasImpl(20, 4);
        HorizontalLine horizontalLine = new HorizontalLine(1, 6, 2);
        VerticalLine verticalLine = new VerticalLine(3, 4, 6);
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        canvas = rectangle.draw(verticalLine.draw(horizontalLine.draw(canvas)));

        log.debug("before fill:\n{}", canvas);

        BucketFill bucketFill = new BucketFill(10, 3, COLOR);
        Canvas result = bucketFill.draw(canvas);
        log.debug("after fill:\n{}", result);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|oooooooooooooxxxxx|")
                .line("|xxxxxxooooooox   x|")
                .line("|     xoooooooxxxxx|")
                .line("|     xoooooooooooo|")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void willNotDrawIfPointIsOutsideBoundary() {
        Canvas canvas = new CanvasImpl(3, 1);
        BucketFill columnInvalid = new BucketFill(canvas.maxDrawableColumn() + 1,
                canvas.maxDrawableRow(), COLOR);

        assertThat(columnInvalid.draw(canvas)).isSameAs(canvas);

        BucketFill rowInvalid = new BucketFill(canvas.maxDrawableColumn(),
                canvas.maxDrawableRow() + 1, COLOR);
        assertThat(rowInvalid.draw(canvas)).isSameAs(canvas);
    }

    @Test
    public void willNotFillIfPointIsAlreadyPainted() {
        Canvas canvas = new CanvasImpl(3, 1);
        VerticalLine line = new VerticalLine(1, 1, 1);
        canvas = line.draw(canvas);
        log.debug("{}", canvas);
        BucketFill bucketFill = new BucketFill(canvas.maxDrawableColumn(),
                canvas.maxDrawableRow(), COLOR);
        Canvas result = bucketFill.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void canFillInEnclosingArea() {
        Canvas canvas = new CanvasImpl(20, 4);
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        canvas = rectangle.draw(canvas);
        log.debug("before fill:\n{}", canvas);

        BucketFill bucketFill = new BucketFill(15, 2, COLOR);
        Canvas result = bucketFill.draw(canvas);
        log.debug("after fill:\n{}", result);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|             xxxxx|")
                .line("|             xooox|")
                .line("|             xxxxx|")
                .line("|                  |")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void canFillAroundEnclosingArea() {
        Canvas canvas = new CanvasImpl(20, 5);
        Rectangle rectangle = new Rectangle(5, 2, 8, 4);
        canvas = rectangle.draw(canvas);
        log.debug("before fill:\n{}", canvas);

        BucketFill bucketFill = new BucketFill(2, 3, COLOR);
        Canvas result = bucketFill.draw(canvas);
        log.debug("after fill:\n{}", result);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|oooooooooooooooooo|")
                .line("|ooooxxxxoooooooooo|")
                .line("|oooox  xoooooooooo|")
                .line("|ooooxxxxoooooooooo|")
                .line("|oooooooooooooooooo|")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }
}
