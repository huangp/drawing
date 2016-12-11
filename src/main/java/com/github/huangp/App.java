package com.github.huangp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.commands.CommandParser;
import com.github.huangp.components.Canvas;
import com.github.huangp.components.Drawable;
import javaslang.control.Try;


public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("please input your drawing instruction");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            InputIterator inputIterator = new InputIterator(br);
            ProcessLoop processLoop =
                    new ProcessLoop(inputIterator, new CommandParser(),
                            System.out::println);
            processLoop.process();
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    static class InputIterator implements Iterator<String> {

        private final BufferedReader br;
        private String line;

        InputIterator(BufferedReader br) {
            this.br = br;
        }

        @Override
        public boolean hasNext() {
            line = Try.of(br::readLine).get();
            return line != null;
        }

        @Override
        public String next() {
            return line;
        }
    }

    static class ProcessLoop {
        private final Iterator<String> inputSupplier;
        private final CommandParser commandParser;
        private final Consumer<Canvas> canvasPrinter;

        ProcessLoop(Iterator<String> inputSupplier,
                CommandParser commandParser,
                Consumer<Canvas> canvasPrinter) {
            this.inputSupplier = inputSupplier;
            this.commandParser = commandParser;
            this.canvasPrinter = canvasPrinter;
        }

        void process() {
            while (inputSupplier.hasNext()) {
                String input = inputSupplier.next().trim();
                if ("Q".equalsIgnoreCase(input)) {
                    log.info("quit");
                    break;
                } else if (input.length() == 0) {
                    // user typed blank
                    log.warn("you need to type the drawing instruction");
                } else {
                    Drawable drawable = commandParser.makeDrawable(input);
                    if (drawable != null) {
                        Canvas canvas = drawable.draw(commandParser.getCanvas());
                        commandParser.setCanvas(canvas);
                        canvasPrinter.accept(canvas);
                    }
                }
            }
        }
    }
}
