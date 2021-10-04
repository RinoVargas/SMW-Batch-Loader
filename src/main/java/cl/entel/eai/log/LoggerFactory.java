package cl.entel.eai.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class LoggerFactory {

    public static Logger getLogger(String path, String filename, String pattern, String level) {
        LoggerContext logCtx = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
        logEncoder.setContext(logCtx);
        logEncoder.setPattern(pattern);
        logEncoder.start();

        RollingFileAppender<ILoggingEvent> logFileAppender = new RollingFileAppender<>();
        logFileAppender.setContext(logCtx);
        logFileAppender.setName("logFile");
        logFileAppender.setEncoder(logEncoder);
        logFileAppender.setAppend(true);
        logFileAppender.setFile(path + "/" + filename);

        TimeBasedRollingPolicy logFilePolicy = new TimeBasedRollingPolicy<>();
        logFilePolicy.setContext(logCtx);
        logFilePolicy.setParent(logFileAppender);
        logFilePolicy.setFileNamePattern(path + "/" + filename + "-%d{yyyy-MM-dd}.log");
        logFilePolicy.setMaxHistory(7);
        logFilePolicy.start();

        logFileAppender.setRollingPolicy(logFilePolicy);
        logFileAppender.start();

        Logger logger = logCtx.getLogger("appLogger");

        //
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(logCtx);
        consoleAppender.setName("consoleLogger");
        consoleAppender.setEncoder(logEncoder);

        logger.addAppender(logFileAppender);
        logger.addAppender(consoleAppender);

        switch (level.toUpperCase()) {
            case "OFF" :
                break;
            case "ERROR" :
                logger.setLevel(Level.ERROR);
                break;
            case "INFO" :
                logger.setLevel(Level.INFO);
                break;
            case "DEBUG" :
                logger.setLevel(Level.DEBUG);
                break;
            case "TRACE" :
                logger.setLevel(Level.TRACE);
                break;
            case "ALL" :
                logger.setLevel(Level.ALL);
                break;
            default:
                logger.setLevel(Level.WARN);
        }

        logger.setAdditive(false);

        return logger;
    }
}
