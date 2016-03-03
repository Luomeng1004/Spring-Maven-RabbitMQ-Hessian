package com.imwoniu.rabbitmq;

import java.io.IOException;

/**
 * 用来序列化和反序列化的工厂，用于发送和接受消息持有的对象
 */
public interface CodecFactory {

    byte[] serialize(Object obj) throws IOException;

    Object deSerialize(byte[] in) throws IOException;

}
