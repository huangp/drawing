package com.github.huangp.components;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class ConsoleSimulator {
    private StringBuilder sb = new StringBuilder();
    public ConsoleSimulator line(String line) {
        sb.append(line).append("\n");
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
