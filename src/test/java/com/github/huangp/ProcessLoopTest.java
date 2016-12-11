package com.github.huangp;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.huangp.commands.CommandParser;
import com.github.huangp.components.Canvas;
import com.github.huangp.components.Drawable;
import com.github.huangp.components.HorizontalLine;
import javaslang.control.Try;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ProcessLoopTest {
    private static final Logger log =
            LoggerFactory.getLogger(ProcessLoopTest.class);

    private App.ProcessLoop processLoop;
    private Try<String> supplier;
    @Mock
    private CommandParser commandParser;
    @Mock
    private Canvas canvas;
    @Mock
    private Drawable line;
    @Mock
    private Consumer<Canvas> canvasPrinter;
    @Mock
    private Iterator<String> inputSimulator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        processLoop = new App.ProcessLoop(inputSimulator, commandParser,
                canvasPrinter);
    }

    @Test
    public void canProcessInputContinuously() throws Throwable {
        when(inputSimulator.hasNext()).thenReturn(true, true, true, false);
        when(inputSimulator.next()).thenReturn("a", "b", "c");

        processLoop.process();

        verify(commandParser).makeDrawable("a");
        verify(commandParser).makeDrawable("b");
        verify(commandParser).makeDrawable("c");
    }

    @Test
    public void canProcessQ() throws Throwable {
        when(inputSimulator.hasNext()).thenReturn(true);
        when(inputSimulator.next()).thenReturn("Q");

        processLoop.process();

        verifyZeroInteractions(commandParser);
    }

    @Test
    public void canParseToDrawableAndDraw() throws Throwable {
        when(inputSimulator.hasNext()).thenReturn(true);
        when(inputSimulator.next()).thenReturn("C", "L", "Q");
        when(commandParser.makeDrawable("C")).thenReturn(canvas);
        when(commandParser.makeDrawable("L")).thenReturn(line);
        when(commandParser.getCanvas()).thenReturn(canvas);
        when(line.draw(canvas)).thenReturn(canvas);

        processLoop.process();

        verify(line).draw(canvas);
        verify(canvasPrinter).accept(canvas);
    }

    @Test
    public void emptyInputWillContinue() {
        when(inputSimulator.hasNext()).thenReturn(true);
        when(inputSimulator.next()).thenReturn("", "C", "Q");

        processLoop.process();

        verify(commandParser).makeDrawable("C");
    }
}
