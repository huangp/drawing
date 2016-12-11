package com.github.huangp.components;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VerticalLineTest {

    @Test
    public void canDrawLine() {
        Canvas canvas = new CanvasImpl(20, 4);
        HorizontalLine horizontalLine = new HorizontalLine(1, 6, 2);
        VerticalLine verticalLine = new VerticalLine(3, 4, 6);
        canvas = horizontalLine.draw(canvas);

        Canvas result = verticalLine.draw(canvas);
        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|                  |")
                .line("|xxxxxx            |")
                .line("|     x            |")
                .line("|     x            |")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void willNotDrawIfRowOutsideCanvas() {
        Canvas canvas = new CanvasImpl(20, 4);
        VerticalLine verticalLine = new VerticalLine(3, canvas.maxDrawableRow() + 1, 6);
        Canvas result = verticalLine.draw(canvas);

        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfColumnOutsideCanvas() {
        Canvas canvas = new CanvasImpl(20, 4);
        VerticalLine verticalLine = new VerticalLine(3, 4, canvas.maxDrawableColumn() + 1);
        Canvas result = verticalLine.draw(canvas);

        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void canDrawASinglePoint() {
        CanvasImpl canvas = new CanvasImpl(3, 1);
        VerticalLine verticalLine = new VerticalLine(1, 1, 1);
        Canvas result = verticalLine.draw(canvas);
        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("---")
                .line("|x|")
                .line("---");
        assertThat(result.toString()).isEqualTo(expected.toString());
    }

}
