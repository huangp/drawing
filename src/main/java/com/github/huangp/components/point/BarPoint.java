package com.github.huangp.components.point;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class BarPoint implements Point {
    @Override
    public boolean isPainted() {
        return true;
    }

    @Override
    public String paint() {
        return "|";
    }
}
