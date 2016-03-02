package com.imwoniu.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Work on 2016/3/2.
 */
public class MessageAdapterHandler {

    private static final Logger logger = Logger.getLogger(MessageAdapterHandler.class);

    private ConcurrentMap<String, EventProcessorWrap> epwMap;

    public MessageAdapterHandler() {
        this.epwMap = new ConcurrentMap<String, EventProcessorWrap>();
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
