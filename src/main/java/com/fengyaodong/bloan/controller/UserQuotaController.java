package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.model.condition.UserQuotaCondition;
import com.fengyaodong.bloan.model.domain.UserQuotaDomain;
import com.fengyaodong.bloan.service.UserQuotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户额度Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/10 9:46
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/10 9:46
 */
@Controller
@RequestMapping(value = "/userQuota")
@Slf4j
public class UserQuotaController {

    @Autowired
    private UserQuotaService userQuotaService;

    /**
     * 用户额度列表查询
     *
     * @param condition
     * @return
     */
    @PostMapping(value = "list")
    public BaseResult list(UserQuotaCondition condition) {

        log.info("用户额度列表查询开始，查询条件={}", condition);
        List<UserQuotaDomain> list = userQuotaService.find(condition);
        log.info("用户额度列表查询结束,查询结果={}", list);

        return new BaseResult(list);
    }
}
