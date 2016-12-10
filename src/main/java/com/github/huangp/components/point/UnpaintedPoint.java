package com.github.huangp.components.point;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class UnpaintedPoint implements Point {

    public boolean isPainted() {
        return false;
    }

    @Override
    public <P extends Point> P paint(P newPoint) {
        return newPoint;
    }

    @Override
    public String paint() {
        return " ";
    }
}
