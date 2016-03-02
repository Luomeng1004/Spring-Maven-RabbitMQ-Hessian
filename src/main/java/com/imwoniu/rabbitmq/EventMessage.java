package com.imwoniu.rabbitmq;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 首先我们需要一个用来在app和rabbitmq之间传递消息的持有对象
 */
public class EventMessage implements Serializable {

    private String queueName;

    private String exchangeName;

    private byte[] eventData;

    public EventMessage() {

    }

    public EventMessage(String queueName, String exchangeName, byte[] eventData) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.eventData = eventData;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public byte[] getEventData() {
        return eventData;
    }

    public void setEventData(byte[] eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "queueName='" + queueName + '\'' +
                ", exchangeName='" + exchangeName + '\'' +
                ", eventData=" + Arrays.toString(eventData) +
                '}';
    }
}
