package com.imwoniu.rabbitmq.hessian;

import java.io.IOException;

/**
 * 为了可以发送和接受这个消息持有对象，我们还需要需要一个用来序列化和反序列化的工厂
 */
public interface CodecFactory {

    byte[] serialize(Object obj) throws IOException;

    Object deSerialize(byte[] in) throws IOException;

}
