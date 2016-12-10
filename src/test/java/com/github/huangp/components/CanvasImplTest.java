package com.github.huangp.components;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class CanvasImplTest {
    private static final Logger log =
            LoggerFactory.getLogger(CanvasImplTest.class);
    @Test
    public void canDrawCanvas() {
        CanvasImpl canvas = new CanvasImpl(20, 4);

        log.info("canvas:\n{}", canvas);

        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("--------------------")
                .line("|                  |")
                .line("|                  |")
                .line("|                  |")
                .line("|                  |")
                .line("--------------------");

        assertThat(canvas.toString()).isEqualTo(expected.toString());

    }


}
