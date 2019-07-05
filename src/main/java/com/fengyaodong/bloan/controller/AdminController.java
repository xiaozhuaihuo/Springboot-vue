package com.fengyaodong.bloan.controller;

import com.fengyaodong.bloan.common.BaseResult;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.model.condition.UserMsgCondition;
import com.fengyaodong.bloan.model.domain.AdminDomain;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import com.fengyaodong.bloan.model.vo.ApprovalVo;
import com.fengyaodong.bloan.model.vo.ForbidVo;
import com.fengyaodong.bloan.service.AdminService;
import com.fengyaodong.bloan.service.CodeHandlerService;
import com.fengyaodong.bloan.service.DictionaryService;
import com.fengyaodong.bloan.service.UserMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 管理员Controller
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/6 21:00
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/6 21:00
 */
@RestController
@RequestMapping(value = "/admin")
@Slf4j
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private CodeHandlerService codeHandlerService;

    /**
     * 管理员登录
     *
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResult sign(AdminDomain adminDomain) throws Exception {

        log.info("admin登录，登录信息={}", adminDomain);
        adminService.adminLogin(adminDomain);
        log.info("登录成功");

        return new BaseResult<>("登录成功");
    }

    /**
     * 管理员信息查询
     *
     * @param adminId
     * @return
     */
    @PostMapping(value = "/findAdmin")
    public BaseResult findAdmin(String adminId) {

        log.info("管理员信息查询，管理员ID={}", adminId);
        AdminDomain domain = adminService.findOneById(adminId);
        log.info("查询成功，返回信息={}", domain);

        return new BaseResult(domain);
    }

    /**
     * 管理员查看待审核信息
     *
     * @return
     */
    @PostMapping(value = "/findMsg")
    public BaseResult findMsg(@RequestBody UserMsgCondition condition) {

        log.info("管理员审批信息查询，查询条件={}", condition);
        Page<UserMsgDomain> list = userMsgService
                .findByPageAll(condition, condition.getPageNum(), condition.getPageSize());
        this.handleResult(list.getContent());

        return new BaseResult(list);
    }

    /**
     * 管理员驳回申请
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/rejectApply")
    public BaseResult reject(@RequestBody ForbidVo vo) {

        log.info("审核信息驳回，驳回信息={},", vo);
        adminService.rejectApply(vo);
        log.info("驳回申请成功");

        return new BaseResult(LoanConstants.SUCCESS, "驳回成功");
    }

    /**
     * 管理员批准申请
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/approvalApply")
    public BaseResult approvalUserApply(@RequestBody ApprovalVo vo) {

        log.info("批准用户申请信息，批准信息={}", vo);
        adminService.approvalApply(vo);
        log.info("批准成功");

        return new BaseResult(LoanConstants.SUCCESS, "批准成功");
    }

    /**
     * 管理员批准提升额度
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/approvalRemote")
    public BaseResult approvalUserRemote(@RequestBody ApprovalVo vo) {

        log.info("批准用户提额信息，批准信息={}", vo);
        adminService.approvalPromote(vo);
        log.info("批准成功");

        return new BaseResult(LoanConstants.SUCCESS, "批准成功");
    }

    /**
     * 根据字典项编码查询数据字典
     *
     * @param itemCode
     * @return
     */
    @PostMapping("/findDic")
    public BaseResult findDictionaryCode(String itemCode) {

        log.info("查询数据字典，字典项编码={}", itemCode);
        Map<String, String> map = dictionaryService.findByParentCode(itemCode);
        log.info("查询结果={}", map);

        return new BaseResult(map);
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
