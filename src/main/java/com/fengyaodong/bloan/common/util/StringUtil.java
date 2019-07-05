package com.fengyaodong.bloan.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串工具类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 14:01
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 14:01
 */
public class StringUtil extends StringUtils {

    public static final String CHARSET_ISO = "ISO-8859-1";
    public static final String CHARSET_UTF8 = "utf-8";

    /**
     * 将字符串转换为集合
     * 数据格式要求为：3333,333,33,3
     *
     * @return
     */
    public static List<String> str2List(String str) {
        String[] strArr = split(str, ",");
        return strArr == null ? new ArrayList() : Arrays.asList(strArr);
    }

    /**
     * 判断结合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 压缩
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString(CHARSET_ISO);
    }

    /**
     * 解压缩
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(CHARSET_ISO));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString(CHARSET_UTF8);
    }
}
