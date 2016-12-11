package com.github.huangp.components;

import com.github.huangp.commands.Arg;
import com.github.huangp.commands.CommandInitializer;
import com.github.huangp.commands.CommandInstruction;
import com.github.huangp.commands.PositiveIntValueConverter;
import com.google.common.base.Preconditions;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@CommandInstruction(instruction = "L", drawable = Drawable.class, arguments = {
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class),
        @Arg(value = PositiveIntValueConverter.class)
}
)
public final class Line {

    @CommandInitializer
    public static Drawable instance(int x1, int y1, int x2, int y2) {
        Preconditions.checkArgument(x1 > 0 && y1 > 0 && x2 > 0 && y2 > 0, "x1, y1, x2, y2 must all be greater than 0");
        Preconditions.checkArgument(y1 == y2 || x1 == x2, "only horizontal or vertical line is supported");
        if (y1 == y2) {
            if (x1 < x2) {
                return new HorizontalLine(x1, x2, y1);
            } else {
                return new HorizontalLine(x2, x1, y1);
            }
        } else {
            if (y1 < y2) {
                return new VerticalLine(y1, y2, x1);
            } else {
                return new VerticalLine(y2, y1, x1);
            }
        }
    }
}
