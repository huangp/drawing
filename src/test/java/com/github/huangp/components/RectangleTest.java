package com.github.huangp.components;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RectangleTest {

    @Test
    public void canDrawRectangle() {
        Canvas canvas = new CanvasImpl(20, 4);
        // the sample I/O's x coordinates are not using index which is not consistent with the other sample.
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        Canvas result = rectangle.draw(canvas);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|             xxxxx|")
                .line("|             x   x|")
                .line("|             xxxxx|")
                .line("|                  |")
                .line("--------------------");

        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void willNotDrawIfRowIsOutsideBoundary() {
        Canvas canvas = new CanvasImpl(20, 4);
        Rectangle rectangle =
                new Rectangle(2, 2, 4, canvas.maxDrawableRow() + 1);
        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfColumnIsOutsideBoundary() {
        Canvas canvas = new CanvasImpl(5, 4);
        Rectangle rectangle =
                new Rectangle(2, 2, canvas.maxDrawableColumn() + 1, canvas.maxDrawableRow());
        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfTopLeftColumnIsNotLessThanBottomRightColumn() {
        Canvas canvas = new CanvasImpl(5, 4);
        Rectangle rectangle =
                new Rectangle(2, 2, 1, 3);

        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfwillNotDrawIfBothCoordinateHaveTheSameColumn() {
        Canvas canvas = new CanvasImpl(5, 4);
        Rectangle rectangle =
                new Rectangle(2, 2, 2, 3);

        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfTopLeftRowIsNotLessThanBottomRightRow() {
        Canvas canvas = new CanvasImpl(5, 4);
        Rectangle rectangle =
                new Rectangle(2, 3, 4, 1);

        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }

    @Test
    public void willNotDrawIfBothCoordinateHaveTheSameRow() {
        Canvas canvas = new CanvasImpl(5, 4);
        Rectangle rectangle =
                new Rectangle(2, 3, 4, 3);

        Canvas result = rectangle.draw(canvas);
        assertThat(result).isSameAs(canvas);
    }
}
