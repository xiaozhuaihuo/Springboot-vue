package com.fengyaodong.bloan.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据脱敏工具类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 11:39
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 11:39
 */
public final class DesensitizeUtil {

    /**
     * 脱敏符号
     */
    public static final String DESENSITIZE_SYMBOL = "*";

    /**
     * 银行卡脱敏：前缀保留长度
     */
    public static final int CARD_NO_PREFIX_LEN = 0;

    /**
     * 银行卡脱敏：后缀保留长度
     */
    public static final int CARD_NO_SUFFIX_LEN = 4;
    /**
     * 身份证脱敏：前缀保留长度
     */
    public static final int ID_NO_PREFIX_LEN = 6;
    /**
     * 身份证脱敏：后缀保留长度
     */
    public static final int ID_NO_SUFFIX_LEN = 4;

    /**
     * 手机号脱敏：前缀保留长度
     */
    public static final int PHONE_PREFIX_LEN = 3;
    /**
     * 手机号脱敏：后缀保留长度
     */
    public static final int PHONE_SUFFIX_LEN = 4;

    /**
     * 姓名脱敏：后缀保留长度
     */
    public static final int NAME_SUFFIX_LEN = 1;

    /**
     * 对字符串plainText进行脱敏处理，保留左侧len位字符
     * 例如：DesensitizeUtil.left("冯垚栋",1)，<结果：冯**>
     *
     * @param plainText 需要脱敏的数据
     * @param len       保留明文字符数
     * @return
     */
    public static String left(String plainText, int len) {
        if (StringUtils.isBlank(plainText)) {
            return "";
        }
        return StringUtils
                .rightPad(StringUtils.left(plainText, len), StringUtils.length(plainText), DESENSITIZE_SYMBOL);
    }

    /**
     * 对字符串plainText进行脱敏处理，保留右侧len位字符
     * 例如：DesensitizeUtil.right("冯垚栋",1)，<结果：**栋>
     *
     * @param plainText 需要脱敏的数据
     * @param len       保留明文字符数
     * @return
     */
    public static String right(String plainText, int len) {
        if (StringUtils.isBlank(plainText)) {
            return "";
        }
        return StringUtils
                .leftPad(StringUtils.right(plainText, len), StringUtils.length(plainText), DESENSITIZE_SYMBOL);
    }

    /**
     * 对字符串plainText进行脱敏处理，保留左侧leftLen位字符，保留右侧rightLen位字符
     * 例如：DesensitizeUtil.around("13020068384",3,4)，<结果：130****8384>
     *
     * @param plainText 需要脱敏的数据
     * @param leftLen   左侧保留明文字符数
     * @param rightLen  右侧保留明文字符数
     * @return
     */
    public static String around(String plainText, int leftLen, int rightLen) {
        if (StringUtils.isBlank(plainText)) {
            return "";
        }
        int count = leftLen + rightLen;
        if (plainText.length() < count) {
            return plainText;
        }
        String leftText = StringUtils.left(plainText, leftLen);
        String rightText = StringUtils.right(plainText, rightLen);
        return leftText + StringUtils.rightPad("", plainText.length() - count, DESENSITIZE_SYMBOL) + rightText;
    }

    /**
     * 对字符串email进行脱敏处理，保留左侧leftLen位字符，保留右侧【@】字符后所有字符
     * 例如：DesensitizeUtil.email("243773629@qq.com",3)，<结果：243******@qq.com>
     *
     * @param email   需要脱敏的数据
     * @param leftLen 左侧保留明文字符数
     * @return
     */
    public static String email(String email, int leftLen) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int rightLen = StringUtils.lastIndexOf(email, "@");
        return around(email, leftLen, email.length() - rightLen);
    }

    /**
     * 对字符串phone进行脱敏处理，保留左侧leftLen位字符，保留右侧【@】字符后所有字符
     * 例如：DesensitizeUtil.phone("13020068384")，<结果：130****8384>
     *
     * @param phone 需要脱敏的数据
     * @return
     */
    public static String phone(String phone) {
        return around(phone, PHONE_PREFIX_LEN, PHONE_SUFFIX_LEN);
    }

    /**
     * 对字符串身份证进行脱敏处理，保留左侧leftLen位字符，保留右侧【@】字符后所有字符
     * 例如：DesensitizeUtil.idNo("130421195006010318")，<结果：130421********0318>
     *
     * @param idNo 需要脱敏的数据
     * @return
     */
    public static String idNo(String idNo) {
        return around(idNo, ID_NO_PREFIX_LEN, ID_NO_SUFFIX_LEN);
    }

    /**
     * 对字符串银行卡号进行脱敏处理，保留左侧leftLen位字符，保留右侧【@】字符后所有字符
     * 例如：DesensitizeUtil.idNo("6217000260001357154")，<结果：***************7154>
     *
     * @param cardNo 需要脱敏的数据
     * @return
     */
    public static String cardNo(String cardNo) {
        return around(cardNo, CARD_NO_PREFIX_LEN, CARD_NO_SUFFIX_LEN);
    }
}
