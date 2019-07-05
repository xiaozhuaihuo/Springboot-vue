package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.BillCondition;
import com.fengyaodong.bloan.model.domain.BillDomain;
import com.fengyaodong.bloan.service.BillService;
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
 * 总账单Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/7 15:40
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/7 15:40
 */
@RestController
@RequestMapping(value = "/bill")
@Slf4j
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 总账单列表查询
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResult list(@RequestBody BillCondition condition) {

        log.info("总账单列表查询开始，查询条件={}", condition);
        Page<BillDomain> list = billService.findByPage(condition, condition.getPageNum(), condition.getPageSize());
        this.handleResult(list.getContent());
        log.info("总账单列表查询结束，查询结果={}", list);

        return new BaseResult(list);
    }

    /**
     * 处理结果集
     *
     * @param list
     * @return
     */
    private List<BillDomain> handleResult(List<BillDomain> list) {

        if (list == null || list.size() <= 0) {
            return null;
        }
        Stream<BillDomain> stream = list.stream();
        //处理提前还款文本信息
        stream = codeHandlerService.handleAdvanceName(stream);
        //处理账单状态文本信息
        stream = codeHandlerService.handleClearName(stream);
        //处理自动还款文本信息
        stream = codeHandlerService.handleAutoRepayName(stream);
        //处理还款方式文本信息
        stream = codeHandlerService.handleRepayMethodName(stream);

        return stream.collect(Collectors.toList());
    }
}
