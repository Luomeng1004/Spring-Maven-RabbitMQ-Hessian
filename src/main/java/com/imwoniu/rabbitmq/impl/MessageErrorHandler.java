package com.imwoniu.rabbitmq.impl;

import org.apache.log4j.Logger;
import org.springframework.util.ErrorHandler;

/**
 * Created by Work on 2016/3/3.
 */
public class MessageErrorHandler implements ErrorHandler {

    private static final Logger logger = Logger.getLogger(MessageErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        logger.error("RabbitMQ happen a error:" + t.getMessage(), t);
    }
}
