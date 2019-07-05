package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.UserInfoCondition;
import com.fengyaodong.bloan.model.domain.UserInfoDomain;
import com.fengyaodong.bloan.service.CodeHandlerService;
import com.fengyaodong.bloan.service.UserInfoService;
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
 * 用户信息Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/9 22:46
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/9 22:46
 */
@RestController
@RequestMapping(value = "/userInfo")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 用户信息列表查询
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResult list(@RequestBody UserInfoCondition condition) {

        log.info("用户信息列表查询开始，查询条件={}", condition);
        Page<UserInfoDomain> list = userInfoService
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
    private List<UserInfoDomain> handleResult(List<UserInfoDomain> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        Stream<UserInfoDomain> stream = list.stream();
        //处理性别类型文本信息
        stream = codeHandlerService.handleGenderName(stream);
        //处理自动还款文本信息
        stream = codeHandlerService.handleAutoRepayName(stream);

        return stream.collect(Collectors.toList());
    }
}
