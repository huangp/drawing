package com.github.huangp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.commands.CommandParser;
import com.github.huangp.components.Canvas;
import com.github.huangp.components.Drawable;


public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        log.info("please input your drawing instruction");

        CommandParser commandParser = new CommandParser();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            while (line != null) {
                if ("Q".equalsIgnoreCase(line.trim())) {
                    log.info("quit");
                    break;
                } else {
                    Drawable drawable = commandParser.makeDrawable(line);
                    if (drawable != null) {
                        Canvas canvas = drawable.draw(commandParser.getCanvas());
                        System.out.println(canvas);
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("error reading line from stdin", e);
        }
    }
}
