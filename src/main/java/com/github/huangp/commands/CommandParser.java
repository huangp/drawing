package com.github.huangp.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.components.BucketFill;
import com.github.huangp.components.Canvas;
import com.github.huangp.components.CanvasImpl;
import com.github.huangp.components.Drawable;
import com.github.huangp.components.Line;
import com.github.huangp.components.Rectangle;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class CommandParser {
    private static final Logger log =
            LoggerFactory.getLogger(CommandParser.class);
    private static final Set<Class<?>> DRAWABLE_TYPES = HashSet.of(
            CanvasImpl.class,
            Line.class,
            Rectangle.class,
            BucketFill.class
    );
    private final static Map<CommandInstruction, Method> instructionToFactoryMethodMap = initFactoryMap();

    private Canvas canvas;

    private static Map<CommandInstruction, Method> initFactoryMap() {
        Set<Tuple2<CommandInstruction, Method>>
                instructionToMethod =
                DRAWABLE_TYPES.map(clazz -> {
                    CommandInstruction instruction =
                            clazz.getDeclaredAnnotation(
                                    CommandInstruction.class);
                    Option<Method> methodOpt =
                            List.of(clazz.getMethods()).find(method ->
                                    method.getDeclaredAnnotation(
                                            CommandInitializer.class) !=
                                            null);
                    Method method = methodOpt.getOrElseThrow(
                            () -> new IllegalStateException(
                                    "the registered class needs to have exactly one static factory method annotated with " +
                                            CommandInitializer.class));

                    return Tuple.of(instruction, method);

                });
        return HashMap.ofEntries(instructionToMethod);
    }

    public Drawable makeDrawable(String input) {
        List<String> parts = List.ofAll(
                Splitter.on(" ").omitEmptyStrings().trimResults().split(input));
        if (parts.length() < 3) {
            log.warn("minimum drawing instruction consists of at least 3 arguments");
            return canvas;
        }
        Option<Tuple2<CommandInstruction, Method>> entryOpt =
                instructionToFactoryMethodMap.find(tp -> {
                    CommandInstruction commandInstruction = tp._1();
                    return commandInstruction.instruction()
                            .equalsIgnoreCase(parts.head());
                });
        if (entryOpt.isEmpty()) {
            log.warn("can not understand instruction");
            return canvas;
        }


        // start the real work
        Tuple2<CommandInstruction, Method> commandInstructionToMethod =
                entryOpt.get();
        List<String> argsValue = parts.pop();
        Array<Arg> args = Array.of(commandInstructionToMethod._1().arguments());
        if (args.length() != argsValue.length()) {
            log.warn("invalid number of arguments: required {}, given {}", args.length(), parts.tail().length());
            return canvas;
        }

        Array<Object> values = args.zip(argsValue)
                .map(argDefToValue -> {
                    String stringVal = argDefToValue._2();
                    return toValueConverter(
                            argDefToValue._1().value())
                            .from(stringVal);
                });

        if (values.find(Objects::isNull).isDefined()) {
            log.warn("invalid argument parsing");
            return canvas;
        }

        Method factorMethod = instructionToFactoryMethodMap
                .get(commandInstructionToMethod._1()).get();

        Drawable drawable = invokeFactoryMethod(factorMethod, values);
        if (drawable instanceof Canvas) {
            canvas = (Canvas) drawable;
        } else if (canvas == null) {
            log.warn("you need to have a canvas first");
            return null;
        }
        return drawable;
    }

    private static Drawable invokeFactoryMethod(Method factorMethod,
            Array<Object> argValues) {
        try {
            return (Drawable) factorMethod.invoke(new Object(), argValues.toJavaArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("can not invoke factory method");
            throw new RuntimeException(e);
        }
    }

    private static ValueConverter toValueConverter(Class<? extends ValueConverter> arg) {
        try {
            return arg.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("error instantiate value converter", e);
            throw new RuntimeException(e);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
