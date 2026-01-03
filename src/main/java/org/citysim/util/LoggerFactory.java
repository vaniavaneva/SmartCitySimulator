package org.citysim.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class LoggerFactory {
    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        if (logger.getHandlers().length == 0) {
            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new MessageOnlyFormatter());
            logger.addHandler(handler);
        }

        return logger;
    }
}
