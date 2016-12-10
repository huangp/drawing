package com.github.huangp.components;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleTest {

    @Test
    public void canDrawRectangle() {
        Canvas canvas = new CanvasImpl(20, 4);
        // the sample I/O's x coordinates are not using index which doesn't make sense...
        Rectangle rectangle = new Rectangle(14, 1, 18, 3);
        Canvas result = rectangle.draw(canvas);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|             xxxxx|")
                .line("|             x   x|")
                .line("|             xxxxx|")
                .line("|                  |")
                .line("--------------------");

        Assertions.assertThat(result.toString()).isEqualTo(expected.toString());
    }

}
