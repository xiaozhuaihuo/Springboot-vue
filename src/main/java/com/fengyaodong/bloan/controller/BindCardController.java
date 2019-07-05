package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.BindCardCondition;
import com.fengyaodong.bloan.model.domain.BindCardDomain;
import com.fengyaodong.bloan.service.BindCardService;
import com.fengyaodong.bloan.service.CodeHandlerService;
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
 * 绑卡Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/4/8 0:49
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/4/8 0:49
 */
@RestController
@RequestMapping(value = "/bindCard")
@Slf4j
public class BindCardController {

    @Autowired
    private BindCardService bindCardService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 用户绑卡信息列表查询
     *
     * @param condition
     * @return
     * @throws Exception
     */
    @PostMapping(value = "list")
    public BaseResult list(@RequestBody BindCardCondition condition) throws Exception {

        log.info("用户绑卡信息查询开始，查询条件={}", condition);
        bindCardService.handleCondition(condition);
        Page<BindCardDomain> list = bindCardService
                .findByPage(condition, condition.getPageNum(), condition.getPageSize());
        this.handleResult(list.getContent());
        log.info("用户绑卡信息查询结束，查询结果={}", list);

        return new BaseResult(list);
    }

    /**
     * 处理结果集
     *
     * @param list
     * @return
     */
    private List<BindCardDomain> handleResult(List<BindCardDomain> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        Stream<BindCardDomain> stream = list.stream();
        //处理银行卡类型文本信息
        stream = codeHandlerService.handleCardTypeName(stream);
        //处理默认卡文本信息
        stream = codeHandlerService.handleDefaultName(stream);

        return stream.collect(Collectors.toList());
    }
}
