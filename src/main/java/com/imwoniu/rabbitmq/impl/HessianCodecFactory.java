package com.imwoniu.rabbitmq.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.imwoniu.rabbitmq.CodecFactory;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 下面是编码解码的实现类，用了hessian来实现，大家可以自行选择序列化方式
 */
public class HessianCodecFactory implements CodecFactory {

    private static final Logger logger = Logger.getLogger(HessianCodecFactory.class);

    /**
     * 序列化对象
     * @param obj
     * @return
     * @throws IOException
     */
    public byte[] serialize(Object obj) throws IOException {

        ByteArrayOutputStream baos = null;

        HessianOutput output = null;

        try {
            baos = new ByteArrayOutputStream(1024);
            output = new HessianOutput(baos);
            output.startCall();
            output.writeObject(obj);
            output.completeCall();
        } catch (final IOException ex) {
            throw ex;
        } finally {
            if (output != null) {
                try {
                    baos.close();
                } catch (IOException ex) {
                    this.logger.error("Failed to close stream.", ex);
                }
            }
        }

        return baos != null ? baos.toByteArray() : null;
    }

    /**
     * 反序列化对象
     * @param in
     * @return
     * @throws IOException
     */
    public Object deSerialize(byte[] in) throws IOException {

        Object obj = null;
        ByteArrayInputStream bais = null;
        HessianInput input = null;

        try {
            bais = new ByteArrayInputStream(in);
            input = new HessianInput(bais);
            input.startReply();
            input.completeReply();
        } catch (IOException ex) {
            throw ex;
        } catch (Throwable throwable) {
            this.logger.error("Failed to decode object.", throwable);
        } finally {
            if(input != null) {
                try {
                    bais.close();
                } catch (IOException ex) {
                    this.logger.error("Failed to close stream.", ex);
                }
            }
        }

        return obj;
    }
}
