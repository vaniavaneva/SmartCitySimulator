package org.citysim.util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MessageOnlyFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage() + "\n";
    }
}