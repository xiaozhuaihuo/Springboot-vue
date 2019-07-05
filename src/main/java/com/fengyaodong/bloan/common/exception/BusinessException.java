package com.fengyaodong.bloan.common.exception;

/**
 * 通用业务异常
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/9 20:26
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/9 20:26
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4648879327187605014L;

    /**
     * 附加描述信息
     */
    private String message = null;

    /**
     * 本异常错误代码
     */
    private Integer code = 0;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(Integer code) {
        super();
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public BusinessException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * 获取异常错误代码
     *
     * @return
     */
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        String localMessage = "系统内部错误";
        if (null != this.message) {
            localMessage = this.message;
        }
        return localMessage;
    }

}
