package com.github.huangp.commands;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class PositiveIntValueConverterTest {

    private PositiveIntValueConverter converter;

    @Before
    public void setUp() {
        converter = new PositiveIntValueConverter();
    }

    @Test
    public void willReturnNullForNull() {
        assertThat(converter.from(null)).isNull();
    }

    @Test
    public void willReturnNullForEmpty() {
        assertThat(converter.from("")).isNull();
    }

    @Test
    public void willReturnNullForBlank() {
        assertThat(converter.from("  ")).isNull();
    }

    @Test
    public void willReturnNullForNegative() {
        assertThat(converter.from("-1")).isNull();
    }

    @Test
    public void willReturnNullForNonInteger() {
        assertThat(converter.from("a")).isNull();
    }

    @Test
    public void willReturnNullForFloatPoint() {
        assertThat(converter.from("1.1")).isNull();
    }

    @Test
    public void willReturnIntegerForPositiveIntegerString() {
        assertThat(converter.from("1")).isEqualTo(1);
    }

}
