package com.fengyaodong.bloan.dao.page;

/**
 * ${TODO} 写点注释吧
 *
 * @author: feng_yd[740195680@qq.com]
 * @date: 2019/5/14 9:56
 * @version: V1.0
 * @review: feng_yd[740195680@qq.com]/2019/5/14 9:56
 */
public interface IPagination {

    /**
     * offset
     *
     * @return
     */
    int getOffset();

    /**
     * limit
     *
     * @return
     */
    int getLimit();
}
