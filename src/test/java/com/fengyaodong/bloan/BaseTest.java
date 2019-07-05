package com.fengyaodong.bloan;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 基础测试类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 10:46
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 10:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BloanApplication.class)
public class BaseTest {
}
