package com.github.huangp.commands;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class SingleCharStringValueConverterTest {

    private SingleCharStringValueConverter converter;

    @Before
    public void setUp() {
        converter = new SingleCharStringValueConverter();
    }

    @Test
    public void nullWillGetNull() {
        String value = converter.from(null);
        Assertions.assertThat(value).isNull();
    }

    @Test
    public void emptyWillGetNull() {
        String value = converter.from("");
        Assertions.assertThat(value).isNull();
    }

    @Test
    public void blankWillGetNull() {
        String value = converter.from("   ");
        Assertions.assertThat(value).isNull();
    }

    @Test
    public void singleCharWillReturnAsIs() {
        String value = converter.from("a");
        Assertions.assertThat(value).isEqualTo("a");
    }

}
