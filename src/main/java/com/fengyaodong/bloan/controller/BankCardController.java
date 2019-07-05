package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.BankCardCondition;
import com.fengyaodong.bloan.model.domain.BankCardDomain;
import com.fengyaodong.bloan.service.BankCardService;
import com.fengyaodong.bloan.service.CodeHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 银行卡Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/13 16:40
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/13 16:40
 */
@RestController
@RequestMapping(value = "/bankCard")
@Slf4j
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 银行卡列表查询
     *
     * @param condition
     * @return
     * @throws Exception
     */
    @PostMapping("/list")
    public BaseResult list(@RequestBody BankCardCondition condition) throws Exception {

        log.info("银行卡列表查询，查询条件={}", condition);
        bankCardService.handleCondition(condition);
        Page<BankCardDomain> list = bankCardService
                .findByPage(condition, condition.getPageNum(), condition.getPageSize());
        this.handleResult(list.getContent());

        return new BaseResult(list);
    }

    /**
     * 新增银行卡
     *
     * @param domain
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public BaseResult add(@RequestBody BankCardDomain domain) throws Exception {

        log.info("添加银行卡，银行卡信息={}", domain);
        bankCardService.addBankCard(domain);

        return BaseResult.ok();
    }

    /**
     * 处理结果集
     *
     * @param list
     * @return
     */
    private List<BankCardDomain> handleResult(List<BankCardDomain> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        Stream<BankCardDomain> stream = list.stream();
        //处理银行卡类型文本信息
        stream = codeHandlerService.handleCardTypeName(stream);

        return stream.collect(Collectors.toList());
    }
}
