package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.PerBillCondition;
import com.fengyaodong.bloan.model.domain.PerBillDomain;
import com.fengyaodong.bloan.service.PerBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当期账单Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/4/8 14:40
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/4/8 14:40
 */
@RestController
@RequestMapping(value = "/perBill")
@Slf4j
public class PerBillController {

    @Autowired
    private PerBillService perBillService;

    /**
     * 当期账单列表查询
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResult list(@RequestBody PerBillCondition condition) {

        log.info("当期账单查询开始，查询条件={}", condition);
        Page<PerBillDomain> list = perBillService
                .findByPage(condition, condition.getPageNum(), condition.getPageSize());
        log.info("当期账单查询结束，查询结果={}", list);

        return new BaseResult(list);
    }
}
