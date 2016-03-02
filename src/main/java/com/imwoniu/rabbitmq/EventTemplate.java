package com.imwoniu.rabbitmq;

import com.imwoniu.rabbitmq.exception.SendRefuseException;

/**
 * 实现发送功能的接口
 */
public interface EventTemplate {

    void send(String queueName, String exchangeName, Object eventContent) throws SendRefuseException;

    void send(String queueName, String exchangeName, Object eventContent, CodecFactory codecFactory) throws SendRefuseException;
}
