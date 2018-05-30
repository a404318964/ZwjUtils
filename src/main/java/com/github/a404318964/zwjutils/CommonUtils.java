package com.github.a404318964.zwjutils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by zwj on 2017/4/26.
 */
public class CommonUtils {

    /**
     * 判断list是否有效（不为空且size大于0）
     */
    public static boolean isValidList(List list) {
        if(list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断map是否有效（不为空且size大于0）
     */
    public static boolean isValidMap(Map map) {
        if(map != null && map.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 对list进行分割
     *
     * @param list   要被切割的数据
     * @param length 每段长度
     */
    public static <T> List<List<T>> divisionList(List<T> list, int length) {
        if (list != null && list.size() > length) {
            List<List<T>> dataList = new ArrayList<List<T>>();

            int count = list.size() / length;
            int remainder = list.size() % length;
            if (remainder > 0) {
                count++;
            }

            for (int i = 0; i < count; i++) {
                int fromIndex = i * length;
                int toIndex = 0;
                if (remainder > 0 && i == count - 1) {
                    toIndex = fromIndex + remainder;
                } else {
                    toIndex = fromIndex + length;
                }

                dataList.add(list.subList(i * length, toIndex));
            }

            return dataList;
        }

        return null;
    }

    /**
     * 将String的list转为数组
     */
    public static String[] ListToArray(List<String> list) {
        String[] strings = new String[list.size()];
        return list.toArray(strings);
    }

    /**
     * 保留两位小数
     */
    public static String fromaterTwoPlacesOfDecimal(double number) {
        if (number != 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(number);
        } else {
            return "0";
        }
    }

    /**
     * 保留两位小数
     */
    public static double fromaterTwoPlacesOfDecimal2(double number) {
        if (number != 0) {
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.valueOf(df.format(number));
        } else {
            return 0;
        }
    }

    /**
     * 判断字符串是否为纯数字d
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
