package com.imwoniu.rabbitmq.impl;

import org.apache.log4j.Logger;
import org.springframework.util.ErrorHandler;

/**
 * Created by Work on 2016/3/3.
 */
public class MessageErrorHandler implements ErrorHandler {

    private static final Logger logger = Logger.getLogger(MessageErrorHandler.class);

    // 处理错误
    public void handleError(Throwable throwable) {
        logger.error("RabbitMQ happen a error:" + throwable.getMessage(), throwable);
    }
}
