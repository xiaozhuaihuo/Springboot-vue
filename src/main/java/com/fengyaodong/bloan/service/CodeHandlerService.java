package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.common.codehandler.*;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 状态码、类型码转文本Service
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/5 11:37
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/5 11:37
 */
@Service
public class CodeHandlerService {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 处理提前结清名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends AdvanceHandler> Stream<T> handleAdvanceName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.ADVANCE);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getAdvanceHandlerCode());
            handler.setAdvanceHandlerText(name != null ? name : handler.getAdvanceHandlerCode());
        });
    }

    /**
     * 处理自动还款名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends AutoRepayHandler> Stream<T> handleAutoRepayName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.AUTO_REPAY);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getAutoRepayHandlerCode());
            handler.setAutoRepayHandlerText(name != null ? name : handler.getAutoRepayHandlerCode());
        });
    }

    /**
     * 处理银行卡类型名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends CardTypeHandler> Stream<T> handleCardTypeName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.CARD_TYPE);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getCardTypeHandlerCode());
            handler.setCardTypeHandlerText(name != null ? name : handler.getCardTypeHandlerCode());
        });
    }

    /**
     * 处理结清名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends ClearHandler> Stream<T> handleClearName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.CLEAR);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getClearHandlerCode());
            handler.setClearHandlerText(name != null ? name : handler.getClearHandlerCode());
        });
    }

    /**
     * 处理默认卡名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends DefaultHandler> Stream<T> handleDefaultName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.DEFAULT);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getDefaultHandlerCode());
            handler.setDefaultHandlerText(name != null ? name : handler.getDefaultHandlerCode());
        });
    }

    /**
     * 处理性别名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends GenderHandler> Stream<T> handleGenderName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.GENDER);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getGenderHandlerCode());
            handler.setGenderHandlerText(name != null ? name : handler.getGenderHandlerCode());
        });
    }

    /**
     * 处理消息类型名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends MsgTypeHandler> Stream<T> handleMsgTypeName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.MSG_TYPE);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getMsgTypeHandlerCode());
            handler.setMsgTypeHandlerText(name != null ? name : handler.getMsgTypeHandlerCode());
        });
    }

    /**
     * 处理逾期名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends OverdueHandler> Stream<T> handleOverdueName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.OVERDUE);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getOverdueHandlerCode());
            handler.setOverdueHandlerText(name != null ? name : handler.getOverdueHandlerCode());
        });
    }

    /**
     * 处理阅读名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends ReadHandler> Stream<T> handleReadName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.READ);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getReadHandlerCode());
            handler.setReadHandlerText(name != null ? name : handler.getReadHandlerCode());
        });
    }

    /**
     * 处理还款方式名称信息
     *
     * @param stream
     * @param <T>
     * @return
     */
    public <T extends RepayMethodHandler> Stream<T> handleRepayMethodName(Stream<T> stream) {
        if (stream == null) {
            return Stream.empty();
        }
        Map<String, String> names = dictionaryService.findByParentCode(LoanConstants.REPAY_METHOD);
        if (names == null || names.size() <= 0) {
            return stream;
        }
        return stream.peek(handler -> {
            String name = names.get(handler.getRepayMethodHandlerCode());
            handler.setRepayMethodHandlerText(name != null ? name : handler.getRepayMethodHandlerCode());
        });
    }
}
