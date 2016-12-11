package com.github.huangp.components;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class HorizontalLineTest {
    private static final Logger log =
            LoggerFactory.getLogger(HorizontalLineTest.class);

    @Test
    public void canDrawLine() {
        Canvas canvas = new CanvasImpl(20, 4);

        HorizontalLine horizontalLine = new HorizontalLine(1, 6, 2);
        Canvas result = horizontalLine.draw(canvas);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|                  |")
                .line("|xxxxxx            |")
                .line("|                  |")
                .line("|                  |")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void willNotDrawIfColOutsideOfBoundary() {
        Canvas canvas = new CanvasImpl(20, 4);

        HorizontalLine horizontalLine = new HorizontalLine(1, canvas.maxDrawableColumn() + 1, 2);
        Canvas result = horizontalLine.draw(canvas);

        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfRowOutsideOfBoundary() {
        Canvas canvas = new CanvasImpl(20, 4);

        HorizontalLine horizontalLine = new HorizontalLine(1, 6, canvas.maxDrawableRow() + 1);
        Canvas result = horizontalLine.draw(canvas);

        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void canDrawASinglePoint() {
        CanvasImpl canvas = new CanvasImpl(3, 1);
        HorizontalLine line = new HorizontalLine(1, 1, 1);
        Canvas result = line.draw(canvas);
        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("---")
                .line("|x|")
                .line("---");
        assertThat(result.toString()).isEqualTo(expected.toString());
    }

}
