package io.transwarp.pudge.protocol;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;

/**
 * Created by Nirvana on 2017/12/13.
 * TODO LinkedBuffer recycle.
 */
public class ProtostuffUtils {

    private ProtostuffUtils() {}

    public static <T> byte[] serializer(T o, Schema<T> schema) {
        return ProtobufIOUtil.toByteArray(o, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    public static <T> T deserializer(byte[] bytes, Schema<T> schema) {
        T obj = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }
}
