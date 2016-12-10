package com.github.huangp.components;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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

        Assertions.assertThat(result.toString()).isEqualTo(expected.toString());
    }

}
