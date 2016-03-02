package com.imwoniu.rabbitmq.impl;

import com.imwoniu.rabbitmq.CodecFactory;
import com.imwoniu.rabbitmq.EventTemplate;
import com.imwoniu.rabbitmq.exception.SendRefuseException;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * EventTemplate的实现类
 * 主要功能是将数据转换为EventTemplate
 */
public class DefaultEventTemplate implements EventTemplate {

    private static final Logger logger = Logger.getLogger(DefaultEventTemplate.class);

    private AmqpTemplate eventAmqpTemplate;

    private CodecFactory defaultCodeFactory;

    public DefaultEventTemplate(AmqpTemplate eventAmqpTemplate, CodecFactory defaultCodeFactory) {
        this.eventAmqpTemplate = eventAmqpTemplate;
        this.defaultCodeFactory = defaultCodeFactory;
    }

    public void send(String queueName, String exchangeName, Object eventContent) throws SendRefuseException {
        this.send(queueName, exchangeName, eventContent, defaultCodeFactory);
    }

    public void send(String queueName, String exchangeName, Object eventContent, CodecFactory codecFactory) throws SendRefuseException {

        if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName)) {
            throw new SendRefuseException("queueName or exchangeName can not be empty.");
        }

        byte[] eventContentBytes = null;
        if (codecFactory == null) {
            if (eventContent == null) {
                logger.warn("Find eventContent is null,are you sure...");
            } else {
                throw new SendRefuseException("codecFactory must not be null ,unless eventContent is null");
            }
        } else {
            try {
                eventContentBytes = codecFactory.serialize(eventContent);
            } catch (IOException e) {
                throw new SendRefuseException(e);
            }
        }

        EventMessage msg = new EventMessage(queueName, exchangeName, eventContentBytes);
        try {
            eventAmqpTemplate.convertAndSend(exchangeName, queueName, msg);
        } catch (AmqpException e) {
            logger.error("send event fail. Event Message : [" + eventContent + "]", e);
            throw new SendRefuseException("send event fail", e);
        }


    }
}
