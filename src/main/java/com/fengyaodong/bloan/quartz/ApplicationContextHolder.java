package com.fengyaodong.bloan.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ${TODO} 写点注释吧
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/7/4 17:30
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/7/4 17:30
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
