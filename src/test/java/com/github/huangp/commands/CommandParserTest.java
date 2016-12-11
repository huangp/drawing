package com.github.huangp.commands;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.components.Canvas;
import com.github.huangp.components.Drawable;
import com.github.huangp.components.VerticalLine;
import com.google.common.base.Splitter;
import javaslang.collection.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserTest {
    private static final Logger log =
            LoggerFactory.getLogger(CommandParserTest.class);
    private CommandParser parser;

    @Before
    public void setUp() {
        parser = new CommandParser();
    }

    @Test
    public void canParseCanvasInstruction() {
        Drawable drawable = parser.makeDrawable("C 20 4");

        assertThat(drawable).isInstanceOf(Canvas.class);
    }

    @Test
    public void giveLessThanThreeArgumentsWillResultInNothing() {
        Drawable drawable = parser.makeDrawable("a b");

        assertThat(drawable).isNull();
    }

    @Test
    public void giveUnrecognizedInstructionWillResultInNothing() {
        Drawable drawable = parser.makeDrawable("A b c");

        assertThat(drawable).isNull();
    }

    @Test
    public void giveInvalidArgumentWillResultInNothing() {
        Drawable drawable = parser.makeDrawable("C a b");

        assertThat(drawable).isNull();
    }

    @Test
    public void giveOtherDrawingBeforeHavingCanvasWilResultInNothing() {
        Drawable drawable = parser.makeDrawable("L 10 5 1 5");

        assertThat(drawable).isNull();
    }

    @Test
    public void canParseCorrectInstructionForDrawingAfterCanvasIsCreated() {
        Drawable canvas = parser.makeDrawable("C 20 4");

        assertThat(canvas).isInstanceOf(Canvas.class);

        Drawable line = parser.makeDrawable("L 1 2 1 3");
        assertThat(line).isInstanceOf(VerticalLine.class);
    }

    @Test
    public void willGetSameCanvasWhenGivingInvalidInstruction() {
        Drawable canvas = parser.makeDrawable("C 20 4");
        assertThat(canvas).isInstanceOf(Canvas.class);

        assertThat(parser.makeDrawable("a b c")).isSameAs(canvas);
        assertThat(parser.makeDrawable("L a b c d")).isSameAs(canvas);
        assertThat(parser.makeDrawable("L 10 5")).isSameAs(canvas);
    }

}
