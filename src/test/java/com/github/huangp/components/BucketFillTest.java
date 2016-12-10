package com.github.huangp.components;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BucketFillTest {
    private static final Logger log =
            LoggerFactory.getLogger(BucketFillTest.class);

    @Test
    public void canFill() {
        Canvas canvas = new CanvasImpl(20, 4);
        HorizontalLine horizontalLine = new HorizontalLine(1, 6, 2);
        VerticalLine verticalLine = new VerticalLine(3, 4, 6);
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        canvas = rectangle.draw(verticalLine.draw(horizontalLine.draw(canvas)));

        log.debug("before fill:\n{}", canvas);

        BucketFill bucketFill = new BucketFill(10, 3, "o");
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

}
