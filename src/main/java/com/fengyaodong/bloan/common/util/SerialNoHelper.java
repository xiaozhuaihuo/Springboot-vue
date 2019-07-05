package com.fengyaodong.bloan.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取流水号或主键的帮助类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/11 11:53
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/11 11:53
 */
public final class SerialNoHelper {

    /**
     * 获取默认流水号:17位时间戳+6位随机数(yyyyMMddHHmmssSSS+6位随机数)
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:33:54
     */
    public static String getDefaultSerialNo() {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(getTmsmpSerialNo());
        serialNo.append(random6Num());
        return serialNo.toString();
    }

    /**
     * 获取默认流水号:前缀+17位时间戳+6位随机数(prefix+yyyyMMddHHmmssSSS+6位随机数)
     *
     * @param prefix 流水号前缀
     * @return
     * @author liaochangxun 2016年7月27日 下午4:32:55
     */
    public static String getDefaultSerialNo(String prefix) {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(prefix);
        serialNo.append(getDefaultSerialNo());
        return serialNo.toString();
    }

    /**
     * 获取流水号:14位时间戳+6位随机数(yyyyMMddHHmmss+6位随机数)
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:33:54
     */
    public static String getShortDefaultSerialNo() {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(getShortTmsmpSerialNo());
        serialNo.append(random6Num());
        return serialNo.toString();
    }

    /**
     * 获取流水号:前缀+14位时间戳+6位随机数(prefix+yyyyMMddHHmmss+6位随机数)
     *
     * @param prefix 流水号前缀
     * @return
     * @author liaochangxun 2016年7月27日 下午4:33:54
     */
    public static String getShortDefaultSerialNo(String prefix) {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(prefix);
        serialNo.append(getShortTmsmpSerialNo());
        return serialNo.toString();
    }

    /**
     * 获取流水号:前缀+17位时间戳+流水号后缀(prefix+yyyyMMddHHmmssSSS+suffix)
     *
     * @param prefix 流水号前缀
     * @param suffix 流水号后缀
     * @return
     * @author liaochangxun 2016年7月27日 下午4:47:38
     */
    public static String getDefaultSerialNo(String prefix, String suffix) {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(prefix);
        serialNo.append(getShortTmsmpSerialNo());
        serialNo.append(suffix);
        return serialNo.toString();
    }

    /**
     * 获取流水号:前缀+14位时间戳+流水号后缀(prefix+yyyyMMddHHmmss+suffix)
     *
     * @param prefix 流水号前缀
     * @param suffix 流水号后缀
     * @return
     * @author liaochangxun 2016年7月27日 下午5:06:17
     */
    public static String getSerialNo(String prefix, String suffix) {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(prefix);
        serialNo.append(getShortTmsmpSerialNo());
        serialNo.append(suffix);
        return serialNo.toString();
    }

    /**
     * 获取流水号:前缀+14位时间戳+8位序列号(prefix+yyyyMMddHHmmss+序列号[不足8位左补0])
     *
     * @param prefix
     * @param seqNo
     * @return
     * @author liaochangxun 2016年8月8日 下午12:46:34
     */
    public static String getSerialNoBySeqNo(String prefix, String seqNo) {
        StringBuffer serialNo = new StringBuffer();
        serialNo.append(prefix);
        serialNo.append(getShortTmsmpSerialNo());
        serialNo.append(addzero(Integer.parseInt(seqNo), 8));
        return serialNo.toString();
    }

    /**
     * 获取时间戳流水号:17位时间戳(yyyyMMddHHmmssSSS)
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:44:23
     */
    public static String getTmsmpSerialNo() {
        DateFormat completeDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return completeDateFormat.format(new Date());
    }

    /**
     * 获取时间戳流水号:14位时间戳(yyyyMMddHHmmss)
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:44:16
     */
    public static String getShortTmsmpSerialNo() {
        DateFormat completeDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return completeDateFormat.format(new Date());
    }

    /**
     * 获取4位随机数
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:37:05
     */
    public static int random4Num() {
        return (int) Math.round(Math.random() * 8999 + 1000);
    }

    /**
     * 获取6位随机数
     *
     * @return
     * @author liaochangxun 2016年7月27日 下午4:33:14
     */
    public static int random6Num() {
        return (int) Math.round(Math.random() * 899999 + 100000);
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     * @author liaochangxun 2016年8月8日 下午12:36:02
     */
    public static String addzero(int sourceDate, int formatLength) {
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }
}
