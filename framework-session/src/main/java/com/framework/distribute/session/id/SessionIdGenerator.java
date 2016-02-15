package com.framework.distribute.session.id;

import com.framework.distribute.session.exception.SessionException;
import com.framework.distribute.session.util.IPUtil;
import com.framework.distribute.session.util.MD5Util;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.UUID;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2015/9/1
 * @Description
 */
public class SessionIdGenerator {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * create sessionId
     * @return
     */
    public static String generateSessionId() {
        StringBuffer sb = new StringBuffer();
        try {
            String mac = IPUtil.getMACAddress();
            sb.append(mac);
        } catch (Exception e) {
            throw new SessionException("获取mac地址失败");
        }
        //虚拟机进程id
        int pId = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        String uuid = UUID.randomUUID().toString().replace("_", "");
        sb.append(pId).append(uuid);
        return MD5Util.MD5(sb.toString());
    }


    /**
     * 获取随机数
     * @param sum
     * @return
     */
    public static String generateMixRandomCode(int sum){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < sum; ++i){
            int number = random.nextInt(62);//[0,62)

            sb.append(RANDOM_STR.charAt(number));
        }
        return sb.toString();
    }

}
