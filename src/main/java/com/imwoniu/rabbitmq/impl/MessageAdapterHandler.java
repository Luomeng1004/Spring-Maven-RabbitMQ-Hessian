package com.imwoniu.rabbitmq.impl;

import com.imwoniu.rabbitmq.CodecFactory;
import com.imwoniu.rabbitmq.EventProcesser;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 消息处理适配器，作用是将不同类型的消息交由对应的程序处理
 */
public class MessageAdapterHandler {

    private static final Logger logger = Logger.getLogger(MessageAdapterHandler.class);

    private ConcurrentMap<String, EventProcessorWrap> epwMap;

    public MessageAdapterHandler() {
        this.epwMap = new ConcurrentHashMap<String, EventProcessorWrap>();
    }

    public void handleMessage(EventMessage eem) {
        logger.debug("Receive an EventMessage: [" + eem + "]");
        // 先要判断接收到的message是否是空的，在某些异常情况下，会产生空值
        if (eem == null) {
            logger.warn("Receive an null EventMessage, it may product some errors, and processing message is canceled.");
            return;
        }
        if (StringUtils.isEmpty(eem.getQueueName()) || StringUtils.isEmpty(eem.getExchangeName())) {
            logger.warn("The EventMessage's queueName and exchangeName is empty, this is not allowed, and processing message is canceled.");
            return;
        }
        // 解码，并交给对应的EventHandle执行
        EventProcessorWrap eepw = epwMap.get(eem.getQueueName() + "|" + eem.getExchangeName());
        if (eepw == null) {
            logger.warn("Receive an EopEventMessage, but no processor can do it.");
            return;
        }
        try {
            eepw.process(eem.getEventData());
        } catch (IOException e) {
            logger.error("Event content can not be Deserialized, check the provided CodecFactory.", e);
            return;
        }
    }

    protected void add(String queueName, String exchangeName, EventProcesser processor, CodecFactory codecFactory) {
        if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName) || processor == null || codecFactory == null) {
            throw new RuntimeException("queueName and exchangeName can not be empty,and processor or codecFactory can not be null. ");
        }
        EventProcessorWrap epw = new EventProcessorWrap(codecFactory, processor);
        EventProcessorWrap oldProcessorWrap = epwMap.putIfAbsent(queueName + "|" + exchangeName, epw);
        if (oldProcessorWrap != null) {
            logger.warn("The processor of this queue and exchange exists, and the new one can't be add");
        }
    }

    protected Set<String> getAllBinding() {
        Set<String> keySet = epwMap.keySet();
        return keySet;
    }

    protected static class EventProcessorWrap {

        private CodecFactory codecFactory;

        private EventProcesser eep;

        protected EventProcessorWrap(CodecFactory codecFactory,
                                     EventProcesser eep) {
            this.codecFactory = codecFactory;
            this.eep = eep;
        }

        public void process(byte[] eventData) throws IOException {
            Object obj = codecFactory.deSerialize(eventData);
            eep.process(obj);
        }
    }
}
