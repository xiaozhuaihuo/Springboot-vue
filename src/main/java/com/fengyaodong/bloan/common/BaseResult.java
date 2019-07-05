package com.fengyaodong.bloan.common;

import com.fengyaodong.bloan.common.constant.LoanConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共响应类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/31 9:53
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/31 9:53
 */
@Data
@ToString
@ApiModel(description = "通用响应返回对象")
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int SUCCESS = LoanConstants.SUCCESS;
    private static final String SUCCESS_MESSAGE = "OK";

    @ApiModelProperty(value = "结果代码")
    private int code;
    @ApiModelProperty(value = "结果信息")
    private String message;
    @ApiModelProperty(value = "结果数据")
    private T data;
    @ApiModelProperty(value = "附加信息")
    private Map<String, Object> extra;

    public BaseResult() {
        super();
        this.code = SUCCESS;
        this.message = SUCCESS_MESSAGE;
    }

    public BaseResult(T data) {
        super();
        this.code = SUCCESS;
        this.message = SUCCESS_MESSAGE;
        this.data = data;
    }

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResult error(String message) {
        return new BaseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public boolean isSuccess() {
        return this.code == SUCCESS;
    }

    public boolean getSuccess() {
        return this.code == SUCCESS;
    }

    public BaseResult<T> putExtra(String key, Object value) {
        if (this.extra == null) {
            this.extra = new HashMap<>(1);
        }
        this.extra.put(key, value);
        return this;
    }

    public BaseResult<T> putExtras(Map<String, Object> extra) {
        if (this.extra == null) {
            this.extra = new HashMap<>(extra.size());
        }
        this.extra.putAll(extra);
        return this;
    }

    /**
     * new 一个实例
     *
     * @return
     */
    public static BaseResult ok() {
        return new BaseResult();
    }

    /**
     * new 一个实例
     *
     * @return
     */
    public static <T> BaseResult ok(T data) {
        return new BaseResult(data);
    }

}
