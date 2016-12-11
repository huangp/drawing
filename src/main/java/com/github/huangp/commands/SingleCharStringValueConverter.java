package com.github.huangp.commands;

import com.google.common.base.Strings;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class SingleCharStringValueConverter implements ValueConverter<String> {
    @Override
    public String from(String value) {
        if (Strings.isNullOrEmpty(value) || value.trim().length() != 1) {
            return null;
        }
        return value;
    }
}
