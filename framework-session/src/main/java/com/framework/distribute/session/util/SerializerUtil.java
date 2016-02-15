package com.framework.distribute.session.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/7
 * @Description
 */
public class SerializerUtil {

    /**
     * hessian2序列化
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] h2Serialize(Object obj) throws IOException{

        if(null == obj) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    /**
     * hessian2反序列化
     * @param in
     * @return
     * @throws IOException
     */
    public static Object h2Deserialize(byte[] in) throws IOException{
        if(null == in) {
            return null;
        }

        ByteArrayInputStream is = new ByteArrayInputStream(in);
        Hessian2Input hi = new Hessian2Input(is);
        return hi.readObject();
    }

    /**
     * 序列化
     * @param o
     * @return
     */
    public static byte[] kryoSerialize(Object o) {
        Kryo kryo = new Kryo();
        Output output = new Output(0, 1024);
        try {
            kryo.writeClassAndObject(output, o);
            return output.toBytes();
        } finally {
            output.clear();
        }

    }

    /**
     *
     * @param in
     * @return
     */
    public static Object kryoDeserialize(byte[] in) {
        if (in == null) {
            return null;
        }
        Kryo kryo = new Kryo();
        Input input = new Input();
        input.setBuffer(in);
        return kryo.readClassAndObject(input);
    }


}
