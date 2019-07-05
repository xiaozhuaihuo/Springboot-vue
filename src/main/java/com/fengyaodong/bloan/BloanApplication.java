package com.fengyaodong.bloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/3/5 10:07
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/3/5 10:07
 */
@SpringBootApplication
@EnableScheduling
@ImportResource("quartz.xml")
public class BloanApplication {

    /**
     * 启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(BloanApplication.class, args);
    }
}
