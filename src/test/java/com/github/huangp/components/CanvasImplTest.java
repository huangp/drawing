package com.github.huangp.components;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class CanvasImplTest {
    private static final Logger log =
            LoggerFactory.getLogger(CanvasImplTest.class);

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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
        assertThat(canvas.maxDrawableColumn()).isEqualTo(19);
        assertThat(canvas.maxDrawableRow()).isEqualTo(4);

    }

    @Test
    public void WillNotDrawIfCanvasHeightIsLessThan1() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("width must be greater than 2 and height must be greater than 0");
        new CanvasImpl(4, 0);
    }

    @Test
    public void WillNotDrawIfCanvasWidthIsLessThan3() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("width must be greater than 2 and height must be greater than 0");
        new CanvasImpl(2, 3);
    }

    @Test
    public void minimumCanvas() {
        CanvasImpl canvas = new CanvasImpl(3, 1);
        ConsoleSimulator expected = new ConsoleSimulator();
        expected.line("---")
                .line("| |")
                .line("---");
        assertThat(canvas.toString()).isEqualTo(expected.toString());
    }

    @Test
    public void canCheckWhetherColumnIsWithinBoundary() {
        CanvasImpl canvas = new CanvasImpl(3, 1);
        assertThat(canvas.isColumnWithinBoundary(0)).isFalse();
        assertThat(canvas.isColumnWithinBoundary(1)).isTrue();
        assertThat(canvas.isColumnWithinBoundary(2)).isFalse();
    }

    @Test
    public void canCheckWhetherRowIsWithinBoundary() {
        CanvasImpl canvas = new CanvasImpl(3, 1);
        assertThat(canvas.isRowWithinBoundary(0)).isFalse();
        assertThat(canvas.isRowWithinBoundary(1)).isTrue();
        assertThat(canvas.isRowWithinBoundary(2)).isFalse();
    }
}
