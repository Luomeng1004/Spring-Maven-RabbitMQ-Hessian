package com.imwoniu.rabbitmq;

/**
 * 消费接口，所有消费程序都实现这个接口
 */
public interface EventProcessor {
    void process(Object e);
}
