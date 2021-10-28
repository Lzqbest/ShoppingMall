package com.study.netty.chat.common.protocol;

import com.alibaba.fastjson.JSONObject;
import com.common.BaseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author liaozhiqiang1
 * @date 2021/10/21 15:56
 * @Description
 */
public interface Serializer {
    /**
     * 反序列化
     *
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialze(Class<T> clazz, byte[] bytes);

    /**
     * 序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialze(T obj);

    /**
     * 编码解码器
     */
    enum Algorithm implements Serializer {
        /**
         * JDK序列化与反序列化方式
         */
        JDK {
            @Override
            public <T> T deserialze(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (Exception e) {
                    throw new BaseException("对象反序列化异常！");
                }
            }

            @Override
            public <T> byte[] serialze(T obj) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    return bos.toByteArray();
                } catch (Exception e) {
                    throw new BaseException("对象序列化异常！");
                }
            }
        },
        /**
         * JSON序列化与反序列化方式
         */
        JSON {
            @Override
            public <T> T deserialze(Class<T> clazz, byte[] bytes) {
                return (T) JSONObject.parse(new String(bytes, StandardCharsets.UTF_8));
            }

            @Override
            public <T> byte[] serialze(T obj) {
                String str = JSONObject.toJSONString(obj);
                return str.getBytes(StandardCharsets.UTF_8);
            }
        }
    }
}
