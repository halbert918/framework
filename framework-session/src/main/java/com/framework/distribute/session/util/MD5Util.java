package com.framework.distribute.session.util;

import com.framework.distribute.session.exception.SessionException;

import java.security.MessageDigest;
import java.util.logging.Logger;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/7
 * @Description
 */
public class MD5Util {

    protected static Logger logger = Logger.getLogger(MD5Util.class.getName());

    /**
     * 获取
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            throw new SessionException("MD5加密异常");
        }
    }

}
