package com.pgEnterprise.pgCoding.ToolsDemo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    public static final Logger logger = LogManager.getLogger(Log.class);

    public static void main(String[] args) {

        System.out.println("Log4j2 config file: " +
                Thread.currentThread().getContextClassLoader().getResource("log4j2.xml"));

        logger.info("INFO log working");
        logger.error("ERROR log working");
        logger.debug("DEBUG log working");
    }
    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}

