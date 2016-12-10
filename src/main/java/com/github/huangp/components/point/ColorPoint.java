package com.github.huangp.components.point;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class ColorPoint implements Point {
    private final String color;

    public ColorPoint(String color) {
        this.color = color;
    }

    public static ColorPoint of(String color) {
        return new ColorPoint(color);
    }
    @Override
    public boolean isPainted() {
        return true;
    }

    @Override
    public String paint() {
        return color;
    }
}
