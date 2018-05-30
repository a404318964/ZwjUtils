package com.github.a404318964.zwjutils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by linxiaojun on 16/11/18.
 */
public class GeneratorUtil {

    private static ThreadLocal<Random> threadLocal = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random();
        }
    };

    // TODO 随机单号后缀
    static char[] CHAR_RANDOM = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    // TODO 随机数值
    static char[] NUM_RANDOM = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 随机生成验证码
     *
     * @return
     */
    public static String generatorValideCode(int len) {
        String v = "";
        for (int i = 0; i < len; i++) {
            v += NUM_RANDOM[threadLocal.get().nextInt(10)];
        }
        return v;
    }


    /**
     * 自动生成编号
     *
     * @param prefix 前缀字符
     * @return 编号= 前缀 + MMddHHmm + 后缀3位
     */
    public static String generatorCode(String prefix) {
        return String.join("", prefix,
                DateUtil.getCurDateStr("MMddHHmm"),
                generatorSuffix(3));
    }


    /**
     * 生成UUID
     *
     * @return
     */
    public static String generatorUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 随机生成编号后缀
     *
     * @param len 后缀长试验
     * @return 后缀String
     */
    static String generatorSuffix(int len) {
        String v = "";
        for (int i = 0; i < len; i++) {
            v += CHAR_RANDOM[threadLocal.get().nextInt(36)];
        }
        return v;
    }

}
