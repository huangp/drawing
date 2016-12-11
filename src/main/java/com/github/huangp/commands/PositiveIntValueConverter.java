package com.github.huangp.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class PositiveIntValueConverter implements ValueConverter<Integer> {
    private static final Logger log =
            LoggerFactory.getLogger(PositiveIntValueConverter.class);
    @Override
    public Integer from(String value) {
        if (Strings.isNullOrEmpty(value) || value.trim().length() == 0) {
            return null;
        }
        try {
            int i = Integer.parseInt(value);
            if (i < 0) {
                return null;
            }
            return i;
        } catch (NumberFormatException e) {
            log.warn("{} is not a valid integer", value);
            return null;
        }
    }
}
