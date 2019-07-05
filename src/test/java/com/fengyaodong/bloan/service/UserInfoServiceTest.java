package com.fengyaodong.bloan.service;

import com.fengyaodong.bloan.BaseTest;
import com.fengyaodong.bloan.common.constant.LoanConstants;
import com.fengyaodong.bloan.common.util.DesUtil;
import com.fengyaodong.bloan.common.util.UUIDUtils;
import com.fengyaodong.bloan.model.domain.UserInfoDomain;
import com.fengyaodong.bloan.model.domain.UserMsgDomain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 用户信息测试类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/12 13:52
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/12 13:52
 */
@Slf4j
public class UserInfoServiceTest extends BaseTest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserQuotaService userQuotaService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void add() throws Exception{

        UserInfoDomain domain = new UserInfoDomain();

        domain.setUserId(UUIDUtils.getUUID());
        domain.setLoginNo("fyd740195680");
        domain.setPassword("fyd740195680");
        domain.setUserName("冯垚栋");
        domain.setUserMobileNo("15735170736");
        domain.setUserIdNo("140427199609228080");
        domain.setUserSex(LoanConstants.MALE);
        domain.setUserBirthDate(new Date());
        domain.setUserMail("850195680@qq.com");
        domain.setUserAddress("山西省长治市壶关县");
        userInfoService.addUserInfo(domain);
    }

    @Test
    public void encrypt() throws Exception{

        String name = "fengyaodong";
        String newName = DesUtil.encrypt(name);
        System.out.println(newName);
    }

    @Test
    public void apply() {

        BigDecimal quotaAmt = new BigDecimal(20000);
        UserMsgDomain domain = userMsgService.findOneById("114d9b828fec47d6b31506c22e284556");

    }

    @Test
    public void find() {

        Map<String, String> map = dictionaryService.findByParentCode(LoanConstants.MSG_TYPE);
        System.out.println(map);
    }
}
