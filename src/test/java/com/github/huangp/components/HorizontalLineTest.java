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
        Canvas after = horizontalLine.draw(canvas);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|                  |")
                .line("|xxxxxx            |")
                .line("|                  |")
                .line("|                  |")
                .line("--------------------");

        assertThat(after.toString()).isEqualTo(expected.toString());
    }

}
