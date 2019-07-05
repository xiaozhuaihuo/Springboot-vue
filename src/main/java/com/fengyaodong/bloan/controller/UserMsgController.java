package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.UserMsgCondition;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import com.fengyaodong.bloan.service.CodeHandlerService;
import com.fengyaodong.bloan.service.UserMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户消息Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/15 14:04
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/15 14:04
 */
@RestController
@RequestMapping(value = "/userMsg")
@Slf4j
public class UserMsgController {

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 用户信息列表查询
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResult list(@RequestBody UserMsgCondition condition) {

        log.info("用户信息列表查询开始，查询条件={}", condition);
        Page<UserMsgDomain> list = userMsgService
                .findByPage(condition, condition.getPageNum(), condition.getPageSize());
        this.handleResult(list.getContent());
        log.info("用户信息列表查询结束，查询结果={}", list);

        return new BaseResult(list);
    }

    /**
     * 处理结果集
     *
     * @param list
     * @return
     */
    private List<UserMsgDomain> handleResult(List<UserMsgDomain> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        Stream<UserMsgDomain> stream = list.stream();
        //处理阅读类型文本信息
        stream = codeHandlerService.handleReadName(stream);
        //处理消息类型文本信息
        stream = codeHandlerService.handleMsgTypeName(stream);

        return stream.collect(Collectors.toList());
    }
}
