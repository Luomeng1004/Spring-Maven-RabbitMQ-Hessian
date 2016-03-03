package com.imwoniu.rabbitmq.exception;

import org.springframework.amqp.AmqpException;

import java.io.IOException;

/**
 * Created by Work on 2016/3/2.
 */
@SuppressWarnings("serial")
public class SendRefuseException extends Exception {

    public SendRefuseException() {
        super();
    }

    public SendRefuseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SendRefuseException(String arg0) {
        super(arg0);
    }

    public SendRefuseException(Throwable arg0) {
        super(arg0);
    }

}
