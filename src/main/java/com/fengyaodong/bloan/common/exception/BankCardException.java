package com.fengyaodong.bloan.common.exception;

/**
 * 银行卡异常
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/18 13:37
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/18 13:37
 */
public class BankCardException extends RuntimeException {

    private static final long serialVersionUID = -4648879327187605014L;

    /**
     * 附加描述信息
     */
    private String message = null;

    /**
     * 本异常错误代码
     */
    private Integer code = 0;

    public BankCardException(String message) {
        super(message);
        this.message = message;
    }

    public BankCardException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
